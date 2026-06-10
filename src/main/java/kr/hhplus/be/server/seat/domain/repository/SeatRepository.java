package kr.hhplus.be.server.seat.domain.repository;

import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.seat.domain.model.Seat;

public interface SeatRepository {
    
    List<Seat> findSeats(Long scheduleId);
    Seat save(Seat seat);
    
    Optional<Seat> findSeatByIdAndScheduleIdForUpdate(Long seatId, Long scheduleId);
    Optional<Seat> findSeatByIdAndScheduleId(Long seatId, Long scheduleId);
}
