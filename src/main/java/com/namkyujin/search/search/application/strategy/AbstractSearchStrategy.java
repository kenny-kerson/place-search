package com.namkyujin.search.search.application.strategy;

import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.application.SearchStrategy;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractSearchStrategy implements SearchStrategy {
    protected final List<PlaceSearcher> placeSearchers;
}
