package com.namkyujin.search.member.application;

import com.namkyujin.search.common.security.auth.JwtTokenProvider;
import com.namkyujin.search.member.domain.Member;
import com.namkyujin.search.member.domain.MemberRepository;
import com.namkyujin.search.member.model.LoginCommand;
import com.namkyujin.search.member.model.LoginFailedException;
import com.namkyujin.search.member.model.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResult login(LoginCommand loginCommand) {
        Member member = memberRepository.findByLoginId(loginCommand.getLoginId())
                .orElseThrow(LoginFailedException::notExistMember);

        boolean matchedPassword = member.matchPassword(passwordEncoder.encode(loginCommand.getPassword()));
        if (!matchedPassword) {
            throw LoginFailedException.invalidLoginInfo();
        }

        return LoginResult.of(jwtTokenProvider.createToken(member.getId()));
    }
}
