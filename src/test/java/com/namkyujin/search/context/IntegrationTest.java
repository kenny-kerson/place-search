package com.namkyujin.search.context;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;


@Tag("integration")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {
    @Autowired
    protected TestRestTemplate localServerRestTemplate;

    protected <T> ResponseEntity get(String url, @Nullable  HttpEntity<?> entity, Class<T> responseType, Map<String, Object> queryParam) {
        try {
            return localServerRestTemplate.exchange(url, HttpMethod.GET, entity, responseType, queryParam);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    protected <T> ResponseEntity post(String url, @Nullable Object request, Class<T> responseType) {
        try {
            return localServerRestTemplate.postForEntity(url, request, responseType);
        } catch (RestClientResponseException e) {
            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
