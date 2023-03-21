package com.hmyu.place.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "토큰 발급 요청부")
public class ReqTokenVo {
    @ApiModelProperty(position = 1, value = "이메일" , required = true, example = "hmyu@kakao.com")
    private String email;
    @ApiModelProperty(position = 2, value = "이름" , required = true, example = "유혜미")
    private String name;
}
