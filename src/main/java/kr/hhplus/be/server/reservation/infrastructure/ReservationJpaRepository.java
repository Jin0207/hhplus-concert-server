package kr.hhplus.be.server.reservation.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.hhplus.be.server.reservation.infrastructure.entity.ReservationEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long>{
    
    @Query("SELECT r FROM ReservationEntity r WHERE r.status = 'PENDING' AND r.expiresAt < :now")
    List<ReservationEntity> findExpiredPendingReservation(LocalDateTime now);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ReservationEntity r SET r.status = 'EXPIRED' " +
        "WHERE r.status = 'PENDING' AND r.expiresAt < :now")
    int expireOverdueReservations(@Param("now") LocalDateTime now);
} 