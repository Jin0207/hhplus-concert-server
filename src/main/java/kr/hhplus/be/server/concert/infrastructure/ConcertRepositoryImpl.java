package kr.hhplus.be.server.concert.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.concert.domain.model.Concert;
import kr.hhplus.be.server.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.concert.infrastructure.entity.ConcertEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository{

    private final ConcertJpaRepository jpaRepository;

    @Override
    public Optional<Concert> findById(Long id) {
        return jpaRepository.findById(id).map(ConcertEntity::toModel);
    }

    @Override
    public List<Concert> findConcerts() {
        return jpaRepository.findConcerts()
                .stream()
                .map(ConcertEntity::toModel)
                .toList();
    }

}
