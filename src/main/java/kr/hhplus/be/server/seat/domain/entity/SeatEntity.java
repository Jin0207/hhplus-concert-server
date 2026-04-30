package kr.hhplus.be.server.seat.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.vo.SeatNumber;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "seat",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_seat_schedule_number",
        columnNames = {"schedule_id", "seat_number"}
    )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SeatEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    public static SeatEntity from(Seat seat) {
        return SeatEntity.builder()
            .id(seat.id())
            .scheduleId(seat.scheduleId())
            .seatNumber(seat.seatNumber().getValue())
            .price(seat.price())
            .status(seat.status().name())
            .build();
    }

    public Seat toModel() {
        return new Seat(
            this.id, this.scheduleId, SeatNumber.of(this.seatNumber),
            this.price, SeatStatus.valueOf(this.status), this.getCreatedAt()
        );
    }
}
