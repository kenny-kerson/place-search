package com.namkyujin.search.common.security.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    public final SecretKey secretKey;
    private final Duration validateDuration;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.validate-duration}") Duration validateDuration) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.validateDuration = validateDuration;
    }

    public String createToken(long memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validateDuration.toMillis());

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public long getMemberId(String token) {
        try {
            String loginId = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();

            return Long.parseLong(loginId);
        } catch (Exception e) {
            log.error("Failed to get member id. cause by {}", e.getMessage(), e);
            throw e;
        }
    }
}
