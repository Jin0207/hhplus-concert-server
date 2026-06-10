package kr.hhplus.be.server.reservation.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.domain.repository.ReservationRepository;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    @Transactional
    @Scheduled(fixedDelay = 30000) // 30초마다 실행
    public void processReservation() {
        expireOverdueReservation();
    }

    /*
        1.유효기간 지난 예약건을 'Pending'상태로 변경한다.
        2.해당 예약의 좌석을 'Available'상태로 변경한다.
    */ 
    private void expireOverdueReservation(){
        LocalDateTime now = LocalDateTime.now();

        List<Reservation> targets = reservationRepository.findExpiredPendingReservation(now);
        
        if (targets.isEmpty()) return;
        
        int count = reservationRepository.expireOverdueReservations(now);

        targets.forEach(r -> {
            seatRepository.findSeatByIdAndScheduleId(r.seatId(), r.scheduleId())
                .ifPresent(seat -> {
                    if (!seat.isAvailable() && seat.status() == SeatStatus.RESERVED) {
                        seatRepository.save(seat.release());
                    }
            });
        });
        
    }
}
