package com.namkyujin.search.member.model;

public class LoginFailedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "로그인에 실패했습니다.";
    private static final String INVALID_LOGIN_INFO_MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";
    private static final String NOT_EXIST_MEMBER_MESSAGE = "존재하지 않는 회원입니다.";

    public LoginFailedException() {
        super(DEFAULT_MESSAGE);
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public static LoginFailedException invalidLoginInfo() {
        return new LoginFailedException(INVALID_LOGIN_INFO_MESSAGE);
    }

    public static LoginFailedException notExistMember() {
        return new LoginFailedException(NOT_EXIST_MEMBER_MESSAGE);
    }
}
