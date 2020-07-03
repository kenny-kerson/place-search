package com.namkyujin.search.common.security.web;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserPrincipal {
    public static final String ROLE_USER = "ROLE_USER";

    private final LoginUser loginUser;

    public String getName() {
        return String.valueOf(loginUser.getMemberId());
    }

    public boolean hasRole(String role) {
        return ROLE_USER.equals(role);
    }
}
