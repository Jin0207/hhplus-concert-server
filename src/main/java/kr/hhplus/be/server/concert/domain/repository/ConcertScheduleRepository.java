package kr.hhplus.be.server.concert.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;

public interface ConcertScheduleRepository {

    // 예약가능한 날짜 목록 조회
    List<ConcertSchedule> getAvailableDates(Long concertId);

    // 해당콘서트 지정날짜의 콘서트스케줄 조회
    Optional<ConcertSchedule> getConcertSchedule(Long concertId, LocalDate concertDate);
}
