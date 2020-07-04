package com.namkyujin.search.infrastructure.circuit;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Optional;

@Setter
@ConfigurationProperties("circuit")
public class CircuitProperties {
    private static final Duration DEFAULT_WAIT_DURATION_IN_OPEN_STATE = Duration.ofSeconds(3);
    private static final Integer DEFAULT_FAILURE_COUNT_THRESHOLD = 10;

    private Duration waitDurationInOpenState;
    private Integer failureCountThreshold;

    public Duration getWaitDurationInOpenState() {
        return Optional.ofNullable(waitDurationInOpenState)
                .orElse(DEFAULT_WAIT_DURATION_IN_OPEN_STATE);
    }

    public int getFailureCountThreshold() {
        return Optional.ofNullable(failureCountThreshold)
                .orElse(DEFAULT_FAILURE_COUNT_THRESHOLD);
    }
}
