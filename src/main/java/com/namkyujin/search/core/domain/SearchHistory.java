package com.namkyujin.search.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_created_at", columnList = "createdAt")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long memberId;

    private String keyword;

    private LocalDateTime createdAt;

    @Builder
    public SearchHistory(long memberId, String keyword) {
        setMemberId(memberId);
        setKeyword(keyword);
    }

    /* self encapsulation */  void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /* self encapsulation */  void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = LocalDateTime.now();
    }
}
