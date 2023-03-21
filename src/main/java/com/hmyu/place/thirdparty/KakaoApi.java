package com.hmyu.place.thirdparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.constant.CommonProperties;
import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.constant.StringConstant;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class KakaoApi {
    private static final Logger logger = LoggerFactory.getLogger(KakaoApi.class);

    public static final String KAKAO_DOMAIN = CommonProperties.PROPERTIES_MAP.get(StringConstant.KAKAO_DOMAIN);
    public static final String KEY_TYPE = CommonProperties.PROPERTIES_MAP.get(StringConstant.KAKAO_KEY_TYPE);
    public static final String KAKAO_KEY = CommonProperties.PROPERTIES_MAP.get(StringConstant.KAKAO_KEY);

    public static final String SEARCH_PLACE_KEYWORD = "/v2/local/search/keyword.json";

    /**
     * Desc : kakao_get, no parameter
     */
    public static JSONObject get(String uri) throws Exception {
        return get(uri, null);
    }

    /**
     * Desc : kakao_get, parameter
     */
    public static JSONObject get(String uri, HashMap<String, Object> paramMap) throws Exception {
        // 파라미터 세팅
        String paramStr = "";
        if (paramMap != null) {
            paramStr += "?";
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                paramStr += key + "=" + value + "&";
            }
            paramStr = paramStr.substring(0, paramStr.length() -1);
        }

        String url = KAKAO_DOMAIN + uri + paramStr;
        logger.debug("[KakaoApi] get url : {}", url);

        String responseStr = ApiSender.get(url, getHeaderMap());
        JSONObject response = new ObjectMapper().readValue(responseStr, JSONObject.class);
        return response;
    }

    /**
     * Desc : 공통 헤더 세팅 (인증키)
     */
    public static HashMap<String, Object> getHeaderMap() {
        String authorization = KEY_TYPE + StringUtils.SPACE + KAKAO_KEY;

        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put(HttpConstant.AUTHORIZATION, authorization);
        return headerMap;
    }
}
