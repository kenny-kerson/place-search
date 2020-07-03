package com.namkyujin.search.common.security.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUser {
    private long memberId;

    public static LoginUser of(long memberId) {
        return new LoginUser(memberId);
    }
}
