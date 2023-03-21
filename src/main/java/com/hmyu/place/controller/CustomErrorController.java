package com.hmyu.place.controller;

import com.hmyu.place.exception.ExpiredTokenException;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.vo.ResponseVo;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public ResponseVo error(HttpServletRequest request) throws Exception {
        String exception = (String) request.getAttribute("exceptionClass");
        if(exception.equals("UnauthorizedException")){
            throw new UnauthorizedException();
        } else if(exception.equals("ExpiredTokenException")){
            throw new ExpiredTokenException();
        } else {
            throw new Exception();
        }
    }

    public String getErrorPath() {
        return null;
    }
}
