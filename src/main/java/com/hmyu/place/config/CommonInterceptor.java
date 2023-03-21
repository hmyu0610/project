package com.hmyu.place.config;

import com.hmyu.place.constant.CommonProperties;
import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.util.EtcUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Desc : 공통 인터셉터
 */
public class CommonInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    private final JwtService jwtService;

    public CommonInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * 전처리 핸들러
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpConstant.AUTHORIZATION);
        String requestUri = request.getRequestURI();
        String serverMode = CommonProperties.PROPERTIES_MAP.get(StringConstant.MODE);
        logger.warn("[Interceptor] IP : {}, ServerMode : {}, Request URI : {}", EtcUtil.getClientIp(request), serverMode, requestUri);

        if (StringUtils.isEmpty(token)) {
            request.setAttribute("exceptionClass", "UnauthorizedException");
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        }

        // 토큰 만료일자 확인
        boolean usable = false;
        try {
            usable = jwtService.checkTokenUsable(token);
        } catch (UnauthorizedException e) {
            request.setAttribute("exceptionClass", "UnauthorizedException");
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        }

        if (!usable) {
            logger.error("[TokenCheckAspect] expired token");
            request.setAttribute("exceptionClass", "ExpiredTokenException");
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        }

        return true;
    }

    /**
     * 후처리 핸들러.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

}
