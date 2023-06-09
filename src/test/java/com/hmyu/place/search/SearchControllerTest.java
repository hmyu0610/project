package com.hmyu.place.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.AbstractTest;
import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.controller.SearchController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest extends AbstractTest {

    @Autowired
    private SearchController searchController;

    @Before
    public void before() throws Exception {
        super.setup(searchController);
        this.om = new ObjectMapper();
    }

    @Test
    @DisplayName("[장소 검색 테스트][권한오류]")
    public void searchPlaceBasicTest() throws Exception {
        mockMvc.perform(
                get("/v1/search/place").param("keyword", "제주 스타벅스")
        )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(MessageConstant.UNAUTHORIZED.getCode()))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("[장소 검색 테스트][키워드 없음]")
    public void searchPlaceBasicTest2() throws Exception {
        mockMvc.perform(
                get("/v1/search/place").param("keyword", "")
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NDg5MzA4NzM1LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYTU5MjMwMjExMGU0NjBlODEyMTM1NTcyMjRhMzY3MSIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiZmE1OTIzMDIxMTBlNDYwZTgxMjEzNTU3MjI0YTM2NzEiLCJlbWFpbCI6InRlc3QxQHRlc3QuY29tIiwibmFtZSI6Iu2FjOyKpO2KuDEifSwiZXhwIjoxNjc5NDk2NTA5fQ.Ob-ctxnxCbB2KRimOFeeZ_1APn_uUz5oIF92TO6nu4E")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(MessageConstant.INVALID_PARAMETER.getCode()))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("[장소 검색 테스트][성공]")
    public void searchPlaceBasicTest3() throws Exception {
        mockMvc.perform(
                get("/v1/search/place").param("keyword", "제주 스타벅스")
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NDg5MzA4NzM1LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYTU5MjMwMjExMGU0NjBlODEyMTM1NTcyMjRhMzY3MSIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiZmE1OTIzMDIxMTBlNDYwZTgxMjEzNTU3MjI0YTM2NzEiLCJlbWFpbCI6InRlc3QxQHRlc3QuY29tIiwibmFtZSI6Iu2FjOyKpO2KuDEifSwiZXhwIjoxNjc5NDk2NTA5fQ.Ob-ctxnxCbB2KRimOFeeZ_1APn_uUz5oIF92TO6nu4E")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("[검색 키워드 목록 테스트][권한오류]")
    public void searchKeywordBasicTest() throws Exception {
        mockMvc.perform(
                get("/v1/search/keyword")
        )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(MessageConstant.UNAUTHORIZED.getCode()))
                .andExpect(jsonPath("data").isEmpty())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("[검색 키워드 목록 테스트][성공]")
    public void searchKeywordBasicTest2() throws Exception {
        mockMvc.perform(
                get("/v1/search/keyword")
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjc5NDg5MzA4NzM1LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYTU5MjMwMjExMGU0NjBlODEyMTM1NTcyMjRhMzY3MSIsInVzZXJJbmZvIjp7InVzZXJVdWlkIjoiZmE1OTIzMDIxMTBlNDYwZTgxMjEzNTU3MjI0YTM2NzEiLCJlbWFpbCI6InRlc3QxQHRlc3QuY29tIiwibmFtZSI6Iu2FjOyKpO2KuDEifSwiZXhwIjoxNjc5NDk2NTA5fQ.Ob-ctxnxCbB2KRimOFeeZ_1APn_uUz5oIF92TO6nu4E")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print())
        ;
    }

}
