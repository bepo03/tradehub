package com.bepo.tradehub.reservation.dto;


import com.bepo.tradehub.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id", "title", "price", "status"
})
public class TradeReservationProductResponse {

    private Long id;
    private String title;
    private Long price;
    private String status;

    public static TradeReservationProductResponse from(Product product) {
        return TradeReservationProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .status(product.getStatus().toString())
                .build();
    }
}
