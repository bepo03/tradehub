package com.bepo.tradehub.product.entity;

import com.bepo.tradehub.product.exception.ProductNotReservableException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void validateReservableDoesNotThrowWhenProductIsSelling() {
        Product product = productWithStatus(ProductStatus.SELLING);

        assertThatCode(product::validateReservable)
                .doesNotThrowAnyException();
    }

    @Test
    void validateReservableThrowsWhenProductIsReserved() {
        Product product = productWithStatus(ProductStatus.RESERVED);

        assertThatThrownBy(product::validateReservable)
                .isInstanceOf(ProductNotReservableException.class);
    }

    @Test
    void validateReservableThrowsWhenProductIsSoldOut() {
        Product product = productWithStatus(ProductStatus.SOLD_OUT);

        assertThatThrownBy(product::validateReservable)
                .isInstanceOf(ProductNotReservableException.class);
    }

    @Test
    void reserveChangesSellingProductToReserved() {
        Product product = productWithStatus(ProductStatus.SELLING);

        product.reserve();

        assertThat(product.getStatus()).isEqualTo(ProductStatus.RESERVED);
    }

    @Test
    void reserveThrowsWhenProductIsAlreadyReserved() {
        Product product = productWithStatus(ProductStatus.RESERVED);

        assertThatThrownBy(product::reserve)
                .isInstanceOf(ProductNotReservableException.class);
    }

    private Product productWithStatus(ProductStatus status) {
        return Product.builder()
                .title("Keyboard")
                .description("Mechanical keyboard")
                .price(30000L)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
