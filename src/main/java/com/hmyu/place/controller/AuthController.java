package com.hmyu.place.controller;

import com.hmyu.place.service.AuthService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.auth.ReqTokenVo;
import com.hmyu.place.vo.auth.ResTokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(value = "토큰", tags = {"전체"})
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @ApiOperation(value = "[토큰 발급]", notes = "토큰 발급을 요청한다.", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, tags = {"토큰"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success" , response = ResTokenVo.class)
    })
    @PostMapping("/token")
    public ResponseEntity<ResponseVo> getToken(@RequestBody ReqTokenVo vo) throws Exception {
        return ResponseEntity.ok(authService.createToken(vo));
    }
}
