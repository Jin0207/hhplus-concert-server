package kr.hhplus.be.server.user.domain.repository;

import java.util.Optional;

import kr.hhplus.be.server.user.domain.model.User;

public interface LockableUserRepository extends UserRepository {
    Optional<User> findByIdWithLock(Long id);
}
