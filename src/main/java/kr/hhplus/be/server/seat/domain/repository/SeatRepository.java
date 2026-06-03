package kr.hhplus.be.server.seat.domain.repository;

import java.util.List;

import kr.hhplus.be.server.seat.domain.model.Seat;

public interface SeatRepository {
    
    List<Seat> findSeats(Long scheduleId);
}
