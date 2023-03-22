package com.hmyu.place.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.AbstractTest;
import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.controller.AuthController;
import com.hmyu.place.exception.ExpiredTokenException;
import com.hmyu.place.exception.InvalidTokenException;
import com.hmyu.place.exception.UnauthorizedException;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.vo.auth.ReqTokenVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("[uuid 생성 테스트]")
    @Test
    public void createUuidTest() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    @DisplayName("[토큰 발급 테스트][성공]")
    @Test
    public void createTokenTest() throws Exception {
        ReqTokenVo vo = new ReqTokenVo();
        vo.setEmail("hmyu@kakao.com");
        vo.setName("유혜미");

        mockMvc.perform(
                post("/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsBytes(vo))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andExpect(jsonPath("data.token").isNotEmpty())
                .andDo(print())
        ;
    }

    @DisplayName("[토큰 발급 테스트][실패]")
    @Test
    public void createTokenTest2() throws Exception {
        ReqTokenVo vo = new ReqTokenVo();
        vo.setEmail("");
        vo.setName("테스트3");

        mockMvc.perform(
                post("/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsBytes(vo))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(MessageConstant.INVALID_PARAMETER.getCode()))
                .andExpect(jsonPath("data").isEmpty())
                .andDo(print())
        ;
    }

    @DisplayName("[토큰 검증 테스트]")
    @Test
    public void tokenCheckTest() {
        // 없는 토큰
        String token = "";
        // 정보 오류 토큰
//        String token = "eyJ0eXAiOiJKV1Qi";
        // 만료 토큰
//        String token = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5MjQzNTQwNjkzLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3MjcyYjhlOTI2YjI0MjEyOWYwZmMzMmU5NTliZTM3MyIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiNzI3MmI4ZTkyNmIyNDIxMjlmMGZjMzJlOTU5YmUzNzMiLCJlbWFpbCI6ImhteXVAdGVzdC5jb20iLCJuYW1lIjoi7YWM7Iqk7Yq4MyJ9LCJleHAiOjE2NzkyNDUzNDF9.UoNrCOe4PEpID0srSvzlr4gFUnR2mi1AzulAyZ_keGs";

        try {
            Jws<Claims> claims = jwtService.parseClaims(token);

            HashMap<String, Object> userInfo = (LinkedHashMap<String, Object>) claims.getBody().get("userInfo");
            System.out.println(userInfo);

            boolean isExpired = jwtService.checkTokenUsable(token);
            System.out.println(isExpired);
        } catch (UnauthorizedException e) {
            System.out.println("토큰 없음 오류 발생! : " + e);
        } catch (InvalidTokenException e) {
            System.out.println("토큰 정보 오류 발생! : " + e);
        } catch (ExpiredTokenException e) {
            System.out.println("토큰 만료 오류 발생! : " + e);
        } catch (Exception e) {
            System.out.println("기타 오류 발생! : " + e);
        }
    }
}
