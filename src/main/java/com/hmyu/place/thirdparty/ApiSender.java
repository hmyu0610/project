package com.hmyu.place.thirdparty;

import com.hmyu.place.constant.MessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiSender {
    private static final Logger logger = LoggerFactory.getLogger(ApiSender.class);

    /**
     * Desc : http-post
     */
    public static String post(String uri) throws IOException {
        return post(uri, null, null);
    }

    public static String post(String uri, String jsonString) throws IOException {
        return post(uri, null, jsonString);
    }

    public static String post(String uri, HashMap<String, Object> headerMap, String jsonString) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 추가 헤더 세팅
        if (headerMap != null) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                headers.add(key, value);
            }
        }

        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            logger.error("[ApiSender] post : " + e, e);
            throw new RuntimeException(MessageConstant.INTERNAL_HTTP_CONNECT_ERROR.getCode());
        }

        return responseEntity.getBody();
    }

    /**
     * Desc : http-get
     */
    public static String get(String uri) throws IOException {
        return get(uri, null);
    }

    public static String get(String uri, HashMap<String, Object> headerMap) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 추가 헤더 세팅
        if (headerMap != null) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                headers.add(key, value);
            }
        }

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error("[ApiSender] get : " + e, e);
            throw new RuntimeException(MessageConstant.INTERNAL_HTTP_CONNECT_ERROR.getCode());
        }

        return responseEntity.getBody();
    }

}
