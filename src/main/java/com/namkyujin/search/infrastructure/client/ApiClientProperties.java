package com.namkyujin.search.infrastructure.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("api-client")
public class ApiClientProperties {
    private InstanceProperties kakao;


    @Setter
    @NoArgsConstructor
    public static class InstanceProperties {
        private static final Integer DEFAULT_MAX_TOTAL = 100;
        private static final Integer DEFAULT_MAX_PER_ROUTE = 100;
        private static final Integer DEFAULT_CONNECTION_TIMEOUT = 1_000;
        private static final Integer DEFAULT_READ_TIMEOUT = 1_000;

        private static final String DEFAULT_SCHEME = "https";

        private Integer maxTotal;
        private Integer defaultMaxPerRoute;
        private Integer connectionTimeout;
        private Integer readTimeout;

        private String scheme;
        private String host;
        private Map<String, String> headers = new HashMap<>();

        public Integer getMaxTotal() {
            return Optional.ofNullable(maxTotal)
                    .orElse(DEFAULT_MAX_TOTAL);
        }

        public Integer getDefaultMaxPerRoute() {
            return Optional.ofNullable(defaultMaxPerRoute)
                    .orElse(DEFAULT_MAX_PER_ROUTE);
        }

        public Integer getConnectionTimeout() {
            return Optional.ofNullable(connectionTimeout)
                    .orElse(DEFAULT_CONNECTION_TIMEOUT);
        }

        public Integer getReadTimeout() {
            return Optional.ofNullable(readTimeout)
                    .orElse(DEFAULT_READ_TIMEOUT);
        }

        public String getScheme() {
            return Optional.ofNullable(scheme)
                    .orElse(DEFAULT_SCHEME);
        }

        public String getHost() {
            return host;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }
}
