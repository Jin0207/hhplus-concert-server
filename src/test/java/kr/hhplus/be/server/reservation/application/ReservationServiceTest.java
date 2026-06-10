package kr.hhplus.be.server.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.reservation.domain.enums.ReservationStatus;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Long userId;
    private Long scheduleId;
    private Long seatId;

    @BeforeEach
    void beforeEach() {
        userId = 1L;
        scheduleId = 10L;
        seatId = 100L;
    }

    @Test
    @DisplayName("성공: 좌석 임시 예약 생성")
    void 좌석_예약_성공() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(1L, userId, seatId, scheduleId,
                ReservationStatus.PENDING, now, now.plusMinutes(5), null, null);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // when
        Reservation result = reservationService.reserveSeat(userId, scheduleId, seatId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.seatId()).isEqualTo(seatId);
        assertThat(result.scheduleId()).isEqualTo(scheduleId);
        assertThat(result.status()).isEqualTo(ReservationStatus.PENDING);

        verify(reservationRepository).save(any(Reservation.class));
    }
}
