package kr.hhplus.be.server.queue.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.hhplus.be.server.queue.infrastructure.entity.QueueTokenEntity;

public interface QueueTokenJpaRepository extends JpaRepository<QueueTokenEntity, Long> {
    Optional<QueueTokenEntity> findByToken(String token);

    int countByStatusAndConcertId(String status, Long concertId);

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.status = 'ACTIVE' AND q.expiredAt < :now")
    List<QueueTokenEntity> findExpiredActiveTokens(@Param("now") LocalDateTime now);

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.userId = :userId AND q.concertId = :concertId AND q.status IN ('WAITING', 'ACTIVE')")
    Optional<QueueTokenEntity> findActiveOrWaitingToken(@Param("userId") Long userId, @Param("concertId") Long concertId);

    @Query("SELECT DISTINCT q.concertId FROM QueueTokenEntity q WHERE q.status = 'WAITING'")
    List<Long> findConcertIdsWithWaitingTokens();

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.status = 'WAITING' AND q.concertId = :concertId ORDER BY q.id ASC")
    List<QueueTokenEntity> findTopWaitingByConcertId(@Param("concertId") Long concertId, Pageable pageable);
}
