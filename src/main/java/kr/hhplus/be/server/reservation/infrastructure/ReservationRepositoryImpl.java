package kr.hhplus.be.server.reservation.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
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
    public Reservation save(Reservation reservation) {
        return jpaRepository.save(ReservationEntity.from(reservation)).toModel();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaRepository.findById(id).map(ReservationEntity::toModel);
    }

    @Override
    public List<Reservation> findExpiredPendingReservation(LocalDateTime now) {
        return jpaRepository.findExpiredPendingReservation(now)
            .stream()
            .map(ReservationEntity :: toModel)
            .toList();
    }

    @Override
    public List<Reservation> saveAll(List<Reservation> reservations) {
        List<ReservationEntity> entities = reservations.stream()
            .map(ReservationEntity::from)
            .toList();
        
        return jpaRepository.saveAll(entities).stream()
        .map(ReservationEntity::toModel)
        .toList();
    }
    
    @Override
    public int expireOverdueReservations(LocalDateTime now) {
        return jpaRepository.expireOverdueReservations(now);
    }
}
