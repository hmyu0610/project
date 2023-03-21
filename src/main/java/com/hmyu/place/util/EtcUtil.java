package com.hmyu.place.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * Desc : 기타 모음 유틸
 */
public class EtcUtil {

	/**
	 * Desc : 클라이언트 IP 가져오기 
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
	    return ip;
	}

    /**
     * HashMap 에서 key 에 해당하는 value 반환
     */
	public static String getString(HashMap<String, Object> map, String key) {
	    String value = StringUtils.EMPTY;

	    if (!map.containsKey(key)) {
	        return value;
        }

	    return map.get(key).toString();
    }

    /**
     * Desc : 공백 제거
     */
    public static String removeSpace(String str) {
	    return str.replaceAll("\\s+", StringUtils.EMPTY);
    }
}
