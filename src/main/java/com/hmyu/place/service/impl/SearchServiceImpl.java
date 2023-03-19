package com.hmyu.place.service.impl;

import com.hmyu.place.service.SearchService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.search.ReqSearchPlaceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    private final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    /**
     * Desc : 장소 검색
     */
    @Override
    public ResponseVo getSearchPlace(ReqSearchPlaceVo vo) {
        ResponseVo resVo = new ResponseVo();

        return resVo;
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @Override
    public ResponseVo getSearchKeyword() {
        ResponseVo resVo = new ResponseVo();

        return resVo;
    }
}
