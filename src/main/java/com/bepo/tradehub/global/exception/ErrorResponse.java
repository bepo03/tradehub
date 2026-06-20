package com.bepo.tradehub.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private List<ValidationError> errors;

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(List.of())
                .build();
    }

    public static ErrorResponse from(ErrorCode errorCode, List<ValidationError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}
