package com.namkyujin.search.search.application.strategy;

import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.application.SearchStrategy;
import com.namkyujin.search.support.AbstractTestSearcher;
import com.namkyujin.search.support.AbstractTestSearcher.CircuitExceptionSearcher;
import com.namkyujin.search.support.AbstractTestSearcher.RuntimeExceptionSearcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.namkyujin.search.support.AbstractTestSearcher.NormalSearcher;
import static org.assertj.core.api.Assertions.assertThat;

class HASearchStrategyTest extends SearchStrategyTest {

    @DisplayName("Searcher 가 실패하면 가능할 때 까지 시도한다")
    @Test
    void shouldHighAvailability() {
        //given
        AbstractTestSearcher first = new CircuitExceptionSearcher();
        AbstractTestSearcher second = new RuntimeExceptionSearcher();
        AbstractTestSearcher third = new NormalSearcher();
        sut = of(first, second, third);

        //when
        sut.search(SEARCH_QUERY);

        //then
        assertThat(first.getCallCount()).isEqualTo(1);
        assertThat(second.getCallCount()).isEqualTo(1);
        assertThat(third.getCallCount()).isEqualTo(1);
    }


    @Override
    SearchStrategy of(PlaceSearcher... searchers) {
        return new HASearchStrategy(Arrays.asList(searchers));
    }
}