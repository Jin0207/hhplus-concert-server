package kr.hhplus.be.server.seat.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.seat.domain.model.Seat;

public interface SeatRepository {
    
    List<Seat> findSeats(Long scheduleId);
}
