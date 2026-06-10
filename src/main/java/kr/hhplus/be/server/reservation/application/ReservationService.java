package kr.hhplus.be.server.reservation.application;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation reserveSeat(Long userId, Long scheduleId, Long seatId) {
        Reservation reservation = Reservation.create(userId, seatId, scheduleId); // ← 여기서 생성
        return reservationRepository.save(reservation);
    }

}
