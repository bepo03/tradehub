package com.bepo.tradehub.category.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;

public class CategoryInUseException extends BusinessException {

    public CategoryInUseException(Long categoryId) {
        super(
                ErrorCode.CATEGORY_IN_USE,
                "카테고리가 사용 중입니다. ID: " + categoryId
        );
    }
}
