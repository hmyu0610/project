package com.hmyu.place.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository
public interface SearchMapper {
    int insertSearchKeywordHistory(HashMap<String, Object> map);
}
