package kr.hhplus.be.server.concert.domain.repository;

import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.concert.infrastructure.entity.ConcertScheduleEntity;

public interface ConcertScheduleRepository {

    // 예약가능한 날짜 목록 조회
    List<ConcertSchedule> availableDates(Long concertId);
}
