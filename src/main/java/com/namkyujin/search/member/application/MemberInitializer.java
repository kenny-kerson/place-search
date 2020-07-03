package com.namkyujin.search.member.application;

import com.namkyujin.search.member.domain.Member;
import com.namkyujin.search.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;
import java.util.List;

// local only component
@RequiredArgsConstructor
public class MemberInitializer implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        memberRepository.saveAll(getInitMembers());

    }

    private List<Member> getInitMembers() {
        return Arrays.asList(
                Member.builder().loginId("ryan").password(passwordEncoder.encode("foobar")).build(),
                Member.builder().loginId("apeach").password(passwordEncoder.encode("foobar1")).build(),
                Member.builder().loginId("frodo").password(passwordEncoder.encode("foobar2")).build()
        );
    }

}
