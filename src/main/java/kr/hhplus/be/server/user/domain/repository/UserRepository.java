package kr.hhplus.be.server.user.domain.repository;

import java.util.Optional;

import kr.hhplus.be.server.user.domain.model.User;

public interface UserRepository {

    // 식별자로 사용자 조회
    Optional<User> findById(Long id);
    /*
        식별자로 사용자 조회(비관적 락)
        포인트 충전 및 사용 시 동시성 제어
    */
    Optional<User> findByIdWithLock(Long id);

    // 사용자 저장
    User save(User user);
}
