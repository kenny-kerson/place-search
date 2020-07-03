package com.namkyujin.search.common.security.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JwtTokenProviderTest {
    private static final String SECRET_KEY = "ThisIsTestSecretKeyThisIsTestSecretKeyThisIsTestSecretKey";
    private static final long LOGIN_ID = 1L;

    private JwtTokenProvider sut;

    @DisplayName("토큰을 생성할 수 있다")
    @Test
    void shouldCreateToken() {
        sut = new JwtTokenProvider(SECRET_KEY, Duration.ofMinutes(30));
        String token = sut.createToken(LOGIN_ID);

        assertThat(token).isNotEmpty();
    }

    @DisplayName("키 길이가 짧으면 토큰 생성에 실패한다")
    @Test
    void shouldNotCreateProviderIfSecretKeyIsTooShort() {
        //given
        String shortKey = "helloworld";

        //when
        Throwable result = catchThrowable(() -> sut = new JwtTokenProvider(shortKey, Duration.ofMinutes(30)));

        //then
        assertThat(result).isInstanceOf(WeakKeyException.class);
    }

    @DisplayName("토큰으로 부터 회원 번호를 얻을 수 있다")
    @Test
    void shouldGetLoginIdFromToken() {
        //given
        sut = new JwtTokenProvider(SECRET_KEY, Duration.ofMinutes(30));
        String token = sut.createToken(LOGIN_ID);

        //when
        long result = sut.getMemberId(token);

        //then
        assertThat(result).isEqualTo(LOGIN_ID);
    }

    @DisplayName("토큰이 만료되었다면 회원 번호를 얻을 수 없다")
    @Test
    void shouldNotGetLoginIdIfTokenIsExpired() {
        //given
        Duration immediatelyExpire = Duration.ofMinutes(0);
        sut = new JwtTokenProvider(SECRET_KEY, immediatelyExpire);
        String token = sut.createToken(LOGIN_ID);

        //when
        Throwable result = catchThrowable(() -> sut.getMemberId(token));

        //then
        assertThat(result).isInstanceOf(ExpiredJwtException.class);
    }
}