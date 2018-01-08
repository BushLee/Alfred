package com.alfred.think;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by sogaaIT003 on 2018/1/8.
 */
public class WakeupAndSleepWords {
    private static Map<String, String> wakeupMap = new HashMap<>();
    private static Map<String, String> sleepMap = new HashMap<>();

    static {
        try {
            Properties wakeUpProperties = new Properties();
            wakeUpProperties.load(new InputStreamReader(Object.class.getResourceAsStream("/wakeupWords.properties"), "UTF-8"));
            Set<String> wakeupWords = wakeUpProperties.stringPropertyNames();
            wakeupWords.forEach(words -> {
                String answer = wakeUpProperties.getProperty(words);
                wakeupMap.put(words, answer);
            });

            Properties sleepProperties = new Properties();
            sleepProperties.load(new InputStreamReader(Object.class.getResourceAsStream("/sleepWords.properties"), "UTF-8"));
            Set<String> sleepWords = sleepProperties.stringPropertyNames();
            sleepWords.forEach(words -> {
                String answer = sleepProperties.getProperty(words);
                sleepMap.put(words, answer);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> isWakeupWords(String input) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("isWakeup", false);
        if (wakeupMap.containsKey(input)) {
            returnMap.put("isWakeup", true);
            returnMap.put("answer", wakeupMap.get(input));
        }
        return returnMap;
    }

    public static Map<String, Object> isSleepWords(String input) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("isWakeup", true);
        if (sleepMap.containsKey(input)) {
            returnMap.put("isWakeup", false);
            returnMap.put("answer", sleepMap.get(input));
        }
        return returnMap;
    }

}
