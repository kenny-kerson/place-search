package com.namkyujin.search.member.api;

import com.namkyujin.search.context.IntegrationTest;
import com.namkyujin.search.member.application.PasswordEncoder;
import com.namkyujin.search.member.domain.Member;
import com.namkyujin.search.member.domain.MemberRepository;
import com.namkyujin.search.member.model.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerIntegrationTest extends IntegrationTest {
    private static final String EXIST_MEMBER_LOGIN_ID = "foo";
    private static final String EXIST_MEMBER_RAW_PASSWORD = "bar";

    private Member savedMember;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(Member.builder()
                .loginId(EXIST_MEMBER_LOGIN_ID).password(passwordEncoder.encode(EXIST_MEMBER_RAW_PASSWORD)).build());
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteById(savedMember.getId());
    }

    @DisplayName("로그인을 성공하면 토큰을 받는다")
    @Test
    void shouldReturnTokenWhenLoginSuccess() {
        //given
        LoginRequest request = LoginRequest.of(EXIST_MEMBER_LOGIN_ID, EXIST_MEMBER_RAW_PASSWORD);

        //when
        ResponseEntity<String> response = post(LoginController.LOGIN_PATH, request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("token");
    }

    @DisplayName("비밀번호가 올바르지 않으면 401 응답을 받는다")
    @Test
    void shouldReturnUnauthorizedResponseWhenPasswordIsInvalid_401() {
        //given
        String wrongPassword = EXIST_MEMBER_RAW_PASSWORD + "!";
        LoginRequest request = LoginRequest.of(EXIST_MEMBER_LOGIN_ID, wrongPassword);

        //when
        ResponseEntity<String> response = post(LoginController.LOGIN_PATH, request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @DisplayName("올바르지 않은 아이디로 요청할 수 없다")
    @ParameterizedTest
    @NullSource
    @MethodSource("provideLoginId")
    void shouldIdMustIncluded_400(String loginId) {
        //given
        LoginRequest request = LoginRequest.of(loginId, "pass");

        //when
        ResponseEntity<String> response = post(LoginController.LOGIN_PATH, request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private static Stream<Arguments> provideLoginId() {
        return Stream.of(
                Arguments.of("", HttpStatus.BAD_REQUEST),
                Arguments.of(" ", HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("올바르지 않은 비밀번호로 요청할 수 없다")
    @ParameterizedTest
    @NullSource
    @MethodSource("providePassword")
    void shouldPasswordMustIncluded_400(String password) {
        //given
        LoginRequest request = LoginRequest.of(EXIST_MEMBER_LOGIN_ID, password);

        //when
        ResponseEntity<String> response = post(LoginController.LOGIN_PATH, request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private static Stream<Arguments> providePassword() {
        return Stream.of(
                Arguments.of("", HttpStatus.BAD_REQUEST),
                Arguments.of(" ", HttpStatus.BAD_REQUEST)
        );
    }
}