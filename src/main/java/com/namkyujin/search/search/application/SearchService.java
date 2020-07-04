package com.namkyujin.search.search.application;

import com.namkyujin.search.common.security.web.LoginUser;
import com.namkyujin.search.search.domain.SearchHistory;
import com.namkyujin.search.search.domain.SearchHistoryRepository;
import com.namkyujin.search.search.domain.event.PlaceSearched;
import com.namkyujin.search.search.model.PlaceSearchPresentation;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final DynamicSearchStrategyResolver dynamicSearchStrategyResolver;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchEventPublisher searchEventPublisher;

    @Transactional
    public PlaceSearchPresentation search(LoginUser loginUser, SearchQuery searchQuery) {
        SearchResult searchResult = dynamicSearchStrategyResolver.resolve()
                .search(searchQuery);

        SearchHistory searchHistory = searchHistoryRepository.save(mapHistory(loginUser, searchQuery.getKeyword()));
        searchEventPublisher.publish(PlaceSearched.of(searchHistory));

        return PlaceSearchPresentation.builder()
                .content(searchResult.getPlaces())
                .pageNumber(searchQuery.getPage())
                .pageSize(searchQuery.getPageSize())
                .total(searchResult.getTotalCount())
                .build();
    }

    private SearchHistory mapHistory(LoginUser loginUser, String keyword) {
        return SearchHistory.builder()
                .keyword(keyword)
                .memberId(loginUser.getMemberId())
                .build();
    }
}
