package com.namkyujin.search.search.application.strategy;

import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.application.SearchStrategy;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.support.AbstractTestSearcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SearchStrategyTest {
    protected static final SearchQuery SEARCH_QUERY = SearchQuery.of("test", 1, 10);

    protected SearchStrategy sut;

    @DisplayName("첫번째 Searcher 가 성공하면 바로 응답을 반환한다")
    @Test
    void shouldNotTryNextSearchWhenPrimarySearcherSuccess() {
        //given
        AbstractTestSearcher first = new AbstractTestSearcher.NormalSearcher();
        AbstractTestSearcher second = new AbstractTestSearcher.RuntimeExceptionSearcher();
        sut = of(first, second);

        //when
        sut.search(SEARCH_QUERY);

        //then
        assertThat(first.getCallCount()).isEqualTo(1);
        assertThat(second.getCallCount()).isEqualTo(0);
    }

    abstract SearchStrategy of(PlaceSearcher... searchers);
}
