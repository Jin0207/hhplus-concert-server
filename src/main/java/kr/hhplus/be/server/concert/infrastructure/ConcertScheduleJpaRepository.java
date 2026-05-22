package kr.hhplus.be.server.concert.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.concert.infrastructure.entity.ConcertScheduleEntity;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long>{
    @Query("SELECT s FROM ConcertScheduleEntity s WHERE s.concertId = :concertId AND s.status = 'OPEN'")
    List<ConcertScheduleEntity> availableDates(Long concertId);
}
