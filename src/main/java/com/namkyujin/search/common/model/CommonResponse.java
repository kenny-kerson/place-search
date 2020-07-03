package com.namkyujin.search.common.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponse<T> {
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;
    private T data;

    private CommonResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> CommonResponse<T> of(String message) {
        return of(message, null);
    }

    public static <T> CommonResponse<T> of(T data) {
        return of("", data);
    }

    public static <T> CommonResponse<T> of(String message, T data) {
        return new CommonResponse<>(message, data);
    }
}