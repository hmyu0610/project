package com.hmyu.place.exception;

import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Desc : 익셉션 핸들링을 위한 컨트롤러 어드바이스 클래스
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	@ExceptionHandler(Exception.class)
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

	/**
	 * Desc : 사용 권한 오류
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseVo unauthorizedExceptionHandler(UnauthorizedException e) {
		logger.error("[unauthorizedExceptionHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.UNAUTHORIZED);
		return resVo;
	}

	/**
	 * Desc : 잘못된 토큰 오류
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseVo invalidTokenExceptionHandler(InvalidTokenException e) {
		logger.error("[invalidTokenExceptionHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.INVALID_PERMISSION);
		return resVo;
	}

	/**
	 * Desc : 만료 토큰 오류
	 */
	@ExceptionHandler(ExpiredTokenException.class)
	public ResponseVo expiredTokenExceptionHandler(ExpiredTokenException e) {
		logger.error("[expiredTokenExceptionHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.EXPIRED_TOKEN);
		return resVo;
	}

	/**
	 * Desc : 파라미터 미존재 오류
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseVo missingParamHandler(MissingServletRequestParameterException e) {
		logger.error("[missingParamHandler] " + e, e.getParameterName());
		ResponseVo resVo = new ResponseVo(MessageConstant.INVALID_PARAMETER, e.getParameterName());
		return resVo;
	}

	/**
	 * Desc : 405
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseVo methodNotSupportedHandler(HttpRequestMethodNotSupportedException e) {
		logger.error("[methodNotSupportedHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.METHOD_NOT_ALLOWED);
		return resVo;
	}

	/**
	 * Desc : 404
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseVo notFoundHandler(NoHandlerFoundException e) {
		logger.error("[notFoundHandler] " + e);
		ResponseVo resVo = new ResponseVo(MessageConstant.NOT_FOUND);
		return resVo;
	}

}
