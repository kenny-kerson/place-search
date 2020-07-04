package com.namkyujin.search.infrastructure.client.kakao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 카카오 키워드 장소 검색 요청
 */
@Getter
@ToString
@JsonInclude(value = NON_NULL)
public class KakaoKeywordSearchRequest {
    private static final Sort DEFAULT_SORT = Sort.accuracy;
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_SIZE = 10;

    private String query;

    @JsonProperty("category_group_code")
    private CategoryGroupCode categoryGroupCode;

    private Double x;

    private Double y;

    private Integer radius;

    private String rect;

    private Integer page;

    private Integer size;

    private Sort sort;

    @Builder
    public KakaoKeywordSearchRequest(String query,
                                     @Nullable CategoryGroupCode categoryGroupCode,
                                     @Nullable Double x,
                                     @Nullable Double y,
                                     @Nullable Integer radius,
                                     @Nullable String rect,
                                     @Nullable Integer page,
                                     @Nullable Integer size,
                                     @Nullable Sort sort) {
        setQuery(query);
        setCategoryGroup(categoryGroupCode);
        setX(x);
        setY(y);
        setRadius(radius);
        setRect(rect);
        setSort(sort);
        setPage(page);
        setSize(size);
    }

    public void setQuery(String query) {
        if (StringUtils.isEmpty(query)) {
            throw new IllegalArgumentException("query must not be null");
        }
        this.query = query;
    }

    private void setCategoryGroup(CategoryGroupCode categoryGroupCode) {
        this.categoryGroupCode = categoryGroupCode;
    }

    public void setSort(Sort sort) {
        if (sort == null) {
            sort = DEFAULT_SORT;
        }

        this.sort = sort;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setRadius(Integer radius) {
        if (radius == null) {
            radius = 0;
        }

        if (radius > 20000 || radius < 0) {
            throw new IllegalArgumentException();
        }
        this.radius = radius;
    }

    public void setRect(String rect) {
        this.rect = rect;
    }

    public void setPage(Integer page) {
        if (page == null) {
            page = DEFAULT_PAGE;
        }

        if (page > 45 || page < 1) {
            throw new IllegalArgumentException();
        }

        this.page = page;
    }

    public void setSize(Integer size) {
        if (size == null) {
            size = DEFAULT_SIZE;
        }

        if (size > 15 || size < 1) {
            throw new IllegalArgumentException();
        }

        this.size = size;
    }


    public enum Sort {
        accuracy("정확도순"),
        recency("최신순");

        private String description;

        Sort(String description) {
            this.description = description;
        }
    }


    public enum CategoryGroupCode {
        MT1("대형마트"),
        CS2("편의점"),
        PS3("어린이집, 유치원"),
        SC4("학교"),
        AC5("학원"),
        PK6("주차장"),
        OL7("주유소, 충전소"),
        SW8("지하철역"),
        BK9("은행"),
        CT1("문화시설"),
        AG2("중개업소"),
        PO3("공공기관"),
        AT4("관광명소"),
        AD5("숙박"),
        FD6("음식점"),
        CE7("카페"),
        HP8("병원"),
        PM9("약국");

        private String description;

        CategoryGroupCode(String description) {
            this.description = description;
        }
    }
}
