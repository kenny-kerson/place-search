package com.namkyujin.search.support;

import com.namkyujin.search.infrastructure.client.KakaoApiSpec;
import com.namkyujin.search.infrastructure.client.kakao.KakaoKeywordSearchRequest;
import com.namkyujin.search.infrastructure.client.kakao.KakaoKeywordSearchResponse;

public class StubKakaoApiSpec implements KakaoApiSpec {
    private static final String RESOURCE_FILE_PATH = "data/kakao-response.json";

    private KakaoKeywordSearchResponse kakaoKeywordSearchResponse;

    public StubKakaoApiSpec() {
        kakaoKeywordSearchResponse = TestResourceReader
                .read(RESOURCE_FILE_PATH, KakaoKeywordSearchResponse.class);
    }

    @Override
    public KakaoKeywordSearchResponse search(KakaoKeywordSearchRequest request) {
        return kakaoKeywordSearchResponse;
    }
}
