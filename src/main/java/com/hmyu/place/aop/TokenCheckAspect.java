package com.hmyu.place.aop;

import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.exception.ExpiredTokenException;
import com.hmyu.place.exception.InvalidTokenException;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.mapper.UserMapper;
import com.hmyu.place.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RequiredArgsConstructor
@Aspect
@Component
public class TokenCheckAspect {
    private static final Logger logger = LoggerFactory.getLogger(TokenCheckAspect.class);

    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Before("@annotation(TokenCheck)")
    public void tokenCheckAspect() throws Throwable {
        logger.debug("[tokenCheckAspect]");

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();

        /**    헤더 토큰 추출 및 맵 변환	*/
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            logger.error("[TokenCheckAspect] empty header");
            throw new UnauthorizedException();    // 토큰 없음
        }

        String token = null;
        try {
            token = request.getHeader(HttpConstant.AUTHORIZATION);
        } catch (Exception e) {
            logger.error("[TokenCheckAspect] get token error");
            throw new UnauthorizedException();    // 토큰 없음
        }

        // 토큰 만료일자 확인
        if (!jwtService.checkTokenUsable(token)) {
            logger.error("[TokenCheckAspect] expired token");
            throw new ExpiredTokenException();     // 만료 토큰
        }

        HashMap<String, Object> userInfo = null;
        try {
            userInfo = jwtService.getClaim();
        } catch (Exception e) {
            logger.error("[TokenCheckAspect] get userInfo error");
            throw new InvalidTokenException();   // 잘못된 토큰
        }

        String userUuid = userInfo.get("userUuid").toString();
        String email = userInfo.get("email").toString();
        if (!userUuid.equals(userMapper.selectUserUuidByEmail(email))) {
            logger.error("[TokenCheckAspect] invalid userInfo error");
            throw new InvalidTokenException();   // 잘못된 토큰
        }
    }
}
