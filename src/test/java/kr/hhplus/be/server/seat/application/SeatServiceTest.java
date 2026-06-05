package kr.hhplus.be.server.seat.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    @DisplayName("성공: 콘서트 예약 가능 좌석 조회")
    void 예약_가능_좌석_조회() {
        Long scheduleId = 1L;

        List<Seat> seatList = List.of(
            Seat.create(scheduleId, 1, 50000L),
            Seat.create(scheduleId, 2, 50000L)
        );

        when(seatRepository.findSeats(scheduleId)).thenReturn(seatList);

        List<Seat> result = seatService.getSeatList(scheduleId);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(seatList);
    }

    @Test
    @DisplayName("실패: 콘서트 예약 가능 좌석 없음")
    void 예약_가능_좌석_없음() {
        // given
        Long scheduleId = 1L;

        when(seatRepository.findSeats(scheduleId)).thenReturn(List.of());
        // when&then
        assertThatThrownBy(() -> seatService.getSeatList(scheduleId))
        .isInstanceOf(BusinessException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.SEAT_NOT_FOUND);
    }
}
