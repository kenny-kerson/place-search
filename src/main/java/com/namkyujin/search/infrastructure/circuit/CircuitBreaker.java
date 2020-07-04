package com.namkyujin.search.infrastructure.circuit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Slf4j
public class CircuitBreaker<T> {
    private CircuitProperties circuitProperties;
    private AtomicInteger failureCount = new AtomicInteger();
    private AtomicLong lastFailureTimeInUnixTime = new AtomicLong();

    public CircuitBreaker(CircuitProperties circuitProperties) {
        this.circuitProperties = circuitProperties;
    }

    public T invoke(Supplier<T> supplier) {
        long invokeTimeInUnixTime = System.currentTimeMillis();
        if (isFailureThreshold()) {
            if (inTimeLimit(invokeTimeInUnixTime)) {
                log.warn("Call not permitted. Threshold = {}, FailureCount = {}, lastFailureTime = {}",
                        circuitProperties.getFailureCountThreshold(), failureCount.get(), lastFailureTimeInUnixTime.get());
                throw new CircuitBreakingException();
            }
            reset();
        }

        long currentLastFailureTimeInUnixTime = lastFailureTimeInUnixTime.get();
        int currentFailureCount = failureCount.get();

        try {
            return supplier.get();
        } catch (Exception exception) {
            lastFailureTimeInUnixTime.compareAndSet(currentLastFailureTimeInUnixTime, System.currentTimeMillis());
            failureCount.compareAndSet(currentFailureCount, currentFailureCount + 1);
            if (isFailureThreshold(failureCount.get())) {
                log.info("CircuitBreaker changed state to OPEN");
            }

            throw exception;
        }
    }

    /* for test */ int getFailureCount() {
        return failureCount.get();
    }

    /* for test */ long getLastFailureTimeInUnixTime() {
        return lastFailureTimeInUnixTime.get();
    }

    private boolean isFailureThreshold() {
        return isFailureThreshold(failureCount.get());
    }

    private boolean isFailureThreshold(int failureCount) {
        return failureCount >= circuitProperties.getFailureCountThreshold();
    }

    private boolean inTimeLimit(long baseTime) {
        long waitTimeInOpenState = circuitProperties.getWaitDurationInOpenState().toMillis();
        return (waitTimeInOpenState + lastFailureTimeInUnixTime.get()) > baseTime;
    }

    private void reset() {
        failureCount.set(0);
        lastFailureTimeInUnixTime.set(0);
        log.info("CircuitBreaker changed state to CLOSE");
    }
}
