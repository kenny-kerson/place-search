package com.namkyujin.search.search.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class SearchResult {
    private final int pageSize;
    private final int totalCount;
    private final List<Place> places;


    @Getter
    @Builder
    public static class Place {
        private String name;

        private String address;

        private String roadAddress;

        private String phone;

        /**
         * 경도
         */
        private double longitude;

        /**
         * 위도
         */
        private double latitude;

        private String link;
    }


    public static class LinkBuilder {
        private static final String LINK_WITH_NAME_FORMAT = "https://map.kakao.com/link/map/%s,%f,%f";
        private static final String LINK_FORMAT = "https://map.kakao.com/link/map/%f,%f";

        private double latitude;
        private double longitude;
        private String placeName;

        private LinkBuilder(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static LinkBuilder builder(double latitude, double longitude) {
            return new LinkBuilder(latitude, longitude);
        }

        public LinkBuilder withName(String placeName) {
            try {
                this.placeName = URLEncoder.encode(placeName, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                // do nothing
            }

            return this;
        }

        public String build() {
            if (StringUtils.isEmpty(placeName)) {
                return String.format(LINK_FORMAT, latitude, longitude);
            }
            return String.format(LINK_WITH_NAME_FORMAT, placeName, latitude, longitude);
        }
    }
}
