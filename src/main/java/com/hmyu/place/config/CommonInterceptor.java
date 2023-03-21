package com.hmyu.place.config;

import com.hmyu.place.constant.CommonProperties;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.util.EtcUtil;
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
        String requestUri = request.getRequestURI();
        String serverMode = CommonProperties.PROPERTIES_MAP.get(StringConstant.MODE);
        logger.warn("[Interceptor] IP : {}, ServerMode : {}, Request URI : {}", EtcUtil.getClientIp(request), serverMode, requestUri);
        return true;
    }

    /**
     * 후처리 핸들러.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

}
