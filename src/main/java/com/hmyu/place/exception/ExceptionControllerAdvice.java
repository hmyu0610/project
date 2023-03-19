package com.hmyu.place.exception;

import com.hmyu.place.constant.HttpConstant;
import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Desc : 익셉션 핸들링을 위한 컨트롤러 어드바이스 클래스
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo exceptionHandler(Exception e) throws Exception {
		logger.error("[ExceptionControllerAdvice] " + e, e);
		
		if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")
				|| StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "파이프")) {
			logger.info("[ExceptionControllerAdvice] IOException cuase :" + ExceptionUtils.getRootCauseMessage(e));
			logger.info("[ExceptionControllerAdvice] socket is closed, cannot return any response.");
			return null;
		}

		ResponseVo resVo = new ResponseVo(MessageConstant.SERVER_ERROR);
        return resVo;
	}

	@ExceptionHandler(MethodNotAllowedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		logger.error("[handleHttpRequestMethodNotSupported] " + e, e);
		ResponseVo resVo = new ResponseVo(MessageConstant.METHOD_NOT_ALLOWED);
		return resVo;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo handleNoHandlerFoundException(NoHandlerFoundException e) {
		logger.error("[handleHttpRequestMethodNotSupported] " + e, e);
		ResponseVo resVo = new ResponseVo(MessageConstant.NOT_FOUND);
		return resVo;
	}

}
