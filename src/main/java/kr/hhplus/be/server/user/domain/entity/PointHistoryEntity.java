package kr.hhplus.be.server.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.enums.PointType;
import kr.hhplus.be.server.user.domain.model.PointHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PointHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "amount", nullable = false)
    private long amount;

    public static PointHistoryEntity from(PointHistory pointHistory) {
        return PointHistoryEntity.builder()
            .id(pointHistory.id())
            .userId(pointHistory.userId())
            .type(pointHistory.type().name())
            .amount(pointHistory.amount())
            .build();
    }

    public PointHistory toModel() {
        return new PointHistory(
            this.id, this.userId, PointType.valueOf(this.type),
            this.amount, this.getCreatedAt()
        );
    }
}
