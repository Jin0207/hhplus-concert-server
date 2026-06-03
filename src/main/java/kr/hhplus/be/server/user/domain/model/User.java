package kr.hhplus.be.server.user.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.user.domain.validator.UserValidator;

import java.time.LocalDateTime;

public record User(
        Long id,
        String loginId,
        String password,
        String name,
        long point,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static final long MAX_BALANCE = 1_000_000L; // 최대 보유포인트
    public static final long MIN_TRANSACTION = 1_000L; // 최소 거래 단위

    public static User create(String loginId, String password, String name) {
        UserValidator.validateUserId(loginId);
        UserValidator.validatePassword(password);
        UserValidator.validateName(name);
        
        return new User(null, loginId, password, name, 0L, null, null);
    }

    /*
     *   포인트(Point)
     */
    public User chargePoint(long amount) {
        UserValidator.validateAmount(amount, "충전");

        long finalPoint = point + amount;

        if(finalPoint > MAX_BALANCE){
            // 최대 보유포인트는 1,000,000원입니다.
            throw new BusinessException(ErrorCode.USER_POINT_MAX, this.point);
        }

        return new User(id, loginId, password, name, finalPoint, createdAt, updatedAt);
    }

    public User usePoint(long amount) {
        UserValidator.validateAmount(amount, "사용");

        if (this.point < amount) {
            // 포인트 잔액이 부족합니다.
            throw new BusinessException(ErrorCode.USER_POINT_INSUFFICIENT, this.point);
        }

        return new User(id, loginId, password, name, point - amount, createdAt, updatedAt);
    }

    /*
    *   password 제외
    */
    @Override
    public String toString() {
        return "User[id=" + id + ", loginId=" + loginId + ", name=" + name +
            ", point=" + point + ", createdAt=" + createdAt + "]";
    }
}
