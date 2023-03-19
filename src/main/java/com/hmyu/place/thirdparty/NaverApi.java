package com.hmyu.place.thirdparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.constant.CommonProperties;
import com.hmyu.place.constant.StringConstant;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NaverApi {
    private static final Logger logger = LoggerFactory.getLogger(ApiSender.class);

    public static final String CLIENT_ID_KEY = "X-Naver-Client-Id";
    public static final String CLIENT_SECRET_KEY = "X-Naver-Client-Secret";
    public static final String NAVER_DOMAIN = CommonProperties.PROPERTIES_MAP.get(StringConstant.NAVER_DOMAIN);
    public static final String SEARCH_PLACE_KEYWORD = "/v1/search/local.json";

    /**
     * Desc : naver_get, no parameter
     */
    public static JSONObject get(String uri) throws Exception {
        return get(uri, null);
    }

    /**
     * Desc : naver_get, parameter
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

        String url = NAVER_DOMAIN + uri + paramStr;
        String resultStr = ApiSender.get(url, getHeaderMap());
        return new ObjectMapper().readValue(resultStr, JSONObject.class);
    }

    /**
     * Desc : 공통 헤더 세팅 (인증키)
     */
    public static HashMap<String, Object> getHeaderMap() {
        String clientId = CommonProperties.PROPERTIES_MAP.get(StringConstant.NAVER_CLIENT_ID);
        String clientSecret = CommonProperties.PROPERTIES_MAP.get(StringConstant.NAVER_CLIENT_SECRET);

        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put(CLIENT_ID_KEY, clientId);
        headerMap.put(CLIENT_SECRET_KEY, clientSecret);
        return headerMap;
    }
}
