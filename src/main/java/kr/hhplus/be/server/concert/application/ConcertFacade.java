package kr.hhplus.be.server.concert.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.seat.application.SeatService;
import kr.hhplus.be.server.seat.domain.model.Seat;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertScheduleService concertScheduleService;
    private final SeatService seatService;

    // 예약 가능한 콘서트 일정 목록 조회
    public List<ConcertSchedule> getAvailableSchedules(Long concertId) {
        return concertScheduleService.getAvailableDates(concertId);
    }

    // 해당 일정의 예약 가능한 좌석 목록 조회
    public List<Seat> getAvailableSeats(Long scheduleId) {
        return seatService.getSeatList(scheduleId);
    }
}