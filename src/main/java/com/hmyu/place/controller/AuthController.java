package com.hmyu.place.controller;

import com.hmyu.place.service.AuthService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.auth.ReqTokenVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<ResponseVo> getToken(@RequestBody ReqTokenVo vo) throws Exception{
        return ResponseEntity.ok(authService.createToken(vo));
    }
}
