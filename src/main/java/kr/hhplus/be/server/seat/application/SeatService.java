package kr.hhplus.be.server.seat.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    // 콘서트스케줄ID를 입력받아 예약가능한 좌석정보를 조회한다.
    public List<Seat> getSeatList(Long schduleId){
        return seatRepository.findSeats(schduleId);
    }
}
