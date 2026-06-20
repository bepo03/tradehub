package com.bepo.tradehub.product.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;
import com.bepo.tradehub.product.entity.ProductStatus;

public class ProductNotReservableException extends BusinessException {

    public ProductNotReservableException(Long productId, ProductStatus currentStatus) {
        super(
                ErrorCode.PRODUCT_NOT_RESERVABLE,
                "예약할 수 없는 상품 상태입니다. ID: " + productId
                        + ", 현재 상태: " + currentStatus
        );
    }
}
