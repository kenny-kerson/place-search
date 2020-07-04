package com.namkyujin.search.search.application;

import com.namkyujin.search.config.SearchProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;


/**
 * {@link SearchProperties} 를 런타임에 동적으로 변경할 수 있는 환경을 가정.
 * <br>
 * 설정값에 따라 검색 전략을 변경 가능하도록 함.
 */
@Component
public class DynamicSearchStrategyResolver {
    private final SearchProperties searchProperties;
    private final Map<SearchProperties.Mode, SearchStrategy> modeToStrategy;

    public DynamicSearchStrategyResolver(SearchProperties searchProperties, List<SearchStrategy> searchStrategies) {
        this.searchProperties = searchProperties;
        this.modeToStrategy = searchStrategies.stream()
                .collect(toMap(SearchStrategy::getMode, strategy -> strategy));
    }

    public SearchStrategy resolve() {
        return modeToStrategy.get(searchProperties.getMode());
    }


    /* for test */ protected Map<SearchProperties.Mode, SearchStrategy> getStrategies() {
        return modeToStrategy;
    }
}
