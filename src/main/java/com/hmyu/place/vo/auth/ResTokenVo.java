package com.hmyu.place.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "토큰 발급 요청부")
public class ResTokenVo {
    @ApiModelProperty(position = 1, value = "계정 정보" , required = true, example = "")
    private UserInfoVo userInfo;
    @ApiModelProperty(position = 2, value = "토큰" , required = true, example = "eyJ0eXAiOiJKV1QiLCJydEYXRlIjoxNjc5NDEwODYwM...")
    private String token;
}
