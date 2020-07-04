package com.namkyujin.search.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
public class ApiClient {
    protected final ApiClientProperties.InstanceProperties properties;
    protected final RestTemplate restTemplate;

    public ApiClient(ApiClientProperties.InstanceProperties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = create(properties, restTemplateBuilder);
    }

    protected void handleException(URI uri, RestClientResponseException e) {
        log.error("{} Integration Fail - [Path: {}, ResponseCode: {}, ResponseBody: {}]",
                ClassUtils.getShortName(this.getClass()), uri, e.getRawStatusCode(), e.getResponseBodyAsString(), e);
    }

    private RestTemplate create(ApiClientProperties.InstanceProperties properties, RestTemplateBuilder restTemplateBuilder) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme(properties.getScheme())
                .host(properties.getHost());

        RestTemplate restTemplate = restTemplateBuilder
                .requestFactory(() -> buildHttpRequestFactory(properties))
                .uriTemplateHandler(new DefaultUriBuilderFactory(uriComponentsBuilder))
                .build();

        restTemplate.setInterceptors(buildDefaultHeaders(properties.getHeaders()));

        return restTemplate;
    }

    private ClientHttpRequestFactory buildHttpRequestFactory(ApiClientProperties.InstanceProperties properties) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(properties.getConnectionTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());
        requestFactory.setHttpClient(buildCloseableHttpClient(properties.getMaxTotal(), properties.getDefaultMaxPerRoute()));
        return requestFactory;
    }

    private CloseableHttpClient buildCloseableHttpClient(int maxTotal, int defaultMaxPerRoute) {
        return HttpClientBuilder.create()
                .setMaxConnTotal(maxTotal)
                .setMaxConnPerRoute(defaultMaxPerRoute)
                .build();
    }

    private List<ClientHttpRequestInterceptor> buildDefaultHeaders(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(HttpHeaderInterceptor::of)
                .collect(toList());
    }


    private static class HttpHeaderInterceptor implements ClientHttpRequestInterceptor {
        private String headerName;
        private String headerValue;


        public static HttpHeaderInterceptor of(Map.Entry<String, String> entry) {
            return new HttpHeaderInterceptor(entry.getKey(), entry.getValue());
        }

        public HttpHeaderInterceptor(String headerName, String headerValue) {
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            request.getHeaders().set(headerName, headerValue);
            return execution.execute(request, body);
        }
    }
}
