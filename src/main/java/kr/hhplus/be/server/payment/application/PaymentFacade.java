package kr.hhplus.be.server.payment.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.payment.domain.model.Payment;
import kr.hhplus.be.server.queue.application.QueueTokenService;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.reservation.application.ReservationService;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.user.application.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final SeatService seatService;
    private final UserService userService;
    private final QueueTokenService queueTokenService;

    @Transactional
    public Payment processPayment(String token, Long reservationId) {
        // 1. 토큰 조회
        QueueToken queueToken = queueTokenService.getUserIdByToken(token);

        // 2. 예약 조회
        Reservation reservation = reservationService.getReservation(reservationId);

        // 3. 예약 소유자 검증
        if (!reservation.userId().equals(queueToken.userId())) {
            throw new BusinessException(ErrorCode.RESERVATION_NOT_FOUND);
        }

        // 4. 예약 상태 검증 (만료 여부)
        if (reservation.isExpired()) {
            throw new BusinessException(ErrorCode.RESERVATION_EXPIRED);
        }

        Long userId = reservation.userId();

        // 5. 포인트 차감
        userService.usePoint(userId, reservation.price());

        // 6. 결제 생성
        Payment payment = paymentService.create(reservationId, userId, reservation.price());

        // 7. 예약 확정
        reservationService.confirmReservation(reservation);

        // 8. 좌석 확정
        Seat seat = seatService.getSeat(reservation.seatId(), reservation.scheduleId());
        seatService.save(seat.confirm());

        // 9. 대기열 토큰 만료
        queueTokenService.expiredToken(queueToken);

        return payment;
    }
}
