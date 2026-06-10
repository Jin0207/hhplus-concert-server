package kr.hhplus.be.server.queue.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueTokenScheduler {
    private static final int MAX_ACTIVE_PER_CONCERT = 30; // 콘서트당 동시 활성 유저 수
    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    @Scheduled(fixedDelay = 30000) // 30초마다 실행
    public void processQueue() {
        expireOverdueTokens();
        activateWaitingTokens();
    }

    private void expireOverdueTokens() {
        List<QueueToken> expired = queueTokenRepository.findExpiredActiveTokens(LocalDateTime.now());
        if (expired.isEmpty()) return;

        List<QueueToken> toSave = expired.stream()
            .map(QueueToken::expire)
            .toList();
        queueTokenRepository.saveAll(toSave);
        log.info("[Queue] {}개 토큰 만료 처리", toSave.size());
    }

    private void activateWaitingTokens() {
        List<Long> concertIds = queueTokenRepository.findConcertIdsWithWaitingTokens();

        for (Long concertId : concertIds) {
            int activeCount = queueTokenRepository.countActiveTokens(concertId);
            int toActivate = MAX_ACTIVE_PER_CONCERT - activeCount;
            if (toActivate <= 0) continue;

            List<QueueToken> waiting = queueTokenRepository.findTopWaitingByConcertId(concertId, toActivate);
            if (waiting.isEmpty()) continue;

            List<QueueToken> activated = waiting.stream()
                .map(QueueToken::activate)   // WAITING → ACTIVE, expiredAt = now+5분
                .toList();
            queueTokenRepository.saveAll(activated);
            log.info("[Queue] concertId={} {}개 토큰 활성화", concertId, activated.size());
        }
    }
}
