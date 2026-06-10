package kr.hhplus.be.server.reservation.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import kr.hhplus.be.server.reservation.domain.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);;
    int expireOverdueReservations(LocalDateTime now);
    List<Reservation> saveAll(List<Reservation> reservations);
    List<Reservation> findExpiredPendingReservation(LocalDateTime now);
}
