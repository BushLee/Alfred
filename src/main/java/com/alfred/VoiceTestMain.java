package com.alfred;

import com.alfred.listen.ListenModule;
import com.alfred.speak.SpeakModule;
import com.alfred.think.ThinkModule;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by sogaaIT003 on 2018/1/2.
 */
public class VoiceTestMain {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
        ThinkModule thinkModule = buildThinkModule();
        SpeakModule speakModule = new SpeakModule();
        ListenModule listenModule = new ListenModule();
        listenModule.startListening(thinkModule, speakModule);
    }

    private static ThinkModule buildThinkModule() {
        ThinkModule thinkModule = new ThinkModule();
        thinkModule.buildFormParams(new BasicNameValuePair("key", "b89c8ef9e35443228a1c2f4e07052ec4"));
        thinkModule.buildFormParams(new BasicNameValuePair("userid", "tset001"));
        return thinkModule;
    }
}
