package com.bepo.tradehub.reservation.entity;

import lombok.Getter;

@Getter
public enum TradeReservationStatus {
    REQUESTED,
    ACCEPTED,
    REJECTED,
    CANCELED,
    COMPLETED
}
