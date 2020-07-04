package com.namkyujin.search.common;

import com.namkyujin.search.common.model.CommonResponse;
import com.namkyujin.search.common.security.auth.AuthenticationException;
import com.namkyujin.search.member.model.LoginFailedException;
import com.namkyujin.search.search.model.exception.SearchFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponse<Object>> handleBadRequest(Exception e) {
        return handleError(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({LoginFailedException.class, AuthenticationException.class})
    public ResponseEntity<CommonResponse<Object>> handleUnauthorized(Exception e) {
        return handleError(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler({SearchFailedException.class})
    public ResponseEntity<CommonResponse<Object>> handleServerError(Exception e) {
        return handleError(HttpStatus.INTERNAL_SERVER_ERROR, e, "일시적으로 문제가 발생했습니다.");
    }


    private ResponseEntity<CommonResponse<Object>> handleError(HttpStatus responseStatus, Throwable e) {
        return handleError(responseStatus, e, e.getMessage());
    }

    private ResponseEntity<CommonResponse<Object>> handleError(HttpStatus responseStatus, Throwable e, String message) {
        log.error("Occurred Exception. message={}", e.getMessage(), e);
        return ResponseEntity.status(responseStatus)
                .body(CommonResponse.of(message));
    }
}
