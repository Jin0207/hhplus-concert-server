package kr.hhplus.be.server.queue.presentation.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.queue.domain.enums.TokenStatus;
import kr.hhplus.be.server.queue.domain.model.QueueToken;

@Schema(description = "대기열 토큰 발급 응답")
public record QueueTokenResponse(
    @Schema(description = "토큰 ID")
    Long id,

    @Schema(description = "유저 ID")
    Long userId,

    @Schema(description = "콘서트 ID")
    Long concertId,

    @Schema(description = "토큰 값 (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    String token,

    @Schema(description = "토큰 상태", example = "WAITING")
    TokenStatus status,

    @Schema(description = "만료 시각")
    LocalDateTime expiredAt
) {
    public static QueueTokenResponse from(QueueToken queueToken) {
        return new QueueTokenResponse(
            queueToken.id(),
            queueToken.userId(),
            queueToken.concertId(),
            queueToken.token().getValue(),
            queueToken.status(),
            queueToken.expiredAt()
        );
    }
}
