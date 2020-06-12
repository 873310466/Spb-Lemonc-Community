/*
package com.lemonfish.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.utils
 * @date 2020/5/4 14:45
 *//*

@Component
public class HttpUtils {
    @Autowired
    private RestTemplate restTemplate;

    public static final int SUCCESS_CODE = 200;


    */
/**
     * 封装get方法
     * @param url
     * @return
     *//*

    public String doGet(String url) {
        // 1.设置请求头，伪装浏览器
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestHeaders);

        //ResponseEntity<MyJsonResult> responseEntity = restTemplate.getForEntity(url, MyJsonResult.class);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
        if (responseEntity.getStatusCodeValue()==SUCCESS_CODE) {
            return responseEntity.getBody().toString();
        }
        return null;
    }

    public Map<String, Object> doPost(String url) {

        // 1.设置请求头，伪装浏览器

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");

        // body，请求体，如果需要可以设置
        //MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //requestBody.add("data", data);
        //HttpEntity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, requestEntity, Map.class);
        if (responseEntity.getStatusCodeValue() == SUCCESS_CODE) {
            return responseEntity.getBody();
        }

        return null;
    }

}
*/
