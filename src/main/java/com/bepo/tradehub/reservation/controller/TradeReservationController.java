package com.bepo.tradehub.reservation.controller;

import com.bepo.tradehub.global.common.ApiResponse;
import com.bepo.tradehub.reservation.dto.TradeReservationCreateRequest;
import com.bepo.tradehub.reservation.dto.TradeReservationListResponse;
import com.bepo.tradehub.reservation.dto.TradeReservationResponse;
import com.bepo.tradehub.reservation.service.TradeReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "TradeReservation", description = "거래 예약 API")
public class TradeReservationController {

    private final TradeReservationService tradeReservationService;

    @PostMapping
    @Operation(summary = "예약 생성", description = "상품에 대한 거래 예약을 생성합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> createReservation(
            @RequestBody @Valid TradeReservationCreateRequest request
    ) {
        TradeReservationResponse response = tradeReservationService.createReservation(request);

        return ResponseEntity
                .created(URI.create("/api/reservations/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "예약 목록 조회", description = "전체 거래 예약 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<TradeReservationListResponse>>> getReservations() {
        List<TradeReservationListResponse> responses = tradeReservationService.getReservations();

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{reservationId}")
    @Operation(summary = "예약 상세 조회", description = "예약 ID로 거래 예약 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> getReservation(
            @Parameter(description = "예약 ID", example = "1")
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.getReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/accept")
    @Operation(summary = "예약 수락", description = "요청 상태의 예약을 수락하고 상품 상태를 예약 중으로 변경합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> acceptReservation(
            @Parameter(description = "예약 ID", example = "1")
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.acceptReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/reject")
    @Operation(summary = "예약 거절", description = "요청 상태의 예약을 거절합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> rejectReservation(
            @Parameter(description = "예약 ID", example = "1")
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.rejectReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/cancel")
    @Operation(summary = "예약 취소", description = "요청 또는 수락 상태의 예약을 취소합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> cancelReservation(
            @Parameter(description = "예약 ID", example = "1")
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.cancelReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/complete")
    @Operation(summary = "거래 완료", description = "수락 상태의 예약을 거래 완료로 변경하고 상품 상태를 판매 완료로 변경합니다.")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> completeReservation(
            @Parameter(description = "예약 ID", example = "1")
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.completeReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
