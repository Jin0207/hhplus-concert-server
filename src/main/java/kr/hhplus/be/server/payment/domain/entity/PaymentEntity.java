package kr.hhplus.be.server.payment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.payment.domain.enums.PaymentStatus;
import kr.hhplus.be.server.payment.domain.model.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaymentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public static PaymentEntity from(Payment payment) {
        return PaymentEntity.builder()
            .id(payment.id())
            .reservationId(payment.reservationId())
            .userId(payment.userId())
            .amount(payment.amount())
            .status(payment.status().name())
            .paidAt(payment.paidAt())
            .build();
    }

    public Payment toModel() {
        return new Payment(
            this.id, this.reservationId, this.userId, this.amount,
            PaymentStatus.valueOf(this.status), this.paidAt, this.getCreatedAt()
        );
    }
}
