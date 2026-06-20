package com.bepo.tradehub.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeReservationCreateRequest {

    @NotNull(message = "상품은 필수입니다.")
    private Long productId;

    @NotBlank(message = "구매자 이름은 필수입니다.")
    @Size(max = 50, message = "구매자 이름은 50자 이하로 입력해주세요.")
    private String buyerName;

    @NotBlank(message = "구매자 연락처는 필수입니다.")
    @Size(max = 30, message = "구매자 연락처는 30자 이하로 입력해주세요.")
    private String buyerPhone;

    @Size(max = 500, message = "예약 메시지는 500자 이하로 입력해주세요.")
    private String message;
}
