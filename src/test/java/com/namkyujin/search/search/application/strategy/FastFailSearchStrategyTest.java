package com.namkyujin.search.search.application.strategy;

import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.application.SearchStrategy;
import com.namkyujin.search.search.model.exception.SearchFailedException;
import com.namkyujin.search.support.AbstractTestSearcher;
import com.namkyujin.search.support.AbstractTestSearcher.CircuitExceptionSearcher;
import com.namkyujin.search.support.AbstractTestSearcher.NormalSearcher;
import com.namkyujin.search.support.AbstractTestSearcher.RuntimeExceptionSearcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FastFailSearchStrategyTest extends SearchStrategyTest {

    @DisplayName("첫번째 Searcher 가 실패하면 바로 예외를 발생시킨다")
    @Test
    void shouldFastFail() {
        //given
        AbstractTestSearcher first = new RuntimeExceptionSearcher();
        AbstractTestSearcher second = new NormalSearcher();
        sut = of(first, second);

        //when
        Throwable result = catchThrowable(() -> sut.search(SEARCH_QUERY));

        //then
        assertThat(first.getCallCount()).isEqualTo(1);
        assertThat(second.getCallCount()).isEqualTo(0);
        assertThat(result).isInstanceOf(SearchFailedException.class);
    }

    @DisplayName("첫번째 Searcher 에서 CircuitBreakingException 이 발생한 경우 다음 Searcher 를 시도한다")
    @Test
    void shouldTryNextSearcherWhenPrimarySearcherOccurredCircuitBreakingException() {
        //given
        AbstractTestSearcher first = new CircuitExceptionSearcher();
        AbstractTestSearcher second = new NormalSearcher();
        sut = of(first, second);

        //when
        sut.search(SEARCH_QUERY);

        //then
        assertThat(first.getCallCount()).isEqualTo(1);
        assertThat(second.getCallCount()).isEqualTo(1);
    }

    @Override
    SearchStrategy of(PlaceSearcher... searchers) {
        return new FastFailSearchStrategy(Arrays.asList(searchers));
    }
}
