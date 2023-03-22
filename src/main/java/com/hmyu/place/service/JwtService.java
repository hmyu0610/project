package com.hmyu.place.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.HashMap;

public interface JwtService {
    /**
     * Desc : 토큰 생성
     */
    <T> String create(String key, T data, String subject) throws Exception;

    /**
     * Desc : Claims 파싱
     */
    Jws<Claims> parseClaims(String token) throws Exception;

    /**
     * Desc : 토큰 정보 가져오기
     */
    HashMap<String, Object> getClaim() throws Exception;

    /**
     * Desc : 사용자 식별값 가져오기
     */
    String getUserUuid() throws Exception;

    /**
     * Desc : 토큰 만료 시간 체크, 사용 여부 확인
     */
    boolean checkTokenUsable(String token) throws Exception;
}
