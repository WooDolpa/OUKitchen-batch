package com.project.toy.batch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
public class KakaoDto {

    /**
     * 카카오 키워드 지역 검색 요청
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KeywordPlaceReqDto implements Serializable {

        private static final long serialVersionUID = -4194148862328751357L;

        private String query;                               // 검색어
        private String categoryGroupCode;                   // 카테고리 그룹 코드
        private double x;                                   // 중심 좌표 x
        private double y;                                   // 중심 좌표 y
        private int radius;                                 // 중심 좌표로부터 반경 거리
        private String rect;                                // 지도 화면 내 검색시 등, 제한검색시 사용
        private int page;                                   // 페이지 넘버 1~45 사이, default 1
        private int size;                                   // 한 페이지에 보여질 수 1~15, default 15
        private String sort;                                // 정렬
    }

    /**
     * 카카오 키워드 지역 검색 응답
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KeywordPlaceResDto implements Serializable {

        private static final long serialVersionUID = 4984968976989004705L;

        private MetaDto meta;
        private List<DocumentsDto> documents;

    }

    /**
     * 메타 데이터
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MetaDto implements Serializable{

        private static final long serialVersionUID = 3462670961506350170L;
        @JsonProperty("same_name")
        private SameNameDto sameName;               // 질의어의 지역 및 키워드 분석 정보
        @JsonProperty("total_count")
        private int totalCount;                     // 검색에 검색된 문서 수
        @JsonProperty("pageable_count")
        private int pageableCount;                  // total_count 중 노출 가능 문서 수
        @JsonProperty("is_end")
        private boolean isEnd;                       // 현재 페이지가 마지막 페이지인지 여부
    }

    /**
     * 키워드 분석 정보
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SameNameDto implements Serializable {

        private static final long serialVersionUID = 2722449006732080511L;

        private String[] region;                    // 질의어에서 인식된 지역의 리스트
        private String keyword;                     // 질의어에서 지역 정보를 제외한 키워드
        @JsonProperty("selected_region")
        private String selectedRegion;              // 인식된 지역 리스트 중, 현재 검색어 사용된 지역 정보
    }

    /**
     * 장소 정보
     *
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DocumentsDto implements Serializable {

        private static final long serialVersionUID = -7867653477352392916L;

        private String id;                          // 장소 ID
        @JsonProperty("place_name")
        private String placeName;                   // 장소명
        @JsonProperty("category_name")
        private String categoryName;                // 카테고리 이름
        @JsonProperty("category_group_code")
        private String categoryGroupCode;           // 카테고리 그룹 코드
        @JsonProperty("category_group_name")
        private String categoryGroupName;           // 카테고리 그룹 명
        private String phone;                       // 전화번호
        @JsonProperty("address_name")
        private String addressName;                 // 전체 지번 주소
        @JsonProperty("road_address_name")
        private String roadAddressName;             // 전체 도로명 주소
        private double x;                           // x 좌표
        private double y;                           // y 좌표
        @JsonProperty("place_url")
        private String placeUrl;                    // 상세페이지 URL
        private String distance;                    // 중심좌표까지 거리
    }

}
