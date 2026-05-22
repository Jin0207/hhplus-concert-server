package kr.hhplus.be.server.concert.domain.repository;

import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.concert.domain.model.Concert;

public interface ConcertRepository {

    Optional<Concert> findById(Long id);

    List<Concert> findConcerts();
}
