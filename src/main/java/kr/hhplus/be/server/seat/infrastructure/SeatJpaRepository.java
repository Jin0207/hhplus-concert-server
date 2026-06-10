package kr.hhplus.be.server.seat.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.seat.infrastructure.entity.SeatEntity;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long>{

    // 해당 콘서트의 예약가능한 좌석정보를 조회한다.
    @Query("SELECT s FROM SeatEntity s WHERE s.scheduleId = :scheduleId AND s.status= 'AVAILABLE'")
    List<SeatEntity> findSeats(Long scheduleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SeatEntity s WHERE s.id = :seatId AND s.scheduleId = :scheduleId")
    Optional<SeatEntity> findSeatByIdAndScheduleIdForUpdate(Long seatId, Long scheduleId);

    Optional<SeatEntity> findSeatByIdAndScheduleId(Long seatId, Long scheduleId);
}
