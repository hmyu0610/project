package com.hmyu.place.service;

import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.search.ReqSearchPlaceVo;

public interface SearchService {
    /**
     * Desc : 장소 검색
     */
    ResponseVo getSearchPlace(ReqSearchPlaceVo vo);

    /**
     * Desc : 검색 키워드 목록
     */
    ResponseVo getSearchKeyword();
}
