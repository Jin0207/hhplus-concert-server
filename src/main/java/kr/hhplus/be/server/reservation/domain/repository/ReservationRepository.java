package kr.hhplus.be.server.reservation.domain.repository;

import java.util.Optional;

import kr.hhplus.be.server.reservation.domain.model.Reservation;

public interface ReservationRepository {
    Optional<Reservation> reserveSeat(Long userId, Long scheduleId, Long seatId);

}
