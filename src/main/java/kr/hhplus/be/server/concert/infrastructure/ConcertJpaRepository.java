package kr.hhplus.be.server.concert.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.server.concert.domain.model.Concert;
import kr.hhplus.be.server.concert.infrastructure.entity.ConcertEntity;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long>{
    Optional<ConcertEntity> findById(Long id);

    List<ConcertEntity> findConcerts();
}
