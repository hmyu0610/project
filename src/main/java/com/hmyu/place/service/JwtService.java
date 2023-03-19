package com.hmyu.place.service;

import com.hmyu.place.exception.InvalidTokenException;
import com.hmyu.place.exception.UnauthorizedException;

import java.util.HashMap;

public interface JwtService {
    /**
     * Desc : 토큰 생성
     */
    <T> String create(String key, T data, String subject) throws Exception;

    /**
     * Desc : 토큰 정보 가져오기
     */
    HashMap<String, Object> getClaim(String key) throws UnauthorizedException;

    /**
     * Desc : 토큰 만료 시간 체크, 사용 여부 확인
     */
    boolean checkTokenUsable(String token) throws InvalidTokenException;
}
