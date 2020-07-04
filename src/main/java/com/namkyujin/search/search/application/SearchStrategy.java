package com.namkyujin.search.search.application;

import com.namkyujin.search.config.SearchProperties;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;

public interface SearchStrategy {
    SearchResult search(SearchQuery searchQuery);

    SearchProperties.Mode getMode();
}
