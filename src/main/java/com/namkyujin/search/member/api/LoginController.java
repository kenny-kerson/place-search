package com.namkyujin.search.member.api;

import com.namkyujin.search.common.model.CommonResponse;
import com.namkyujin.search.member.application.LoginService;
import com.namkyujin.search.member.model.LoginCommand;
import com.namkyujin.search.member.model.LoginRequest;
import com.namkyujin.search.member.model.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    protected static final String LOGIN_PATH = "/login";
    @PostMapping(LOGIN_PATH)
    public CommonResponse<LoginResult> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        LoginCommand loginCommand = LoginCommand.of(loginRequest.getId(), loginRequest.getPassword());
        LoginResult loginResult = loginService.login(loginCommand);

        return CommonResponse.of(loginResult);
    }
}
