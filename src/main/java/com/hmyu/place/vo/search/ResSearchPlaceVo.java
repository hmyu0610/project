package com.hmyu.place.vo.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "장소 검색 응답부")
public class ResSearchPlaceVo {
    @ApiModelProperty(position = 1, value = "장소이름" , required = true , example = "투썸플레이스 역삼역점")
    private String placeName;
    @ApiModelProperty(position = 2, value = "카테고리" , required = false , example = "카페")
    private String category;
    @ApiModelProperty(position = 3, value = "지번주소" , required = false , example = "서울 강남구 역삼동 678-10")
    private String address;
    @ApiModelProperty(position = 4, value = "도로명주소" , required = false , example = "서울 강남구 테헤란로27길 16")
    private String roadAddress;
}
