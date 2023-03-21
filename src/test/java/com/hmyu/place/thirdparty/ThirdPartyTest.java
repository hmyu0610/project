package com.hmyu.place.thirdparty;

import com.hmyu.place.AbstractTest;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.HashMap;

public class ThirdPartyTest extends AbstractTest {

    @Test
    public void kakaoApiTest() throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();

        // 검색을 원하는 질의어
        String query = "스타벅스";
        paramMap.put("query", query);

        // 한 페이지에 보여질 문서의 개수 (최소: 1, 최대: 45, 기본값: 15)
        int size = 5;
        paramMap.put("size", size);

        JSONObject result = KakaoApi.get(KakaoApi.SEARCH_PLACE_KEYWORD, paramMap);
        System.out.println(result);
    }

    @Test
    public void naverApiTest() throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();

        // 검색을 원하는 질의어
        String query = "스타벅스";
        paramMap.put("query", query);

        // 한 번에 표시할 검색 결과 개수(기본값: 1, 최댓값: 5)
        int display = 5;
        paramMap.put("display", display);

        // 검색 결과 정렬 방법
        // random: 정확도순으로 내림차순 정렬(기본값)
        // comment: 업체 및 기관에 대한 카페, 블로그의 리뷰 개수순으로 내림차순 정렬
        String sort = "comment";
        paramMap.put("sort", sort);

        JSONObject result = NaverApi.get(NaverApi.SEARCH_PLACE_KEYWORD, paramMap);
        System.out.println(result);
    }
}
