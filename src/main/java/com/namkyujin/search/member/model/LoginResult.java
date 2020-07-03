package com.namkyujin.search.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class LoginResult {
    private final String token;
}
