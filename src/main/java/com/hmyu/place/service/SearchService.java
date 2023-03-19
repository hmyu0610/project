package com.hmyu.place.service;

import com.hmyu.place.vo.ResponseVo;

public interface SearchService {
    /**
     * Desc : 장소 검색
     */
    ResponseVo getSearchPlace(String keyword);

    /**
     * Desc : 검색 키워드 목록
     */
    ResponseVo getSearchKeyword();
}
