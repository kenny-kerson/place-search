package com.namkyujin.search.infrastructure.client.kakao;

import com.namkyujin.search.infrastructure.circuit.CircuitBreaker;
import com.namkyujin.search.infrastructure.circuit.CircuitProperties;
import com.namkyujin.search.infrastructure.client.KakaoApiSpec;

public class KakaoApiCircuitClient extends CircuitBreaker<KakaoKeywordSearchResponse> implements KakaoApiSpec {
    private final KakaoApiClient kakaoApiClient;

    public KakaoApiCircuitClient(CircuitProperties circuitProperties, KakaoApiClient kakaoApiClient) {
        super(circuitProperties);
        this.kakaoApiClient = kakaoApiClient;
    }

    @Override
    public KakaoKeywordSearchResponse search(KakaoKeywordSearchRequest kakaoBookSearchRequest) {
        return invoke(() -> kakaoApiClient.search(kakaoBookSearchRequest));
    }
}
