package com.namkyujin.search.infrastructure.client;

public class IntegrationFailedException extends RuntimeException {
    public IntegrationFailedException(Throwable cause) {
        super(cause);
    }
}
