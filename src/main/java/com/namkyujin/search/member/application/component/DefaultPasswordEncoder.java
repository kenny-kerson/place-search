package com.namkyujin.search.member.application.component;

import com.namkyujin.search.member.application.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {
    public static final String ALGORITHM = "SHA-256";

    // SHA 256 해싱
    // 256bit 의 문자열 (16진수(4bit)로 64글자)
    @Override
    public String encode(String rawPassword) {
        if (StringUtils.isEmpty(rawPassword)) {
            throw new IllegalArgumentException("rawPassword must not be empty");
        }

        MessageDigest digest;
        try {
            digest = getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such hashing algorithm", e);
        }

        byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    // 바이드코드 -> 16진수 변환
    private String bytesToHex(byte[] hash) {

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            // byte 자료형의 범위는 -128 ~ 127. 8개 비트 중 첫번째는 부호 비트이기 때문.
            // 127을 넘으면 음수로 인식.
            // 예컨대 150 -> byte 변환 -> 16진로 변환하면
            // 11111111 11111111 11111111 10010110
            // 비트 and 연산을 통해 보정해줌. ex) 4 & 5 => 4 (100 & 101)
            // 0xff = 255 = 00000000 00000000 00000000 11111111
            String hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                // 자바에서 16진수를 표현할때 0x 로 표현하는데, toHexString 은 0 을 제거해버림.
                // 따라서 추가로 패딩 처리
                // https://rules.sonarsource.com/java/RSPEC-4425
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
