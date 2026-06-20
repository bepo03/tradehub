package com.bepo.tradehub.reservation.repository;

import com.bepo.tradehub.reservation.entity.TradeReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeReservationRepository extends JpaRepository<TradeReservation, Long> {

    boolean existsByProductId(Long productId);
}
