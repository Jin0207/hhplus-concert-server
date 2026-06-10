package kr.hhplus.be.server.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.model.User;
import kr.hhplus.be.server.user.domain.repository.LockableUserRepository;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements LockableUserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByIdWithLock(Long id) {
        return jpaRepository.findByIdWithLock(id)
                .map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(UserEntity.from(user)).toModel();
    }
}
