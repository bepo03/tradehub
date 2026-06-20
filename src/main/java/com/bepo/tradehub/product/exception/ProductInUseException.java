package com.bepo.tradehub.product.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;

public class ProductInUseException extends BusinessException {

    public ProductInUseException(Long productId) {
        super(
                ErrorCode.PRODUCT_IN_USE,
                "상품이 사용 중입니다. ID: " + productId
        );
    }
}
