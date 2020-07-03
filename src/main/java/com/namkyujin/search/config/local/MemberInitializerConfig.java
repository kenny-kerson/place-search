package com.namkyujin.search.config.local;

import com.namkyujin.search.member.application.MemberInitializer;
import com.namkyujin.search.member.application.PasswordEncoder;
import com.namkyujin.search.member.domain.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local"})
@Configuration
public class MemberInitializerConfig {
    @Bean
    public MemberInitializer tokenInitializer(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return new MemberInitializer(memberRepository, passwordEncoder);
    }

}
