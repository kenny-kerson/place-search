package com.namkyujin.search.search.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LinkBuilderTest {
    private static final double 카카오프렌즈_위도 = 33.450863;
    private static final double 카카오프렌즈_경도 = 126.570723;

    private SearchResult.LinkBuilder sut;

    @DisplayName("한글 장소명은 인코딩한다")
    @Test
    void shouldLinkBuild() throws UnsupportedEncodingException {
        //given
        String placeName = "즐겨찾는곳";

        //when
        String result = sut.builder(카카오프렌즈_위도, 카카오프렌즈_경도)
                .withName(placeName)
                .build();

        //then
        assertThat(result).contains(String.valueOf(카카오프렌즈_위도));
        assertThat(result).contains(String.valueOf(카카오프렌즈_경도));
        assertThat(result).contains(URLEncoder.encode(placeName, StandardCharsets.UTF_8.name()));
    }

    @DisplayName("장소명이 없어도 링크를 생성할 수 있다")
    @Test
    void shouldLinkBuildWithoutPlaceName() {
        //when
        String result = sut.builder(카카오프렌즈_위도, 카카오프렌즈_경도)
                .build();

        //then
        assertThat(result).contains(String.valueOf(카카오프렌즈_위도));
        assertThat(result).contains(String.valueOf(카카오프렌즈_경도));
        assertThat(result).doesNotContain("%s");
    }
}