package com.namkyujin.search.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("전달받은 비밀번호가 일치하는지 비교한다")
    @ParameterizedTest
    @MethodSource("providePassword")
    void shouldComparePassword(String currentPassword, String inputPassword, boolean expected) {
        //given
        Member member = Member.builder()
                .loginId("foo")
                .password(currentPassword)
                .build();

        //when then
        assertThat(member.matchPassword(inputPassword)).isEqualTo(expected);
    }

    private static Stream<Arguments> providePassword() {
        return Stream.of(
                Arguments.of("foo", "foo", true),
                Arguments.of("foo", "bar", false),
                Arguments.of("bar", "foo", false),
                Arguments.of("foo", null, false)
        );
    }

}