package kr.hhplus.be.server.queue.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.infrastructure.entity.QueueTokenEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository{

    private final QueueTokenJpaRepository jpaRepository;

    @Override
    public QueueToken save(QueueToken queueToken) {
        return jpaRepository.save(QueueTokenEntity.from(queueToken)).toModel();
    }

    @Override
    public List<QueueToken> saveAll(List<QueueToken> tokens) {
        List<QueueTokenEntity> entities = tokens.stream()
            .map(QueueTokenEntity::from)
            .toList();
        return jpaRepository.saveAll(entities).stream()
            .map(QueueTokenEntity::toModel)
            .toList();
    }
    
    @Override
    public Optional<QueueToken> findByToken(String token) {
        return jpaRepository.findByToken(token).map(QueueTokenEntity::toModel);
    }

    @Override
    public int countActiveTokens(Long concertId) {
        return jpaRepository.countByStatusAndConcertId("ACTIVE", concertId);
    }

    @Override
    public Optional<QueueToken> findActiveOrWaitingToken(Long userId, Long concertId) {
        return jpaRepository.findActiveOrWaitingToken(userId, concertId)
            .map(QueueTokenEntity::toModel);
    }

    @Override
    public List<QueueToken> findExpiredActiveTokens(LocalDateTime now) {
        return jpaRepository.findExpiredActiveTokens(now).stream()
            .map(QueueTokenEntity::toModel)
            .toList();
    }

    @Override
    public List<Long> findConcertIdsWithWaitingTokens() {
        return jpaRepository.findConcertIdsWithWaitingTokens();
    }

    @Override
    public List<QueueToken> findTopWaitingByConcertId(Long concertId, int limit) {
        PageRequest pageable = PageRequest.of(0, limit);
        return jpaRepository.findTopWaitingByConcertId(concertId, pageable).stream()
            .map(QueueTokenEntity::toModel)
            .toList();
    }
}
