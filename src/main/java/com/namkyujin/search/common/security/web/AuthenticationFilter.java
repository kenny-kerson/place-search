package com.namkyujin.search.common.security.web;

import com.namkyujin.search.common.security.auth.AuthenticationException;
import com.namkyujin.search.common.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER_FIELD = "Authorization";
    public static final String BEARER = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = parseToken(request);

        if (StringUtils.isEmpty(token)) {
            handleException(response, AuthenticationException.instance(), token);
            return;
        }

        long memberId;
        try {
            memberId = jwtTokenProvider.getMemberId(token);
        } catch (Exception e) {
            handleException(response, e, token);
            return;
        }


        LoginUserPrincipal loginUserPrincipal = new LoginUserPrincipal(LoginUser.of(memberId));
        LoginUserHttpServletRequest loginUserHttpServletRequest = new LoginUserHttpServletRequest(request, loginUserPrincipal);
        filterChain.doFilter(loginUserHttpServletRequest, response);
    }

    private void handleException(HttpServletResponse response, Exception e, String token) throws IOException {
        log.warn("Failed to authentication request (token: {}).", token, e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 요청입니다.");
    }

    private String parseToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER_FIELD);
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(BEARER)
                && bearerToken.length() > BEARER.length() + 1) {
            return bearerToken.substring((BEARER + " ").length());
        }

        return "";
    }
}
