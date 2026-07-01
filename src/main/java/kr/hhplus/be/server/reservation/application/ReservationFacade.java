package kr.hhplus.be.server.reservation.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.queue.application.QueueTokenService;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.model.Seat;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationService reservationService;
    private final QueueTokenService queueTokenService;
    private final SeatService seatService;

    @Transactional
    public Reservation reserveSeat(String token, Long scheduleId, Long seatId){
        QueueToken queueToken = queueTokenService.getUserIdByToken(token);
                
        if(!queueToken.isActive()){
                // 활성화된 토큰이 아닙니다.
                throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_ACTIVE);
        }

        // 좌석 '예약'상태 변경
        Seat seat = seatService.getSeat(seatId, scheduleId);
        seatService.save(seat.reserve());

        // 예약 임시 배정(5분)
        Reservation reservation = reservationService.reserveSeat(queueToken.userId(), scheduleId, seatId, seat.price());

        return reservation;
    }
}
