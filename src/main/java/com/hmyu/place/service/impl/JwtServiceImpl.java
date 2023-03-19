package com.hmyu.place.service.impl;

import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class JwtServiceImpl implements JwtService {
    private final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static final String SALT = "hmyuSecret";
    public static final int JWT_EXPIRATION_TIME = 30 * 60 * 1000;				// JWT 30분

    /**
     * Desc : 토큰 생성
     */
    public <T> String create(String key, T data, String subject) throws Exception{
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .compact();


        return jwt;
    }

    /**
     * Desc : Claims 파싱
     */
    public Jws<Claims> parseClaims(String token) throws UnauthorizedException {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return claims;
    }

    /**
     * Desc : 토큰 정보 가져오기
     */
    public HashMap<String, Object> getClaim(String key) throws UnauthorizedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader(HttpConstant.AUTHORIZATION);
        Jws<Claims> claims = parseClaims(jwt);

        HashMap<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
        return value;
    }

    /**
     * Desc : 토큰 만료 시간 체크, 사용 여부 확인
     */
    public boolean checkTokenUsable(String token) throws UnauthorizedException {
        Jws<Claims> claims = parseClaims(token);

        long regDate = (long) claims.getHeader().get("regDate");

        /* 현재 시간과 토큰 생성시간 간에 시차 계산 */
        long elapsedTime =  System.currentTimeMillis() - regDate;

        /* 만료 설정 시간값을 비교하여 만료 여부 판단 */
        if (elapsedTime > JWT_EXPIRATION_TIME) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * Desc : byte[] key 생성
     */
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return key;
    }
}
