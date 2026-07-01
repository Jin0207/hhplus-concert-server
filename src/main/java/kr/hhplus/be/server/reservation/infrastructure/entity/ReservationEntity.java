package kr.hhplus.be.server.reservation.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.reservation.domain.enums.ReservationStatus;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReservationEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    public static ReservationEntity from(Reservation reservation) {
        return ReservationEntity.builder()
            .id(reservation.id())
            .userId(reservation.userId())
            .seatId(reservation.seatId())
            .scheduleId(reservation.scheduleId())
            .price(reservation.price())
            .status(reservation.status().name())
            .expiresAt(reservation.expiresAt())
            .confirmedAt(reservation.confirmedAt())
            .cancelledAt(reservation.cancelledAt())
            .build();
    }

    public Reservation toModel() {
        return new Reservation(
            this.id, this.userId, this.seatId, this.scheduleId, this.price,
            ReservationStatus.valueOf(this.status), this.getCreatedAt(),
            this.expiresAt, this.confirmedAt, this.cancelledAt
        );
    }
}
