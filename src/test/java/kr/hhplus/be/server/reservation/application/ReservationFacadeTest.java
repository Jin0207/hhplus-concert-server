package kr.hhplus.be.server.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.queue.application.QueueTokenService;
import kr.hhplus.be.server.queue.domain.enums.TokenStatus;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.vo.Token;
import kr.hhplus.be.server.reservation.domain.enums.ReservationStatus;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.vo.SeatNumber;

@ExtendWith(MockitoExtension.class)
public class ReservationFacadeTest {

        @Mock
        private ReservationService reservationService;

        @Mock
        private QueueTokenService queueTokenService;

        @Mock
        private SeatService seatService;

        @InjectMocks
        private ReservationFacade reservationFacade;

        private Long userId;
        private Long concertId;
        private Long scheduleId;
        private Long seatId;
        private String tokenValue;
        private QueueToken activeToken;
        private QueueToken waitingToken;
        private Seat availableSeat;
        private Reservation reservation;

        @BeforeEach
        void beforeEach() {
                userId = 1L;
                concertId = 100L;
                scheduleId = 10L;
                seatId = 50L;
                tokenValue = "test-token-uuid";
                LocalDateTime now = LocalDateTime.now();

                activeToken = new QueueToken(1L, userId, concertId,
                        Token.of(tokenValue), TokenStatus.ACTIVE, now, now.plusMinutes(5), now);
                waitingToken = new QueueToken(1L, userId, concertId,
                        Token.of(tokenValue), TokenStatus.WAITING, null, null, now);

                availableSeat = new Seat(seatId, scheduleId,
                        SeatNumber.of(1), 50_000L, SeatStatus.AVAILABLE, now, null);
                reservation = new Reservation(1L, userId, seatId, scheduleId, 50_000L,
                        ReservationStatus.PENDING, now, now.plusMinutes(5), null, null);
        }

        @Test
        @DisplayName("성공: 좌석 예약")
        void 좌석_예약_성공() {
                // given
                when(queueTokenService.getUserIdByToken(tokenValue)).thenReturn(activeToken);
                when(seatService.getSeat(seatId, scheduleId)).thenReturn(availableSeat);
                when(reservationService.reserveSeat(userId, scheduleId, seatId, 50_000L)).thenReturn(reservation);

                // when
                Reservation result = reservationFacade.reserveSeat(tokenValue, scheduleId, seatId);

                // then
                assertThat(result).isNotNull();
                assertThat(result.userId()).isEqualTo(userId);
                assertThat(result.seatId()).isEqualTo(seatId);
                assertThat(result.scheduleId()).isEqualTo(scheduleId);
                assertThat(result.status()).isEqualTo(ReservationStatus.PENDING);

                verify(queueTokenService).getUserIdByToken(tokenValue);
                verify(seatService).getSeat(seatId, scheduleId);
                verify(seatService).save(any(Seat.class));
                verify(reservationService).reserveSeat(userId, scheduleId, seatId, 50_000L);
        }

        @Test
        @DisplayName("실패: 비활성 토큰으로 예약 시 예외가 발생한다.")
        void 좌석_예약_실패_비활성_토큰() {
                // given
                when(queueTokenService.getUserIdByToken(tokenValue)).thenReturn(waitingToken);

                // when & then
                assertThatThrownBy(() -> reservationFacade.reserveSeat(tokenValue, scheduleId, seatId))
                        .isInstanceOf(BusinessException.class)
                        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.QUEUE_TOKEN_NOT_ACTIVE);
        }
}
