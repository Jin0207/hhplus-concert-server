package kr.hhplus.be.server.seat.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.seat.domain.model.Seat;
import kr.hhplus.be.server.seat.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    // 콘서트스케줄ID를 입력받아 예약가능한 좌석정보를 조회한다.
    public List<Seat> getSeatList(Long scheduleId){
        List<Seat> list = seatRepository.findSeats(scheduleId);

        if(list.isEmpty()){
            throw new BusinessException(ErrorCode.SEAT_NOT_FOUND);
        }

        log.debug("좌석 목록 - {}", list);
        return list;
    }
}
