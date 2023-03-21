package com.hmyu.place.service.impl;

import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.mapper.SearchMapper;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.service.SearchService;
import com.hmyu.place.thirdparty.SearchPlace;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.search.ResSearchKeywordVo;
import com.hmyu.place.vo.search.ResSearchPlaceVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final JwtService jwtService;
    private final SearchMapper searchMapper;

    /**
     * Desc : 장소 검색
     */
    @Override
    public ResponseVo getSearchPlace(String keyword) throws Exception {
        ResponseVo resVo = new ResponseVo();

        if (StringUtils.isEmpty(keyword)) {
            resVo.setResultMessage(MessageConstant.INVALID_PARAMETER, "keyword");
            return resVo;
        }

        // 검색 키워드 저장
        String userUuid = jwtService.getUserUuid();
        HashMap<String, Object> insertMap = new HashMap<>();
        insertMap.put("userUuid", userUuid);
        insertMap.put("keyword", keyword);

        int insertCnt = searchMapper.insertSearchKeywordHistory(insertMap);
        if (insertCnt == 0) {
            resVo.setResultMessage(MessageConstant.INSERT_KEYWORD_ERROR);
            return resVo;
        }

        // 장소 검색
        List<ResSearchPlaceVo> placeList = new SearchPlace().getSarchPlaceResult(keyword);
        logger.debug("[getSearchPlace] placeList : {}", placeList);
        if (placeList == null || placeList.isEmpty()) {
            resVo.setResultMessage(MessageConstant.NOT_EXISTS_DATA);
        }
        resVo.setData(placeList);

        return resVo;
    }

    /**
     * Desc : 검색 키워드 목록
     */
    @Override
    public ResponseVo getSearchKeyword() {
        ResponseVo resVo = new ResponseVo();

        List<ResSearchKeywordVo> keywordList = searchMapper.selectKeywordHistoryList();
        if (keywordList == null || keywordList.isEmpty()) {
            resVo.setResultMessage(MessageConstant.NOT_EXISTS_DATA);
        }
        resVo.setData(keywordList);

        return resVo;
    }
}
