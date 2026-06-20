package com.bepo.tradehub.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "요청 값이 올바르지 않습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVATION_NOT_FOUND", "예약을 찾을 수 없습니다."),
    CATEGORY_IN_USE(HttpStatus.CONFLICT, "CATEGORY_IN_USE", "카테고리가 사용 중입니다."),
    PRODUCT_IN_USE(HttpStatus.CONFLICT, "PRODUCT_IN_USE", "상품이 사용 중입니다."),
    INVALID_RESERVATION_STATUS(HttpStatus.CONFLICT, "INVALID_RESERVATION_STATUS", "현재 예약 상태에서는 해당 작업을 처리할 수 없습니다."),
    PRODUCT_NOT_RESERVABLE(HttpStatus.CONFLICT, "PRODUCT_NOT_RESERVABLE", "예약할 수 없는 상품 상태입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
