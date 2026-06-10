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
        LocalDateTime activatedAt,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {
    public static QueueToken create(Long userId, Long concertId) {
        return new QueueToken(null, userId, concertId, Token.generate(),
                TokenStatus.WAITING, null, null, null);
    }

    public QueueToken activate() {
        if (this.status != TokenStatus.WAITING) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_WAITING);
        }

        LocalDateTime now = LocalDateTime.now();

        return new QueueToken(id, userId, concertId, token,
        TokenStatus.ACTIVE,
        now,
        now.plusMinutes(5),
        createdAt);
    }

    public QueueToken expire() {
        return new QueueToken(id, userId, concertId, token,
                TokenStatus.EXPIRED, activatedAt, expiredAt, createdAt);
    }

    public boolean isActive() {
        return this.status == TokenStatus.ACTIVE && !isExpired();
    }

    public boolean isExpired() {
        return this.status == TokenStatus.EXPIRED
                || (this.expiredAt != null && LocalDateTime.now().isAfter(this.expiredAt));
    }
}
