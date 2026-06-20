package com.bepo.tradehub.reservation.exception;

import com.bepo.tradehub.global.exception.BusinessException;
import com.bepo.tradehub.global.exception.ErrorCode;
import com.bepo.tradehub.reservation.entity.TradeReservationStatus;

public class InvalidReservationStatusException extends BusinessException {

    public InvalidReservationStatusException(
            Long reservationId,
            TradeReservationStatus currentStatus,
            String action
    ) {
        super(
                ErrorCode.INVALID_RESERVATION_STATUS,
                "현재 예약 상태에서는 해당 작업을 처리할 수 없습니다. ID: " + reservationId
                        + ", 현재 상태: " + currentStatus
                        + ", 요청 작업: " + action
        );
    }
}
