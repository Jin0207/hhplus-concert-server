package kr.hhplus.be.server.queue.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.queue.domain.enums.TokenStatus;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.vo.Token;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class QueueTokenEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "token", nullable = false, unique = true, length = 36)
    private String token;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "queue_position")
    private Integer queuePosition;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public static QueueTokenEntity from(QueueToken queueToken) {
        return QueueTokenEntity.builder()
            .id(queueToken.id())
            .userId(queueToken.userId())
            .concertId(queueToken.concertId())
            .token(queueToken.token().getValue())
            .status(queueToken.status().name())
            .queuePosition(queueToken.queuePosition())
            .activatedAt(queueToken.activatedAt())
            .expiredAt(queueToken.expiredAt())
            .build();
    }

    public QueueToken toModel() {
        return new QueueToken(
            this.id, this.userId, this.concertId, Token.of(this.token),
            TokenStatus.valueOf(this.status), this.queuePosition,
            this.activatedAt, this.expiredAt, this.getCreatedAt()
        );
    }
}
