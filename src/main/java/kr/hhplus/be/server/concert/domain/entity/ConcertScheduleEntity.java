package kr.hhplus.be.server.concert.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.concert.domain.enums.ScheduleStatus;
import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "concert_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ConcertScheduleEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "concert_date", nullable = false)
    private LocalDate concertDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Version
    @Column(name = "version")
    private Long version;

    public static ConcertScheduleEntity from(ConcertSchedule schedule) {
        return ConcertScheduleEntity.builder()
            .id(schedule.id())
            .concertId(schedule.concertId())
            .concertDate(schedule.concertDate())
            .startTime(schedule.startTime())
            .availableSeats(schedule.availableSeats())
            .status(schedule.status().name())
            .build();
    }

    public ConcertSchedule toModel() {
        return new ConcertSchedule(
            this.id, this.concertId, this.concertDate, this.startTime,
            this.availableSeats, ScheduleStatus.valueOf(this.status), this.getCreatedAt(), this.getUpdatedAt()
        );
    }
}
