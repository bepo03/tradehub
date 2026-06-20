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
        "id", "product", "buyerName", "status", "createdAt"
})
public class TradeReservationListResponse {

    private Long id;
    private TradeReservationProductResponse product;
    private String buyerName;
    private String status;
    private LocalDateTime createdAt;

    public static TradeReservationListResponse from(TradeReservation tradeReservation) {
        return TradeReservationListResponse.builder()
                .id(tradeReservation.getId())
                .product(TradeReservationProductResponse.from(tradeReservation.getProduct()))
                .buyerName(tradeReservation.getBuyerName())
                .status(tradeReservation.getStatus().toString())
                .createdAt(tradeReservation.getCreatedAt())
                .build();
    }
}
