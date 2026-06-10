package kr.hhplus.be.server.seat.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;
import kr.hhplus.be.server.seat.infrastructure.entity.SeatEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository{
    
    private final SeatJpaRepository jpaRepository;

    
    @Override
    public List<Seat> findSeats(Long scheduleId) {
        return jpaRepository.findSeats(scheduleId)
            .stream()
            .map(SeatEntity::toModel)
            .toList();
    }
    
    @Override
    public Optional<Seat> findSeatByIdAndScheduleId(Long seatId, Long scheduleId) {
        return jpaRepository.findSeatByIdAndScheduleId(seatId, scheduleId)
                .map(SeatEntity :: toModel);
    }
    
    @Override
    public Optional<Seat> findSeatByIdAndScheduleIdForUpdate(Long seatId, Long scheduleId) {
        return jpaRepository.findSeatByIdAndScheduleIdForUpdate(seatId, scheduleId)
                .map(SeatEntity :: toModel);
    }

    @Override
    public Seat save(Seat seat) {
        return jpaRepository.save(SeatEntity.from(seat)).toModel();
    }
}