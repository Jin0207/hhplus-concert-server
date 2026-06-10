package kr.hhplus.be.server.queue.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.queue.domain.model.QueueToken;

public interface QueueTokenRepository {
    QueueToken save(QueueToken queueToken);
    List<QueueToken> saveAll(List<QueueToken> tokens);
    Optional<QueueToken> findByToken(String token);
    int countActiveTokens(Long concertId);
    Optional<QueueToken> findActiveOrWaitingToken(Long userId, Long concertId);

    // 스케줄러용
    List<QueueToken> findExpiredActiveTokens(LocalDateTime now);
    List<Long> findConcertIdsWithWaitingTokens();
    List<QueueToken> findTopWaitingByConcertId(Long concertId, int limit);
}