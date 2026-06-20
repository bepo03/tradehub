package com.bepo.tradehub.reservation.service;

import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.product.exception.ProductNotFoundException;
import com.bepo.tradehub.product.repository.ProductRepository;
import com.bepo.tradehub.reservation.dto.TradeReservationCreateRequest;
import com.bepo.tradehub.reservation.dto.TradeReservationListResponse;
import com.bepo.tradehub.reservation.dto.TradeReservationResponse;
import com.bepo.tradehub.reservation.entity.TradeReservation;
import com.bepo.tradehub.reservation.exception.TradeReservationNotFoundException;
import com.bepo.tradehub.reservation.repository.TradeReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeReservationService {

    private final TradeReservationRepository tradeReservationRepository;
    private final ProductRepository productRepository;

    @Transactional
    public TradeReservationResponse createReservation(
            TradeReservationCreateRequest request
    ) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        product.validateReservable();

        TradeReservation tradeReservation = TradeReservation.toEntity(request, product);

        TradeReservation saveReservation = tradeReservationRepository.save(tradeReservation);

        return TradeReservationResponse.from(saveReservation);
    }

    @Transactional(readOnly = true)
    public List<TradeReservationListResponse> getReservations() {
        List<TradeReservation> reservations = tradeReservationRepository.findAll();

        return reservations.stream()
                .map(TradeReservationListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TradeReservationResponse getReservation(Long reservationId) {
        TradeReservation tradeReservation = tradeReservationRepository.findById(reservationId)
                .orElseThrow(() -> new TradeReservationNotFoundException(reservationId));

        return TradeReservationResponse.from(tradeReservation);
    }

    @Transactional
    public TradeReservationResponse acceptReservation(Long reservationId) {
        TradeReservation tradeReservation = tradeReservationRepository.findById(reservationId)
                .orElseThrow(() -> new TradeReservationNotFoundException(reservationId));

        tradeReservation.accept();

        return TradeReservationResponse.from(tradeReservation);
    }

    @Transactional
    public TradeReservationResponse rejectReservation(Long reservationId) {
        TradeReservation tradeReservation = tradeReservationRepository.findById(reservationId)
                .orElseThrow(() -> new TradeReservationNotFoundException(reservationId));

        tradeReservation.reject();

        return TradeReservationResponse.from(tradeReservation);
    }

    @Transactional
    public TradeReservationResponse cancelReservation(Long reservationId) {
        TradeReservation tradeReservation = tradeReservationRepository.findById(reservationId)
                .orElseThrow(() -> new TradeReservationNotFoundException(reservationId));

        tradeReservation.cancel();

        return TradeReservationResponse.from(tradeReservation);
    }

    @Transactional
    public TradeReservationResponse completeReservation(Long reservationId) {
        TradeReservation tradeReservation = tradeReservationRepository.findById(reservationId)
                .orElseThrow(() -> new TradeReservationNotFoundException(reservationId));

        tradeReservation.complete();

        return TradeReservationResponse.from(tradeReservation);
    }
}
