package com.bepo.tradehub.global.exception;

import com.bepo.tradehub.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        List<ErrorResponse.ValidationError> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorResponse.ValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        ErrorResponse response = ErrorResponse.from(errorCode, errors);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(response));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException e
    ) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(e.getMessage())
                .errors(List.of())
                .build();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(response));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexcepted exception occurred: ", e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.from(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(response));
    }
}
