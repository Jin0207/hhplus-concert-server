package kr.hhplus.be.server.seat.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository repository;

    @InjectMocks
    private SeatService service;

    @Test
    @DisplayName("성공: 콘서트 예약 가능 좌석 조회")
    void 예약_가능_좌석_조회() {
        Long scheduleId = 1L;

        List<Seat> seatList = List.of(
            Seat.create(scheduleId, 1, 50000L),
            Seat.create(scheduleId, 2, 50000L)
        );

        when(repository.findSeats(scheduleId)).thenReturn(seatList);

        List<Seat> result = service.getSeatList(scheduleId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(seatList);
    }
}
