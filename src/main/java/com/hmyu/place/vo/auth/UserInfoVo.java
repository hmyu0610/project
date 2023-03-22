package com.hmyu.place.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "토큰 발급 응답부, 계정 정보")
public class UserInfoVo {
    @ApiModelProperty(position = 1, value = "유저 식별값" , required = true, example = "c269b7c297404e0b8a07209b83cf6cca")
    private String userUuid;
    @ApiModelProperty(position = 2, value = "이메일" , required = true, example = "hmyu@kakao.com")
    private String email;
    @ApiModelProperty(position = 3, value = "이름" , required = true, example = "유혜미")
    private String name;
}
