package com.namkyujin.search.support;

import com.namkyujin.search.infrastructure.circuit.CircuitBreakingException;
import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;
import lombok.Getter;

import java.util.Collections;

@Getter
public abstract class AbstractTestSearcher implements PlaceSearcher {
    private int callCount;

    @Override
    public SearchResult search(SearchQuery searchQuery) {
        callCount++;
        return delegate(searchQuery);
    }

    protected abstract SearchResult delegate(SearchQuery searchQuery);


    @Override
    public int getOrder() {
        return 0;
    }



    public static class NormalSearcher extends AbstractTestSearcher {
        @Override
        protected SearchResult delegate(SearchQuery searchQuery) {
            return SearchResult.of(1, 1, Collections.emptyList());
        }
    }

    public static class CircuitExceptionSearcher extends AbstractTestSearcher {
        @Override
        protected SearchResult delegate(SearchQuery searchQuery) {
            throw new CircuitBreakingException();
        }
    }

    public static class RuntimeExceptionSearcher extends AbstractTestSearcher {
        @Override
        protected SearchResult delegate(SearchQuery searchQuery) {
            throw new UnsupportedOperationException();
        }
    }
}
