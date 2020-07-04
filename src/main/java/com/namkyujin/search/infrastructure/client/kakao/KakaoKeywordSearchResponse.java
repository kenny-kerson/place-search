package com.namkyujin.search.infrastructure.client.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 카카오 키워드 장소 검색 응답
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoKeywordSearchResponse {
    private List<Document> documents = new ArrayList<>();
    private Meta meta;


    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Document {
        private String id;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        private String phone;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        private Double x;

        private Double y;

        @JsonProperty("place_url")
        private String placeUrl;

        private String distance;
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Meta {
        /**
         * 검색된 문서 수
         */
        @JsonProperty("total_count")
        private Integer totalCount;

        /**
         * total_count 중 노출 가능 문서 수
         */
        @JsonProperty("pageable_count")
        private Integer pageableCount;

        /**
         * 현재 페이지가 마지막 페이지인지 여부, <br>
         * 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
         */
        @JsonProperty("is_end")
        private Boolean isEnd;

        @JsonProperty("same_name")
        private SameName sameName;


        @Getter
        @ToString
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class SameName {
            /**
             * 질의어에 인색된 지역 리스트
             */
            private List<String> region;

            /**
             * 질의어에서 지역 정보를 제외한 키워드
             */
            private String keyword;

            /**
             * 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
             */
            @JsonProperty("selected_region")
            private String selectedRegion;
        }
    }
}
