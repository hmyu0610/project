package com.hmyu.place.controller;

import com.hmyu.place.service.SearchService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.search.ReqSearchPlaceVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;

    /**
     * Desc : 장소 검색
     */
    @PostMapping("/place")
    public ResponseEntity<ResponseVo> getSearchPlace(@RequestBody ReqSearchPlaceVo vo) {
        ResponseVo resVo = searchService.getSearchPlace(vo);
        return ResponseEntity.ok(resVo);
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @GetMapping("/keyword")
    public ResponseEntity<ResponseVo> getSearchKeyword() {
        ResponseVo resVo = searchService.getSearchKeyword();
        return ResponseEntity.ok(resVo);
    }
}
