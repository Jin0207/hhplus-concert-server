package kr.hhplus.be.server.reservation.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.reservation.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

public record Reservation(
        Long id,
        Long userId,
        Long seatId,
        Long scheduleId,
        long price,
        ReservationStatus status,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        LocalDateTime confirmedAt,
        LocalDateTime cancelledAt
) {
    private static final long TEMP_ASSIGN_MINUTES = 5L;

    public static Reservation create(Long userId, Long seatId, Long scheduleId, long price) {
        return new Reservation(null, userId, seatId, scheduleId, price,
                ReservationStatus.PENDING, null,
                LocalDateTime.now().plusMinutes(TEMP_ASSIGN_MINUTES), null, null);
    }

    public Reservation confirm() {
        if (this.status != ReservationStatus.PENDING) {
            throw new BusinessException(ErrorCode.RESERVATION_NOT_PENDING);
        }
        return new Reservation(id, userId, seatId, scheduleId, price,
                ReservationStatus.CONFIRMED, createdAt, expiresAt, LocalDateTime.now(), cancelledAt);
    }

    public Reservation cancel() {
        if (this.status == ReservationStatus.CONFIRMED) {
            throw new BusinessException(ErrorCode.RESERVATION_ALREADY_CONFIRMED);
        }
        return new Reservation(id, userId, seatId, scheduleId, price,
                ReservationStatus.CANCELLED, createdAt, expiresAt, confirmedAt, LocalDateTime.now());
    }

    public Reservation expire() {
        if (this.status != ReservationStatus.PENDING) {
            throw new BusinessException(ErrorCode.RESERVATION_NOT_PENDING);
        }
        return new Reservation(id, userId, seatId, scheduleId, price,
                ReservationStatus.EXPIRED, createdAt, expiresAt, confirmedAt, cancelledAt);
    }

    public boolean isExpired() {
        return this.status == ReservationStatus.EXPIRED
                || LocalDateTime.now().isAfter(this.expiresAt);
    }
}
