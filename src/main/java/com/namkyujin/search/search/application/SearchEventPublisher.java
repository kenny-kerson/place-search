package com.namkyujin.search.search.application;

import com.namkyujin.search.search.domain.event.PlaceSearched;

public interface SearchEventPublisher {
    /**
     * 장소 검색 이벤트 발행
     */
    void publish(PlaceSearched source);
}
