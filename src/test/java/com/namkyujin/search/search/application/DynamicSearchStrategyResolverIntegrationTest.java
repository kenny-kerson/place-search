package com.namkyujin.search.search.application;

import com.namkyujin.search.context.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicSearchStrategyResolverIntegrationTest extends IntegrationTest {

    @Autowired
    private DynamicSearchStrategyResolver sut;

    @DisplayName("검색 전략은 하나 이상 존재한다")
    @Test
    void shouldHaveAtLeastOneStrategy() {
        assertThat(sut.getStrategies()).isNotEmpty();
    }
}