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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
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

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo missingParamHandler(MissingServletRequestParameterException e) {
		logger.error("[missingParamHandler] " + e, e.getParameterName());
		ResponseVo resVo = new ResponseVo(MessageConstant.INVALID_PARAMETER, e.getParameterName());
		return resVo;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo methodNotSupportedHandler(HttpRequestMethodNotSupportedException e) {
		logger.error("[methodNotSupportedHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.METHOD_NOT_ALLOWED);
		return resVo;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@RequestMapping(produces = HttpConstant.APPLICATION_JSON_UTF8)
	public ResponseVo notFoundHandler(NoHandlerFoundException e) {
		logger.error("[notFoundHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.NOT_FOUND);
		return resVo;
	}

}
