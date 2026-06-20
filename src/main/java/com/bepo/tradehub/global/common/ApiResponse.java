package com.bepo.tradehub.global.common;

import com.bepo.tradehub.global.exception.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"success", "data", "error"})
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> fail(ErrorResponse error) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .error(error)
                .build();
    }
}
