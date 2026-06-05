package kr.hhplus.be.server.concert.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertScheduleService {
    private final ConcertScheduleRepository concertScheduleRepository;

    // 예약가능한 날짜 목록 조회
    public List<ConcertSchedule> getAvailableDates(Long concertId){
        List<ConcertSchedule> list = concertScheduleRepository.findAvailableDates(concertId);

        if(list.isEmpty()){
            throw new BusinessException(ErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
        }
        log.debug("예약가능한 날짜 목록 - {}", list);
        return list;
    }

    // 해당콘서트 지정날짜의 콘서트스케줄 조회
    public ConcertSchedule getConcertSchedule(Long concertId, LocalDate concertDate){
        ConcertSchedule concertSchedule = concertScheduleRepository.findConcertSchedule(concertId, concertDate)
        .orElseThrow(() -> new BusinessException(ErrorCode.CONCERT_SCHEDULE_NOT_FOUND));
        
        log.debug("지정날짜 콘서트 스케줄 - {}", concertSchedule);

        return concertSchedule;
    }
}
