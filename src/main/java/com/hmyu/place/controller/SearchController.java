package com.hmyu.place.controller;

import com.hmyu.place.aop.TokenCheck;
import com.hmyu.place.constant.StringConstant;
import com.hmyu.place.service.SearchService;
import com.hmyu.place.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(StringConstant.VERSION)
public class SearchController {
    private final SearchService searchService;

    /**
     * Desc : 장소 검색
     */
    @TokenCheck
    @GetMapping("/search/place")
    public ResponseEntity<ResponseVo> getSearchPlace(@RequestParam String keyword) throws Exception {
        ResponseVo resVo = searchService.getSearchPlace(keyword);
        return ResponseEntity.ok(resVo);
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @TokenCheck
    @GetMapping("/search/keyword")
    public ResponseEntity<ResponseVo> getSearchKeyword() throws Exception {
        ResponseVo resVo = searchService.getSearchKeyword();
        return ResponseEntity.ok(resVo);
    }
}
