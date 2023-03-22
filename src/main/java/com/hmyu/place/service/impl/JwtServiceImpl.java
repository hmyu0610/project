package com.hmyu.place.service.impl;

import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.exception.ExpiredTokenException;
import com.hmyu.place.exception.InvalidTokenException;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.service.JwtService;
import io.jsonwebtoken.*;
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

    public static final int JWT_EXPIRATION_TIME = 2 * 60 * 60 * 1000;				// JWT 2시간

    /**
     * Desc : 토큰 생성
     */
    public <T> String create(String key, T data, String subject) throws Exception {
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .claim(StringConstant.CLAIM_KEY, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .compact();
        return jwt;
    }

    /**
     * Desc : Claims 파싱
     */
    public Jws<Claims> parseClaims(String token) throws Exception {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return claims;
    }

    /**
     * Desc : 토큰 정보 가져오기
     */
    public HashMap<String, Object> getClaim() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader(HttpConstant.AUTHORIZATION);
        Jws<Claims> claims = parseClaims(jwt);

        HashMap<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(StringConstant.CLAIM_KEY);
        return value;
    }

    /**
     * Desc : 사용자 식별값 가져오기
     */
    @Override
    public String getUserUuid() throws Exception {
        HashMap<String, Object> value = getClaim();
        String userUuid = value.get("userUuid").toString();
        return userUuid;
    }

    /**
     * Desc : 토큰 만료 시간 체크, 사용 여부 확인
     */
    public boolean checkTokenUsable(String token) throws Exception {
        Jws<Claims> claims = parseClaims(token);

        // 테스트 토큰 검증하지 않도록 처리
        HashMap<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(StringConstant.CLAIM_KEY);
        String userUuid = value.get("userUuid").toString();
        if ("fa592302110e460e81213557224a3671".equals(userUuid)) {
            return true;
        }

        /* 만료 설정 시간값을 비교하여 만료 여부 판단 */
        int nowTime = (int) (new Date().getTime()/1000);
        int tokenTime = (int) (claims.getBody().get("exp"));
        if (nowTime > tokenTime) {
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
            key = StringConstant.SALT.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return key;
    }
}
