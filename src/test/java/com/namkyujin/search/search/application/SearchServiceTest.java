package com.namkyujin.search.search.application;

import com.namkyujin.search.common.security.web.LoginUser;
import com.namkyujin.search.search.domain.SearchHistory;
import com.namkyujin.search.search.domain.SearchHistoryRepository;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SearchServiceTest {
    private SearchService sut;
    private DynamicSearchStrategyResolver dynamicSearchStrategyResolver;
    private SearchHistoryRepository searchHistoryRepository;
    private SearchEventPublisher searchEventPublisher;
    private SearchStrategy searchStrategy;

    @BeforeEach
    void setUp() {
        searchStrategy = mock(SearchStrategy.class);

        dynamicSearchStrategyResolver = mock(DynamicSearchStrategyResolver.class);
        searchHistoryRepository = mock(SearchHistoryRepository.class);
        searchEventPublisher = mock(SearchEventPublisher.class);

        sut = new SearchService(dynamicSearchStrategyResolver, searchHistoryRepository, searchEventPublisher);
    }

    @Test
    void shouldSearch() {
        //given
        long memberId = 1L;
        String searchKeyword = "카카오프렌즈";
        given(searchStrategy.search(any())).willReturn(SearchResult.of(1, 1, Collections.emptyList()));
        given(dynamicSearchStrategyResolver.resolve()).willReturn(searchStrategy);
        given(searchHistoryRepository.save(any())).willReturn(SearchHistory.builder().memberId(memberId).keyword(searchKeyword).build());

        //when
        sut.search(LoginUser.of(memberId), SearchQuery.of(searchKeyword, 1, 10));

        //then
        verify(dynamicSearchStrategyResolver).resolve();
        verify(searchStrategy).search(any());
        verify(searchHistoryRepository).save(any());
        verify(searchEventPublisher).publish(any());
    }
}