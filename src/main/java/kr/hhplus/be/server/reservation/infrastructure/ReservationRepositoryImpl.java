package kr.hhplus.be.server.reservation.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import kr.hhplus.be.server.reservation.infrastructure.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository{

    private final ReservationJpaRepository jpaRepository;

    @Override
    public Optional<Reservation> reserveSeat(Long userId, Long scheduleId, Long seatId) {
        Reservation reservation = Reservation.create(userId, seatId, scheduleId);
        ReservationEntity saved = jpaRepository.save(ReservationEntity.from(reservation));
        return Optional.of(saved.toModel());
    }
    
}
