package com.bepo.tradehub.reservation.controller;

import com.bepo.tradehub.global.common.ApiResponse;
import com.bepo.tradehub.reservation.dto.TradeReservationCreateRequest;
import com.bepo.tradehub.reservation.dto.TradeReservationListResponse;
import com.bepo.tradehub.reservation.dto.TradeReservationResponse;
import com.bepo.tradehub.reservation.service.TradeReservationService;
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
public class TradeReservationController {

    private final TradeReservationService tradeReservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<TradeReservationResponse>> createReservation(
            @RequestBody @Valid TradeReservationCreateRequest request
    ) {
        TradeReservationResponse response = tradeReservationService.createReservation(request);

        return ResponseEntity
                .created(URI.create("/api/reservations/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TradeReservationListResponse>>> getReservations() {
        List<TradeReservationListResponse> responses = tradeReservationService.getReservations();

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> getReservation(
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.getReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/accept")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> acceptReservation(
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.acceptReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/reject")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> rejectReservation(
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.rejectReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> cancelReservation(
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.cancelReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}/complete")
    public ResponseEntity<ApiResponse<TradeReservationResponse>> completeReservation(
            @PathVariable Long reservationId
    ) {
        TradeReservationResponse response = tradeReservationService.completeReservation(reservationId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
