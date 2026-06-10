package kr.hhplus.be.server.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.concert.domain.enums.ScheduleStatus;
import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.vo.SeatNumber;

@ExtendWith(MockitoExtension.class)
public class ConcertFacadeTest {

    @Mock
    private ConcertScheduleService concertScheduleService;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private ConcertFacade concertFacade;

    private Long concertId;
    private Long scheduleId;
    private List<ConcertSchedule> schedules;
    private List<Seat> seats;

    @BeforeEach
    void beforeEach() {
        concertId = 100L;
        scheduleId = 10L;
        LocalDateTime now = LocalDateTime.now();

        schedules = List.of(
                new ConcertSchedule(1L, concertId, LocalDate.now().plusDays(7),
                        LocalTime.of(19, 0), 50, ScheduleStatus.OPEN, now, null),
                new ConcertSchedule(2L, concertId, LocalDate.now().plusDays(14),
                        LocalTime.of(19, 0), 30, ScheduleStatus.OPEN, now, null)
        );

        seats = List.of(
                new Seat(1L, scheduleId, SeatNumber.of(1), 50_000L, SeatStatus.AVAILABLE, now, null),
                new Seat(2L, scheduleId, SeatNumber.of(2), 50_000L, SeatStatus.AVAILABLE, now, null)
        );
    }

    @Test
    @DisplayName("성공: 예약 가능한 콘서트 일정 목록 조회")
    void 예약가능_일정_목록_조회_성공() {
        // given
        when(concertScheduleService.getAvailableDates(concertId)).thenReturn(schedules);

        // when
        List<ConcertSchedule> result = concertFacade.getAvailableSchedules(concertId);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(ConcertSchedule::isOpen);

        verify(concertScheduleService).getAvailableDates(concertId);
    }

    @Test
    @DisplayName("성공: 예약 가능한 좌석 목록 조회")
    void 예약가능_좌석_목록_조회_성공() {
        // given
        when(seatService.getSeatList(scheduleId)).thenReturn(seats);

        // when
        List<Seat> result = concertFacade.getAvailableSeats(scheduleId);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(Seat::isAvailable);

        verify(seatService).getSeatList(scheduleId);
    }
}
