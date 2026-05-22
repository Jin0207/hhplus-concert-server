package kr.hhplus.be.server.concert.infrastructure;

import java.util.List;

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
    public List<ConcertSchedule> availableDates(Long concertId){
        List<ConcertSchedule> concertSchedules = jpaRepository.availableDates(concertId)
            .stream()
            .map(ConcertScheduleEntity::toModel)
            .toList();
        
        return concertSchedules;
    }
}
