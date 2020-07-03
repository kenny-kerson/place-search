package com.namkyujin.search.member.application;

public interface PasswordEncoder {
    String encode(String rawPassword);
}
