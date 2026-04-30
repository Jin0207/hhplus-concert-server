package kr.hhplus.be.server.seat.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;
import kr.hhplus.be.server.seat.domain.vo.SeatNumber;

import java.time.LocalDateTime;

public record Seat(
        Long id,
        Long scheduleId,
        SeatNumber seatNumber,
        long price,
        SeatStatus status,
        LocalDateTime createdAt
) {
    public static Seat create(Long scheduleId, int seatNumber, long price) {
        if (price < 0) {
            throw new BusinessException(ErrorCode.SEAT_INVALID_PRICE);
        }
        return new Seat(null, scheduleId, SeatNumber.of(seatNumber), price, SeatStatus.AVAILABLE, null);
    }

    public Seat reserve() {
        if (this.status != SeatStatus.AVAILABLE) {
            throw new BusinessException(ErrorCode.SEAT_NOT_AVAILABLE);
        }
        return new Seat(id, scheduleId, seatNumber, price, SeatStatus.RESERVED, createdAt);
    }

    public Seat confirm() {
        if (this.status != SeatStatus.RESERVED) {
            throw new BusinessException(ErrorCode.SEAT_NOT_RESERVED);
        }
        return new Seat(id, scheduleId, seatNumber, price, SeatStatus.CONFIRMED, createdAt);
    }

    public Seat release() {
        return new Seat(id, scheduleId, seatNumber, price, SeatStatus.AVAILABLE, createdAt);
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }
}
