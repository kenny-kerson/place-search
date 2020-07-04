package com.namkyujin.search.search.application.strategy;

import com.namkyujin.search.config.SearchProperties;
import com.namkyujin.search.infrastructure.circuit.CircuitBreakingException;
import com.namkyujin.search.search.application.PlaceSearcher;
import com.namkyujin.search.search.model.exception.SearchFailedException;
import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FastFailSearchStrategy extends AbstractSearchStrategy {
    public FastFailSearchStrategy(List<PlaceSearcher> placeSearchers) {
        super(placeSearchers);
    }

    @Override
    public SearchResult search(SearchQuery searchQuery) {
        Map<String, Throwable> searcherToThrowable = new HashMap<>();
        for (PlaceSearcher searcher : placeSearchers) {
            try {
                return searcher.search(searchQuery);
            } catch (CircuitBreakingException exception) {
                searcherToThrowable.put(ClassUtils.getShortName(searcher.getClass()), exception);
                // ignore only circuit breaking exception

            } catch (Exception exception) {
                searcherToThrowable.put(ClassUtils.getShortName(searcher.getClass()), exception);
                break;
            }
        }

        throw SearchFailedException.createMultiSearchFailedException(searcherToThrowable);
    }

    @Override
    public SearchProperties.Mode getMode() {
        return SearchProperties.Mode.FAST_FAIL;
    }
}
