package com.namkyujin.search.search.api;

import com.namkyujin.search.common.security.auth.JwtTokenProvider;
import com.namkyujin.search.context.IntegrationTest;
import com.namkyujin.search.infrastructure.client.KakaoApiSpec;
import com.namkyujin.search.support.StubKakaoApiSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class SearchControllerIntegrationTest extends IntegrationTest {
    private static final String DUMMY_TOKEN = "foobar";
    public static final long MEMBER_ID = 1L;

    private static HttpEntity<String> entity;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;


    @TestConfiguration
    static class TestConfig {
        @Bean
        public KakaoApiSpec kakaoApiSpec() {
            return new StubKakaoApiSpec();
        }
    }


    @BeforeEach
    void setUp() {
        given(jwtTokenProvider.getMemberId(DUMMY_TOKEN)).willReturn(MEMBER_ID);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + DUMMY_TOKEN);
        entity = new HttpEntity<>(headers);
    }

    @DisplayName("인증 받지 않은 사용자는 검색할 수 없다")
    @Test
    void shouldNotAvailableSearchWithoutAuthorization_401() {
        //given
        HttpEntity<Object> emptyEntity = new HttpEntity<>(new HttpHeaders());

        //when
        ResponseEntity<String> response = get(SearchController.BASE_PATH, emptyEntity, String.class, new HashMap<>());

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @DisplayName("keyword 없이 검색할 수 없다")
    @Test
    void shouldKeywordMustIncluded_400() {
        //given
        Map<String, Object> queryParam = toMap(1, 10, null);
        String url = SearchController.BASE_PATH + "?page={page}&size={size}";

        //when
        ResponseEntity<String> response = get(url, entity, String.class, queryParam);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("keyword 는 빈 문자열일 수 없다")
    @Test
    void shouldKeywordNotEmptyString_400() {
        //given
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("keyword", " ");
        String url = SearchController.BASE_PATH + "?keyword={keyword}";

        //when
        ResponseEntity<String> response = get(url, entity, String.class, queryParam);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("page, size 없이 검색 가능하다")
    @Test
    void shouldSearch_200() {
        //given
        Map<String, Object> queryParam = toMap(null, null, "카카오프렌즈");
        String url = SearchController.BASE_PATH + "?keyword={keyword}";

        //when
        ResponseEntity<String> response = get(url, entity, String.class, queryParam);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("응답은 지번, 도로명주소, 전화번호, Link 를 포함해야한다")
    @Test
    void shouldIncludeSpecificField_200() {
        //given
        Map<String, Object> queryParam = toMap(1, 10, "카카오프렌즈");
        String url = SearchController.BASE_PATH + "?keyword={keyword}";

        //when
        ResponseEntity<String> response = get(url, entity, String.class, queryParam);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = response.getBody();
        assertThat(body).contains("address");
        assertThat(body).contains("roadAddress");
        assertThat(body).contains("phone");
        assertThat(body).contains("link");
    }

    private Map<String, Object> toMap(@Nullable Integer page, @Nullable Integer size, @Nullable String query) {
        Map<String, Object> queryParams = new HashMap<>();
        if (Objects.nonNull(size)) queryParams.put("page", page);
        if (Objects.nonNull(size)) queryParams.put("size", size);
        if (StringUtils.hasText(query)) queryParams.put("keyword", query);
        return queryParams;
    }
}