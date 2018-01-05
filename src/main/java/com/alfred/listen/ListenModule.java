package com.alfred.listen;


import com.alfred.speak.SpeakModule;
import com.alfred.think.ThinkModule;
import com.alfred.util.JsonParser;
import com.iflytek.cloud.speech.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sogaaIT003 on 2018/1/4.
 */
public class ListenModule {
    private static final String APP_ID = "=5a4af9c5";

    public ListenModule() {
        SpeechUtility.createUtility(SpeechConstant.APPID + APP_ID);
    }

    public void startListening(final ThinkModule thinkModule, final SpeakModule speakModule) {
        final SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");
        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");
        mIat.setParameter(SpeechConstant.VAD_EOS, "500");
        //3.开始听写
        mIat.startListening(new RecognizerListener() {
            private double volumeSum = 0;
            private double volumeTimes = 0;
            private StringBuffer sbResult = new StringBuffer();

            public void onVolumeChanged(int volume) {
                if (volume > 15) {
                    volumeSum += volume;
                    volumeTimes++;
                }
            }

            public void onBeginOfSpeech() {
                System.out.println("===开始录音===");
                volumeSum = 0;
                volumeTimes = 0;
                sbResult = new StringBuffer();
            }

            public void onEndOfSpeech() {
                System.out.println("===结束录音===");
            }

            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (recognizerResult == null || StringUtils.isEmpty(recognizerResult.getResultString())) {
                    mIat.stopListening();
                    mIat.startListening(this);
                    return;
                }
                String result = JsonParser.parseIatResult(recognizerResult.getResultString());
                sbResult.append(result);
                if (isLast) {
                    //判断是否达到标准
                    double avgVolume = volumeSum / volumeTimes;
                    System.out.println("===平均音量：" + avgVolume);
                    if (avgVolume >= 20) {
                        System.out.println("识别语音结果: " + sbResult.toString());
                        String thinkingResult = thinkModule.thinking(sbResult.toString());
                        speakModule.speakResult(thinkingResult);
                    }
                    mIat.stopListening();
                    mIat.startListening(this);
                }
            }

            public void onError(SpeechError speechError) {
                System.out.println(speechError);
                //mIat.stopListening();
                //mIat.startListening(this);
            }

            public void onEvent(int i, int i1, int i2, String s) {

            }
        });
    }
}
