package com.namkyujin.search.common;

import com.namkyujin.search.common.model.CommonResponse;
import com.namkyujin.search.common.security.auth.AuthenticationException;
import com.namkyujin.search.member.model.LoginFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler({LoginFailedException.class, AuthenticationException.class})
    public ResponseEntity<CommonResponse<Object>> handleUnauthorized(Exception e) {
        return handleError(HttpStatus.UNAUTHORIZED, e);
    }

    private ResponseEntity<CommonResponse<Object>> handleError(HttpStatus responseStatus, Throwable e) {
        log.error("Occurred Exception. message={}", e.getMessage(), e);
        return ResponseEntity.status(responseStatus)
                .body(CommonResponse.of(e.getMessage()));
    }
}
