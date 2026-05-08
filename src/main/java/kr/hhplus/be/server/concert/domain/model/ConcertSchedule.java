package kr.hhplus.be.server.concert.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.concert.domain.enums.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ConcertSchedule(
        Long id,
        Long concertId,
        LocalDate concertDate,
        LocalTime startTime,
        int availableSeats,
        ScheduleStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ConcertSchedule create(Long concertId, LocalDate concertDate, LocalTime startTime, int availableSeats) {
        return new ConcertSchedule(null, concertId, concertDate, startTime, availableSeats, ScheduleStatus.OPEN, null, null);
    }

    public ConcertSchedule decreaseAvailableSeats() {
        if (this.availableSeats <= 0) {
            throw new BusinessException(ErrorCode.CONCERT_SCHEDULE_NO_SEATS);
        }
        int newSeats = this.availableSeats - 1;
        ScheduleStatus newStatus = newSeats == 0 ? ScheduleStatus.SOLD_OUT : this.status;
        return new ConcertSchedule(id, concertId, concertDate, startTime, newSeats, newStatus, createdAt, updatedAt);
    }

    public ConcertSchedule increaseAvailableSeats() {
        int newSeats = this.availableSeats + 1;
        ScheduleStatus newStatus = this.status == ScheduleStatus.SOLD_OUT ? ScheduleStatus.OPEN : this.status;
        return new ConcertSchedule(id, concertId, concertDate, startTime, newSeats, newStatus, createdAt, updatedAt);
    }

    public boolean isOpen() {
        return this.status == ScheduleStatus.OPEN;
    }
}
