package com.bepo.tradehub.reservation.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;

public class TradeReservationNotFoundException extends BusinessException {

    public TradeReservationNotFoundException(Long reservationId) {
        super(
                ErrorCode.RESERVATION_NOT_FOUND,
                "예약을 찾을 수 없습니다. ID: " + reservationId
        );
    }
}
