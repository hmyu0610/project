package com.hmyu.place.vo.auth;

import lombok.Data;

@Data
public class ResTokenVo {
    private UserInfoVo userInfo;
    private String token;
}
