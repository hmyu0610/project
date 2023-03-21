package com.hmyu.place.mapper;

import com.hmyu.place.vo.auth.ReqTokenVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int insertUser(ReqTokenVo vo);
    int selectUserCountByEmail(String email);
    String selectUserUuidByEmail(String email);
}
