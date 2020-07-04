package com.namkyujin.search.config;

import com.namkyujin.search.infrastructure.client.ApiClientProperties;
import com.namkyujin.search.infrastructure.client.KakaoApiSpec;
import com.namkyujin.search.infrastructure.client.kakao.KakaoApiClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiClientProperties.class})
public class ApiClientConfiguration {

    @Bean
    public KakaoApiSpec kakaoApiCircuitClient(ApiClientProperties apiClientProperties, RestTemplateBuilder restTemplateBuilder) {
        return new KakaoApiClient(apiClientProperties.getKakao(), restTemplateBuilder);
    }
}
