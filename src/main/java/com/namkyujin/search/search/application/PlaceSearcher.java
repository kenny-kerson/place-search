package com.namkyujin.search.search.application;

import com.namkyujin.search.search.model.SearchQuery;
import com.namkyujin.search.search.model.SearchResult;
import lombok.Getter;
import org.springframework.core.Ordered;

public interface PlaceSearcher extends Ordered {
    SearchResult search(SearchQuery searchQuery);

    @Getter
    enum Order {
        PRIMARY(0),
        SECONDARY(1),
        TERTIARY(2);

        private int orderNumber;

        Order(int orderNumber) {
            this.orderNumber = orderNumber;
        }
    }
}
