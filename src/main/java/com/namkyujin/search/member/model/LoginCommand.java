package com.namkyujin.search.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class LoginCommand {
    private final String loginId;
    private final String password;
}
