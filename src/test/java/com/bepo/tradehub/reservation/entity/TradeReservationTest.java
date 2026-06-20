package com.bepo.tradehub.reservation.entity;

import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.product.entity.ProductStatus;
import com.bepo.tradehub.reservation.exception.InvalidReservationStatusException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TradeReservationTest {

    @Test
    void acceptChangesRequestedReservationToAcceptedAndReservesProduct() {
        Product product = productWithStatus(ProductStatus.SELLING);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.REQUESTED);

        reservation.accept();

        assertThat(reservation.getStatus()).isEqualTo(TradeReservationStatus.ACCEPTED);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.RESERVED);
    }

    @Test
    void rejectChangesRequestedReservationToRejectedAndKeepsProductStatus() {
        Product product = productWithStatus(ProductStatus.SELLING);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.REQUESTED);

        reservation.reject();

        assertThat(reservation.getStatus()).isEqualTo(TradeReservationStatus.REJECTED);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.SELLING);
    }

    @Test
    void cancelChangesRequestedReservationToCanceledAndKeepsProductStatus() {
        Product product = productWithStatus(ProductStatus.SELLING);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.REQUESTED);

        reservation.cancel();

        assertThat(reservation.getStatus()).isEqualTo(TradeReservationStatus.CANCELED);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.SELLING);
    }

    @Test
    void cancelChangesAcceptedReservationToCanceledAndReturnsProductToSelling() {
        Product product = productWithStatus(ProductStatus.RESERVED);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.ACCEPTED);

        reservation.cancel();

        assertThat(reservation.getStatus()).isEqualTo(TradeReservationStatus.CANCELED);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.SELLING);
    }

    @Test
    void completeChangesAcceptedReservationToCompletedAndSellsOutProduct() {
        Product product = productWithStatus(ProductStatus.RESERVED);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.ACCEPTED);

        reservation.complete();

        assertThat(reservation.getStatus()).isEqualTo(TradeReservationStatus.COMPLETED);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.SOLD_OUT);
    }

    @Test
    void acceptThrowsWhenReservationIsRejected() {
        Product product = productWithStatus(ProductStatus.SELLING);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.REJECTED);

        assertThatThrownBy(reservation::accept)
                .isInstanceOf(InvalidReservationStatusException.class);
    }

    @Test
    void cancelThrowsWhenReservationIsCompleted() {
        Product product = productWithStatus(ProductStatus.SOLD_OUT);
        TradeReservation reservation = reservationWithStatus(product, TradeReservationStatus.COMPLETED);

        assertThatThrownBy(reservation::cancel)
                .isInstanceOf(InvalidReservationStatusException.class);
    }

    private TradeReservation reservationWithStatus(Product product, TradeReservationStatus status) {
        return TradeReservation.builder()
                .product(product)
                .buyerName("Buyer")
                .buyerPhone("010-1234-5678")
                .message("I want to reserve this product.")
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
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
