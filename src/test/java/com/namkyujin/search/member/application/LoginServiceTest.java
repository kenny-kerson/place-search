package com.namkyujin.search.member.application;

import com.namkyujin.search.common.security.auth.JwtTokenProvider;
import com.namkyujin.search.member.domain.Member;
import com.namkyujin.search.member.domain.MemberRepository;
import com.namkyujin.search.member.model.LoginCommand;
import com.namkyujin.search.member.model.LoginFailedException;
import com.namkyujin.search.member.model.LoginResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoginServiceTest {
    private LoginService sut;
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);

        sut = new LoginService(memberRepository, passwordEncoder, jwtTokenProvider);
    }

    @DisplayName("회원이 존재하지 않으면 예외가 발생한다")
    @Test
    void shouldOccurredExceptionIfMemberIsNotExist() {
        //given
        LoginCommand command = LoginCommand.of("foo", "bar");
        given(memberRepository.findByLoginId(command.getLoginId()))
                .willReturn(Optional.empty());

        //when
        Throwable result = catchThrowable(() -> sut.login(command));

        //then
        verify(memberRepository).findByLoginId(command.getLoginId());
        assertThat(result).isInstanceOf(LoginFailedException.class);
    }

    @DisplayName("비밀번호가 잘못됐다면 예외가 발생한다")
    @Test
    void shouldOccurredExceptionIfPasswordIsWrong() {
        //given
        LoginCommand command = LoginCommand.of("foo", "bar");
        String newPassword = command.getPassword() + "new";
        given(memberRepository.findByLoginId(command.getLoginId()))
                .willReturn(Optional.of(Member.builder()
                        .loginId(command.getLoginId())
                        .password(newPassword)
                        .build()));

        given(passwordEncoder.encode(command.getPassword())).willReturn(command.getPassword());

        //when
        Throwable result = catchThrowable(() -> sut.login(command));

        //then
        verify(memberRepository).findByLoginId(command.getLoginId());
        verify(passwordEncoder).encode(command.getPassword());
        assertThat(result).isInstanceOf(LoginFailedException.class);
    }

    @DisplayName("로그인이 정상적으로 됐다면 토큰을 반환한다")
    @Test
    void shouldReturnTokenIfLoginIsSuccess() {
        //given
        LoginCommand command = LoginCommand.of("foo", "bar");
        Member member = Member.builder()
                .loginId(command.getLoginId())
                .password(command.getPassword())
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);

        given(memberRepository.findByLoginId(command.getLoginId()))
                .willReturn(Optional.of(member));

        given(passwordEncoder.encode(command.getPassword())).willReturn(command.getPassword());
        String expectedToken = "token";
        given(jwtTokenProvider.createToken(member.getId())).willReturn(expectedToken);

        //when
        LoginResult result = sut.login(command);

        //then
        verify(memberRepository).findByLoginId(command.getLoginId());
        verify(passwordEncoder).encode(command.getPassword());
        verify(jwtTokenProvider).createToken(member.getId());
        assertThat(result.getToken()).isEqualTo(expectedToken);
    }
}
