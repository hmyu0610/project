package com.hmyu.place.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.AbstractTest;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.controller.SearchController;
import com.hmyu.place.vo.search.ReqSearchPlaceVo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void searchPlaceBasicTest() throws Exception {
        ReqSearchPlaceVo vo = getReqSearchPlaceVo();
        mockMvc.perform(
                post("/search/place")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsBytes(vo))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andDo(print())
        ;
    }

    public ReqSearchPlaceVo getReqSearchPlaceVo() {
        ReqSearchPlaceVo vo = new ReqSearchPlaceVo();
        vo.setKeyword("keyword");
        return vo;
    }

    @Test
    public void searchKeywordBasicTest() throws Exception {
        mockMvc.perform(
                get("/search/place")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(StringConstant.RESULT).isNotEmpty())
                .andExpect(jsonPath("result.code").value(StringConstant.OK))
                .andDo(print())
        ;
    }

}
