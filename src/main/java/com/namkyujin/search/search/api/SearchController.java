package com.namkyujin.search.search.api;

import com.namkyujin.search.common.model.CommonResponse;
import com.namkyujin.search.common.security.web.LoginUserPrincipal;
import com.namkyujin.search.search.application.SearchService;
import com.namkyujin.search.search.model.PlaceSearchPresentation;
import com.namkyujin.search.search.model.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(SearchController.BASE_PATH)
@RestController
@RequiredArgsConstructor
public class SearchController {
    protected static final String BASE_PATH = "/v1/search";

    private final SearchService searchService;

    @GetMapping
    public CommonResponse<PlaceSearchPresentation> search(LoginUserPrincipal principal,
                                                          @RequestParam String keyword,
                                                          @PageableDefault(page = 1) Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            throw new IllegalArgumentException("키워드는 필수 입니다.");
        }

        SearchQuery searchQuery = SearchQuery.of(keyword, pageable.getPageNumber(), pageable.getPageSize());

        return CommonResponse.of(searchService.search(principal.getLoginUser(), searchQuery));
    }
}
