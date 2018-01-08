package com.alfred.speak;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;

/**
 * Created by sogaaIT003 on 2018/1/4.
 */
public class SpeakModule {
    public void speakResult(String result) {
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        mTts.setParameter(SpeechConstant.VOICE_NAME, "vixf");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        mTts.startSpeaking(result, new SynthesizerListener() {
            public void onBufferProgress(int i, int i1, int i2, String s) {
            }

            public void onSpeakBegin() {
            }

            public void onSpeakProgress(int i, int i1, int i2) {
            }

            public void onSpeakPaused() {
            }

            public void onSpeakResumed() {
            }

            public void onCompleted(SpeechError speechError) {
                System.out.println(speechError);
            }

            public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {
            }
        });
    }
}
