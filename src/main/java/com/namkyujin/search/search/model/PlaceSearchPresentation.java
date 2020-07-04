package com.namkyujin.search.search.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Getter
public class PlaceSearchPresentation extends PageImpl<SearchResult.Place> {

    @Builder
    public PlaceSearchPresentation(List<SearchResult.Place> content, int pageNumber, int pageSize, long total) {
        super(content, PageRequest.of(pageNumber, pageSize), total);
    }
}
