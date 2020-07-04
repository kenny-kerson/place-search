package com.namkyujin.search.search.application.event;

import com.namkyujin.search.search.application.SearchEventPublisher;
import com.namkyujin.search.search.domain.event.PlaceSearched;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class SearchEventMulticaster implements SearchEventPublisher {
    private final List<SearchEventPublisher> searchEventPublishers;

    @Override
    public void publish(PlaceSearched source) {
        searchEventPublishers.forEach(publish -> publish.publish(source));
    }
}
