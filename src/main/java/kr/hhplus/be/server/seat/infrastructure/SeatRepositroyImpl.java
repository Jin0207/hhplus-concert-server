package kr.hhplus.be.server.seat.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;
import kr.hhplus.be.server.seat.infrastructure.entity.SeatEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepositroyImpl implements SeatRepository{
    
    private final SeatJpaRepository jpaRepository;


    @Override
    public List<Seat> findSeats(Long scheduleId) {
        return jpaRepository.findSeats(scheduleId)
            .stream()
            .map(SeatEntity::toModel)
            .toList();
    }
}
