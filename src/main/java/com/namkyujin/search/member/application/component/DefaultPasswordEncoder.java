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

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
