package kr.hhplus.be.server.queue.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.queue.domain.enums.TokenStatus;
import kr.hhplus.be.server.queue.domain.vo.Token;

import java.time.LocalDateTime;

public record QueueToken(
        Long id,
        Long userId,
        Long concertId,
        Token token,
        TokenStatus status,
        Integer queuePosition,
        LocalDateTime activatedAt,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {
    public static QueueToken create(Long userId, Long concertId, int queuePosition, LocalDateTime expiredAt) {
        return new QueueToken(null, userId, concertId, Token.generate(),
                TokenStatus.WAITING, queuePosition, null, expiredAt, null);
    }

    public QueueToken activate() {
        if (this.status != TokenStatus.WAITING) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_WAITING);
        }
        return new QueueToken(id, userId, concertId, token,
                TokenStatus.ACTIVE, null, LocalDateTime.now(), expiredAt, createdAt);
    }

    public QueueToken expire() {
        return new QueueToken(id, userId, concertId, token,
                TokenStatus.EXPIRED, queuePosition, activatedAt, expiredAt, createdAt);
    }

    public boolean isActive() {
        return this.status == TokenStatus.ACTIVE;
    }

    public boolean isExpired() {
        return this.status == TokenStatus.EXPIRED
                || LocalDateTime.now().isAfter(this.expiredAt);
    }
}
