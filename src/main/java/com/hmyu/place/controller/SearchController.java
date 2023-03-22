package com.hmyu.place.controller;

import com.hmyu.place.aop.TokenCheck;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.service.SearchService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.search.ResSearchKeywordVo;
import com.hmyu.place.vo.search.ResSearchPlaceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(StringConstant.VERSION)
@Api(value = "검색", tags = {"전체"})
public class SearchController {
    private final SearchService searchService;

    /**
     * Desc : 장소 검색
     */
    @ApiOperation(value = "[장소 검색]", notes = "키워드로 장소 검색을 요청한다.", httpMethod = "GET", tags = {"검색"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success" , response = ResSearchPlaceVo.class, responseContainer = "List")
    })
    @TokenCheck
    @GetMapping("/search/place")
    public ResponseEntity<ResponseVo> getSearchPlace(@RequestParam String keyword) throws Exception {
        ResponseVo resVo = searchService.getSearchPlace(keyword);
        return ResponseEntity.ok(resVo);
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @ApiOperation(value = "[검색 키워드 목록]", notes = "검색 키워드 목록 조회를 요청한다.", httpMethod = "GET", tags = {"검색"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success" , response = ResSearchKeywordVo.class, responseContainer = "List")
    })
    @TokenCheck
    @GetMapping("/search/keyword")
    public ResponseEntity<ResponseVo> getSearchKeyword() throws Exception {
        ResponseVo resVo = searchService.getSearchKeyword();
        return ResponseEntity.ok(resVo);
    }
}
