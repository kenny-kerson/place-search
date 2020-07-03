package com.namkyujin.search.common.security.auth;

public class AuthenticationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "인증에 실패하였습니다.";
    private static final AuthenticationException INSTANCE = new AuthenticationException();

    public AuthenticationException() {
        super(DEFAULT_MESSAGE);
    }

    public static AuthenticationException instance() {
        return INSTANCE;
    }
}
