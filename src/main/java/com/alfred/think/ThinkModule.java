package com.alfred.think;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sogaaIT003 on 2018/1/4.
 */
public class ThinkModule {
    private String defaultResult = "对不起，没有听懂你说什么。";
    private String url = "http://www.tuling123.com/openapi/api";
    private List<BasicNameValuePair> formParams = new ArrayList<>();
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private static List<String> wakeupWorks = new ArrayList<>();

    static {
        wakeupWorks.add("阿福");
        wakeupWorks.add("狗贼");
    }

    public ThinkModule buildFormParams(BasicNameValuePair basicNameValuePair) {
        formParams.add(basicNameValuePair);
        return this;
    }

    public String thinking(String input) {
        String result = defaultResult;
        try {
            result = useTulingRebort(input);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException closeExcetpion) {
                System.out.println(closeExcetpion);
            }
        }
        System.out.println("Thinking Result: " + result);
        return result;
    }

    private Map<String, String> startWords(String input) {
        return null;
    }

    private String useTulingRebort(String input) throws IOException {
        httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;
        formParams.remove(formParams.size() - 1);
        buildFormParams(new BasicNameValuePair("info", input));
        uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        post.setEntity(uefEntity);
        response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        Map map = (Map) JSONObject.parse(EntityUtils.toString(entity));
        return (map.get("text") + "");
    }
}
