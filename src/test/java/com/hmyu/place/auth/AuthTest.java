package com.hmyu.place.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.AbstractTest;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.controller.AuthController;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.vo.auth.ReqTokenVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthTest extends AbstractTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private JwtService jwtService;

    @Before
    public void before() throws Exception {
        super.setup(authController);
        this.om = new ObjectMapper();
    }

    @Test
    public void createUuidTest() throws Exception {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    @Test
    public void createTokenTest() throws Exception {
        ReqTokenVo vo = getReqTokenVo();
        mockMvc.perform(
                post("/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsBytes(vo))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andDo(print())
        ;
    }

    public ReqTokenVo getReqTokenVo() {
        ReqTokenVo vo = new ReqTokenVo();
        vo.setEmail("hmyu@test.com");
        vo.setName("테스트3");
        return vo;
    }

    @Test
    public void tokenCheckTest() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5MjQzNTQwNjkzLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3MjcyYjhlOTI2YjI0MjEyOWYwZmMzMmU5NTliZTM3MyIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiNzI3MmI4ZTkyNmIyNDIxMjlmMGZjMzJlOTU5YmUzNzMiLCJlbWFpbCI6ImhteXVAdGVzdC5jb20iLCJuYW1lIjoi7YWM7Iqk7Yq4MyJ9LCJleHAiOjE2NzkyNDUzNDF9.UoNrCOe4PEpID0srSvzlr4gFUnR2mi1AzulAyZ_keGs";

        Jws<Claims> claims = jwtService.parseClaims(token);

        HashMap<String, Object> userInfo = (LinkedHashMap<String, Object>)claims.getBody().get("userInfo");
        System.out.println(userInfo);

        boolean isExpired = jwtService.checkTokenUsable(token);
        System.out.println(isExpired);
    }
}
