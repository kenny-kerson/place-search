package com.namkyujin.search.member.domain;

import com.namkyujin.search.common.ArgumentValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "uq_login_id", columnList = "loginId", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public Member(String loginId, String password) {
        setLoginId(loginId);
        setPassword(password);
    }

    /* self encapsulation */ private void setLoginId(String loginId) {
        ArgumentValidator.notEmpty(loginId, "loginId");
        this.loginId = loginId;
    }

    /* self encapsulation */ private void setPassword(String password) {
        ArgumentValidator.notEmpty(password, "password");
        this.password = password;
    }

    public boolean matchPassword(String password) {
        if (password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    @PrePersist
    protected void onPersist() {
        createdAt = modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
