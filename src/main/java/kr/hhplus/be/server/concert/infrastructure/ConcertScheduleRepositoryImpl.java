package kr.hhplus.be.server.concert.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.concert.domain.model.ConcertSchedule;
import kr.hhplus.be.server.concert.domain.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.concert.infrastructure.entity.ConcertScheduleEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository{
    private final ConcertScheduleJpaRepository jpaRepository;

    @Override
    public List<ConcertSchedule> findAvailableDates(Long concertId){
        return jpaRepository.findAvailableDatesByConcertId(concertId)
            .stream()
            .map(ConcertScheduleEntity::toModel)
            .toList();
    }

    @Override
    public Optional<ConcertSchedule> findConcertSchedule(Long concertId, LocalDate concertDate) {
        return jpaRepository.findByConcertIdAndConcertDate(concertId, concertDate)
            .map(ConcertScheduleEntity::toModel);
    }
}
