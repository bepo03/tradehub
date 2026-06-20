package com.bepo.tradehub.category.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(Long categoryId) {
        super(
                ErrorCode.CATEGORY_NOT_FOUND,
                "카테고리를 찾을 수 없습니다. ID: " + categoryId
        );
    }
}
