package com.namkyujin.search.infrastructure.client;

import com.namkyujin.search.infrastructure.client.kakao.KakaoKeywordSearchRequest;
import com.namkyujin.search.infrastructure.client.kakao.KakaoKeywordSearchResponse;

/**
 * 카카오 API 스펙
 */
public interface KakaoApiSpec {

    /**
     * @see <a href='https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword'>API Docs</a>
     */
    KakaoKeywordSearchResponse search(KakaoKeywordSearchRequest request);
}