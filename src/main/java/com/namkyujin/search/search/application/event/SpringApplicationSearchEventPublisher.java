package com.namkyujin.search.search.application.event;

import com.namkyujin.search.search.application.SearchEventPublisher;
import com.namkyujin.search.search.domain.event.PlaceSearched;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringApplicationSearchEventPublisher implements SearchEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(PlaceSearched source) {
        applicationEventPublisher.publishEvent(source);
    }
}
