package kr.hhplus.be.server.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.user.domain.model.User;
import kr.hhplus.be.server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    // 사용자 조회 By식별자
    @Transactional(readOnly = true)
    public User getUser(Long id){
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "사용자"));
        log.debug("유저 정보 - {}", user); 
        return user;
    }

    // 사용자 조회 by식별자(비관적락)
    private User getUserWithLock(Long id){
        User user = userRepository.findByIdWithLock(id)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "사용자"));
        log.debug("유저 정보 - {}", user); 
        return user;
    }

    // 포인트충전
    @Transactional
    public User chargePoint(Long id, long amount){
        User.validateAmount(amount, "충전");
        User user = this.getUserWithLock(id);
        User chargedUser = user.chargePoint(amount);
        log.debug("유저 정보 - {}", chargedUser);
        return this.save(chargedUser);
    }

    // 저장
    private User save(User user){
        return userRepository.save(user);
    }
}
