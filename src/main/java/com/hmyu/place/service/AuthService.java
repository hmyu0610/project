package com.hmyu.place.service;

import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.auth.ReqTokenVo;

public interface AuthService {
    ResponseVo createToken(ReqTokenVo vo) throws Exception;
}
