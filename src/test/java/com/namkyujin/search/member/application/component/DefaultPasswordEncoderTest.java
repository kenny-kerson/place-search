package com.namkyujin.search.member.application.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DefaultPasswordEncoderTest {
    private DefaultPasswordEncoder sut;

    @BeforeEach
    void setUp() {
        sut = new DefaultPasswordEncoder();
    }

    @DisplayName("비밀번호를 인코딩할 수 있다")
    @Test
    void shouldEncodePassword() {
        //given
        String rawPassword = "foobar";

        //when
        String result = sut.encode(rawPassword);

        //then
        assertThat(result).isNotEmpty();
    }

    @DisplayName("같은 입력에는 같은 비밀번호가 출력된다")
    @Test
    void shouldSameInputSameResult() {
        //given
        String rawPassword = "foobar";

        //when
        String firstResult = sut.encode(rawPassword);
        String secondResult = sut.encode(rawPassword);

        //then
        assertThat(firstResult).isEqualTo(secondResult);
    }

    @DisplayName("빈 비밀번호 입력시 예외가 발생한다")
    @Test
    void shouldOccurredExceptionIfInputIsEmpty() {
        //given
        String emptyPassword = "";

        //when
        Throwable result = catchThrowable(() -> sut.encode(emptyPassword));

        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class);
    }
}