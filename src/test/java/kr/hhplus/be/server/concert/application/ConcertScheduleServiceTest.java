package kr.hhplus.be.server.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;


@ExtendWith(MockitoExtension.class)
public class ConcertScheduleServiceTest {

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @InjectMocks
    private ConcertScheduleService concertScheduleService;

    @Test
    @DisplayName("성공: 콘서트 예약가능한 날짜 목록 조회")
    void 콘서트_예약_가능_날짜_목록_조회() {
        // given
        Long concertId = 1L;
        
        List<ConcertSchedule> scheduleList = List.of(
            ConcertSchedule.create(concertId, LocalDate.now(), LocalTime.now(), 10),
            ConcertSchedule.create(concertId, LocalDate.of(2026, 06, 07), LocalTime.now(), 15)
        );

        when(concertScheduleRepository.findAvailableDates(concertId)).thenReturn(scheduleList);
        // when
        List<ConcertSchedule> result = concertScheduleService.getAvailableDates(concertId);
    
        // then
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(scheduleList);
    }

    @Test
    @DisplayName("실패: 콘서트 예약가능한 날짜 목록 없음")
    void 콘서트_예약_가능_날짜_목록_없음() {
        // given
        when(concertScheduleRepository.findAvailableDates(1L)).thenReturn(List.of());

        // when&then
        assertThatThrownBy(() -> concertScheduleService.getAvailableDates(1L))
        .isInstanceOf(BusinessException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
    }

}
