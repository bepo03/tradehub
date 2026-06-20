package com.bepo.tradehub.reservation.dto;


import com.bepo.tradehub.reservation.entity.TradeReservation;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id", "product", "buyerName", "buyerPhone", "message", "status", "createdAt", "updatedAt"
})
public class TradeReservationResponse {

    private Long id;
    private TradeReservationProductResponse product;
    private String buyerName;
    private String buyerPhone;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TradeReservationResponse from(TradeReservation tradeReservation) {
        return TradeReservationResponse.builder()
                .id(tradeReservation.getId())
                .product(TradeReservationProductResponse.from(tradeReservation.getProduct()))
                .buyerName(tradeReservation.getBuyerName())
                .buyerPhone(tradeReservation.getBuyerPhone())
                .message(tradeReservation.getMessage())
                .status(tradeReservation.getStatus().toString())
                .createdAt(tradeReservation.getCreatedAt())
                .updatedAt(tradeReservation.getUpdatedAt())
                .build();
    }
}
