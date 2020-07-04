package com.namkyujin.search.search.application.searcher;

import com.namkyujin.search.search.model.SearchResult;


public interface PlaceApiResponseTransformer<T> {
    SearchResult transform(T response);
}
