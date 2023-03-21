package com.hmyu.place.vo.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "검색 키워드 목록 응답부")
public class ResSearchKeywordVo {
    @ApiModelProperty(position = 1, value = "키워드" , required = true , example = "투썸플레이스")
    private String keyword;
    @ApiModelProperty(position = 2, value = "검색한 횟수" , required = true , example = "3")
    private int count;
    @ApiModelProperty(position = 3, value = "검색한 사용자수" , required = true , example = "2")
    private int userCount;
}
