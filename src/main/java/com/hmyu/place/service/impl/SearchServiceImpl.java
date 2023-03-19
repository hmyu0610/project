package com.hmyu.place.service.impl;

import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.service.SearchService;
import com.hmyu.place.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseVo getSearchPlace(String keyword) {
        ResponseVo resVo = new ResponseVo();

        if (StringUtils.isEmpty(keyword)) {
            resVo.setResultMessage(MessageConstant.INVALID_PARAMETER, "keyword");
            return resVo;
        }

        // TODO :: 검색 키워드 저장

        // TODO :: 카카오 api

        // TODO :: 네이버 api

        // TODO :: 중복 추리기

        // TODO :: 결과 세팅

        return resVo;
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @Override
    public ResponseVo getSearchKeyword() {
        ResponseVo resVo = new ResponseVo();

        // TODO :: 키워드 히스토리 조회

        return resVo;
    }
}
