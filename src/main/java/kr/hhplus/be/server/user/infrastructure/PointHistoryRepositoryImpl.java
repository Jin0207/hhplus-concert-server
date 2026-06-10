package kr.hhplus.be.server.user.infrastructure;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.model.PointHistory;
import kr.hhplus.be.server.user.domain.repository.PointHistoryRepository;
import kr.hhplus.be.server.user.infrastructure.entity.PointHistoryEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository{
    private final PointHistoryJpaRepository jpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return jpaRepository.save(PointHistoryEntity.from(pointHistory)).toModel();
    }
}
