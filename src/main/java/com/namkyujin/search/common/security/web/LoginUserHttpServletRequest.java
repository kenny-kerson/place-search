package com.namkyujin.search.common.security.web;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class LoginUserHttpServletRequest extends HttpServletRequestWrapper {
    private final LoginUserPrincipalWrapper loginUserPrincipal;

    public LoginUserHttpServletRequest(HttpServletRequest request, LoginUserPrincipal loginUserPrincipal) {
        super(request);
        this.loginUserPrincipal = new LoginUserPrincipalWrapper(loginUserPrincipal);
    }

    @Override
    public Principal getUserPrincipal() {
        return loginUserPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return loginUserPrincipal.hasRole(role);
    }



    @RequiredArgsConstructor
    public static class LoginUserPrincipalWrapper implements Principal {
        private final LoginUserPrincipal principal;

        public LoginUserPrincipal getPrincipal() {
            return principal;
        }

        public boolean hasRole(String role) {
            return principal.hasRole(role);
        }

        @Override
        public String getName() {
            return principal.getName();
        }
    }
}
