package com.bepo.tradehub.reservation.entity;


import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.reservation.dto.TradeReservationCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "trade_reservations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 50)
    private String buyerName;

    @Column(nullable = false, length = 30)
    private String buyerPhone;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private TradeReservationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static TradeReservation toEntity(TradeReservationCreateRequest request, Product product) {
        return TradeReservation.builder()
                .product(product)
                .buyerName(request.getBuyerName())
                .buyerPhone(request.getBuyerPhone())
                .message(request.getMessage())
                .status(TradeReservationStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
