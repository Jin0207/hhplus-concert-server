package kr.hhplus.be.server.queue.application;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository queueTokenRepository;

    // 토큰을 발급한다.
    public QueueToken issueToken(Long userId, Long concertId){
        // 이미 WAITING 또는 ACTIVE 토큰이 있으면 재발급 금지
        queueTokenRepository.findActiveOrWaitingToken(userId, concertId)
        .ifPresent(t -> { throw new BusinessException(ErrorCode.QUEUE_TOKEN_ALREADY_EXISTS); });

        QueueToken token = QueueToken.create(userId, concertId);
        return queueTokenRepository.save(token);
    }

    // ACTIVE 토큰을 만료처리한다.
    public QueueToken expiredToken(QueueToken queueToken){
        QueueToken token = queueToken.expire();
        return queueTokenRepository.save(token);
    }

    public QueueToken getUserIdByToken(String token){
        QueueToken queueToken = queueTokenRepository.findByToken(token)
                                .orElseThrow(() -> new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_FOUND));
        return queueToken;
    }

}
