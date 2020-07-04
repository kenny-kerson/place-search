package com.namkyujin.search.search.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class SearchQuery {
    private final String keyword;
    private final int page;
    private final int pageSize;
}