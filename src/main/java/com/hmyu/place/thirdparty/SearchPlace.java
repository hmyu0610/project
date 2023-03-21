package com.hmyu.place.thirdparty;

import com.hmyu.place.util.EtcUtil;
import com.hmyu.place.vo.search.ResSearchPlaceVo;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPlace {
    private static final Logger logger = LoggerFactory.getLogger(KakaoApi.class);

    private final int searchSize = 5;

    /**
     * Desc : 검색 결과 취합
     */
    public List<ResSearchPlaceVo> getSarchPlaceResult(String keyword) {
        List<ResSearchPlaceVo> resultList = new LinkedList<>();

        List<HashMap<String, Object>> kakaoPlaceList = getKakaoPlaceList(keyword, 1);
        List<HashMap<String, Object>> naverPlaceList = getNaverPlaceList(keyword, 2);

        boolean isEmptyKakao = kakaoPlaceList == null || kakaoPlaceList.isEmpty();
        boolean isEmptyNaver = naverPlaceList == null || naverPlaceList.isEmpty();

        if (isEmptyKakao && isEmptyNaver) {
            return resultList;
        } else if (!isEmptyKakao && isEmptyNaver) {
            resultList = convertList(kakaoPlaceList);
        } else if (isEmptyKakao && !isEmptyNaver) {
            resultList = convertList(naverPlaceList);
        } else {
            List<HashMap<String, Object>> dupPlaceList = getDistinctPlaceList(kakaoPlaceList, naverPlaceList);
            List<HashMap<String, Object>> placeList = new LinkedList<>();
            placeList.addAll(dupPlaceList);
            placeList.addAll(getRemoveDistinctPlaceList(kakaoPlaceList, dupPlaceList));
            placeList.addAll(getRemoveDistinctPlaceList(naverPlaceList, dupPlaceList));
            resultList = convertList(placeList);
        }

        return resultList;
    }

    /**
     * Desc : 카카오 검색
     */
    private List<HashMap<String, Object>> getKakaoPlaceList(String keyword, int sort) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", keyword);
        paramMap.put("size", searchSize);

        List<HashMap<String, Object>> kakaoPlaceList = new LinkedList<>();
        try {
            JSONObject response = KakaoApi.get(KakaoApi.SEARCH_PLACE_KEYWORD, paramMap);
            List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) response.get("documents");

            for (HashMap<String, Object> data : dataList) {
                HashMap<String, Object> kakaoPlace = new HashMap<>();
                kakaoPlace.put("placeName", EtcUtil.getString(data, "place_name"));
                kakaoPlace.put("category", EtcUtil.getString(data, "category_group_name"));
                kakaoPlace.put("address", EtcUtil.getString(data, "address_name"));
                kakaoPlace.put("roadAddress", EtcUtil.getString(data, "road_address_name"));
                kakaoPlace.put("sort", sort);
                kakaoPlaceList.add(kakaoPlace);
            }
        } catch (Exception e) {
            logger.error("[SearchPlace] kakao error : {}" + e, e);
        }

        logger.debug("[SearchPlace] kakao place list : {}", kakaoPlaceList);
        return kakaoPlaceList;
    }

    /**
     * Desc : 네이버 검색
     */
    private List<HashMap<String, Object>> getNaverPlaceList(String keyword, int sort) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", keyword);
        paramMap.put("display", searchSize);

        List<HashMap<String, Object>> naverPlaceList = new LinkedList<>();
        try {
            JSONObject response = NaverApi.get(NaverApi.SEARCH_PLACE_KEYWORD, paramMap);
            List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) response.get("items");

            for (HashMap<String, Object> data : dataList) {
                String placeName = EtcUtil.getString(data, "title");
                if (!StringUtils.isEmpty(placeName)) {
                    placeName = placeName.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", StringUtils.EMPTY);
                }

                String category = data.get("category").toString();
                if (!StringUtils.isEmpty(category)) {
                    category = category.contains(",") ? category.split(",")[0] : category;
                }

                HashMap<String, Object> naverPlace = new HashMap<>();
                naverPlace.put("placeName", placeName);
                naverPlace.put("category", category);
                naverPlace.put("address", EtcUtil.getString(data, "address"));
                naverPlace.put("roadAddress", EtcUtil.getString(data, "roadAddress"));
                naverPlace.put("sort", sort);
                naverPlaceList.add(naverPlace);
            }
        } catch (Exception e) {
            logger.error("[SearchPlace] naver error : {}" + e, e);
        }

        logger.debug("[SearchPlace] naver place list : {}", naverPlaceList);
        return naverPlaceList;
    }

    /**
     * Desc : 중복 반환
     */
    private List<HashMap<String, Object>> getDistinctPlaceList(List<HashMap<String, Object>> standardList, List<HashMap<String, Object>> targetList) {
        List<HashMap<String, Object>> distinctPlaceList = new LinkedList<>();

        for (HashMap<String, Object> standard : standardList) {
            String sName = EtcUtil.removeSpace(standard.get("placeName").toString());
            String sAddress = EtcUtil.removeSpace(standard.get("address").toString());
            String sRoadAddress = EtcUtil.removeSpace(standard.get("roadAddress").toString());

            boolean isDup = false;
            for (HashMap<String, Object> target : targetList) {
                String tName = EtcUtil.removeSpace(target.get("placeName").toString());
                String tAddress = EtcUtil.removeSpace(target.get("address").toString());
                String tRoadAddress = EtcUtil.removeSpace(target.get("roadAddress").toString());

                if (sName.equals(tName)) {
                    isDup = true;
                }
                if (isDup && sAddress.length() != tAddress.length()) {
                    boolean isAddressDup = sAddress.length() >= tAddress.length()? sAddress.contentEquals(tAddress) : tAddress.contentEquals(sAddress);
                    if (isAddressDup) {
                        standard.put("address", sAddress.length() >= tAddress.length()? sAddress : tAddress);
                    }
                }
                if (isDup && sRoadAddress.length() != tRoadAddress.length()) {
                    boolean isRoadAddressDup = sRoadAddress.length() >= tRoadAddress.length()? sRoadAddress.contentEquals(tRoadAddress) : tRoadAddress.contentEquals(sRoadAddress);
                    if (isRoadAddressDup) {
                        standard.put("roadAddress", sRoadAddress.length() >= tRoadAddress.length()? sRoadAddress : tRoadAddress);
                    }
                }

                if (isDup) {
                    standard.put("sort", 0);
                    distinctPlaceList.add(standard);
                    break;
                }
            }
        }

        return distinctPlaceList;
    }

    /**
     * Desc : 중복 제외 반환
     */
    private List<HashMap<String, Object>> getRemoveDistinctPlaceList(List<HashMap<String, Object>> placeList, List<HashMap<String, Object>> dupList) {
        List<HashMap<String, Object>> removeDupPlaceList = new LinkedList<>();

        for (HashMap<String, Object> place : placeList) {
            String pName = EtcUtil.removeSpace(place.get("placeName").toString());
            String pAddress = EtcUtil.removeSpace(place.get("address").toString());
            String pRoadAddress = EtcUtil.removeSpace(place.get("roadAddress").toString());

            boolean isDup = false;
            for (HashMap<String, Object> dup : dupList) {
                String dName = EtcUtil.removeSpace(dup.get("placeName").toString());
                String dAddress = EtcUtil.removeSpace(dup.get("address").toString());
                String dRoadAddress = EtcUtil.removeSpace(dup.get("roadAddress").toString());

                if (pName.equals(dName)) {
                    isDup = true;
                }
                if (isDup && pAddress.length() != dAddress.length()) {
                    boolean isAddressDup = pAddress.length() >= dAddress.length()? pAddress.contains(dAddress) : pAddress.contains(dAddress);
                    if (isAddressDup) {
                        place.put("address", pAddress.length() >= dAddress.length()? pAddress : dAddress);
                    }
                }
                if (isDup && pRoadAddress.length() != dRoadAddress.length()) {
                    boolean isRoadAddressDup = pRoadAddress.length() >= dRoadAddress.length()? pRoadAddress.contains(dRoadAddress) : dRoadAddress.contains(pRoadAddress);
                    if (isRoadAddressDup) {
                        place.put("roadAddress", pRoadAddress.length() >= dRoadAddress.length()? pRoadAddress : dRoadAddress);
                    }
                }
            }

            if (!isDup) {
                removeDupPlaceList.add(place);
            }
        }

        return removeDupPlaceList;
    }

    /**
     * Desc : 결과 데이터 변환
     */
    private List<ResSearchPlaceVo> convertList(List<HashMap<String, Object>> placeList) {
        List<ResSearchPlaceVo> voList = new LinkedList<>();

        for (HashMap<String, Object> place : placeList) {
            ResSearchPlaceVo vo = new ResSearchPlaceVo();
            vo.setPlaceName(EtcUtil.getString(place, "placeName"));
            vo.setCategory(EtcUtil.getString(place, "category"));
            vo.setAddress(EtcUtil.getString(place, "address"));
            vo.setRoadAddress(EtcUtil.getString(place, "roadAddress"));
            voList.add(vo);
        }

        return voList.stream().distinct().collect(Collectors.toList());
    }
}
