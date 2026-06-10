package kr.hhplus.be.server.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.server.user.infrastructure.entity.PointHistoryEntity;

public interface PointHistoryJpaRepository  extends JpaRepository<PointHistoryEntity, Long>{
    
}
