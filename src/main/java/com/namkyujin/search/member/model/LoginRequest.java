package com.namkyujin.search.member.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {

    @NotBlank(message = "아이디는 필수 입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;

    private LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static LoginRequest of(String id, String password) {
        return new LoginRequest(id, password);
    }
}
