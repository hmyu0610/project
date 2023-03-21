package com.hmyu.place.mapper;

import com.hmyu.place.vo.search.ResSearchKeywordVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface SearchMapper {
    int insertSearchKeywordHistory(HashMap<String, Object> map);
    List<ResSearchKeywordVo> selectKeywordHistoryList();
}
