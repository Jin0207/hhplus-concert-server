package kr.hhplus.be.server.reservation.application;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation reserveSeat(Long userId, Long scheduleId, Long seatId, long price) {
        Reservation reservation = Reservation.create(userId, seatId, scheduleId, price);
        return reservationRepository.save(reservation);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    public Reservation confirmReservation(Reservation reservation) {
        return reservationRepository.save(reservation.confirm());
    }

}
