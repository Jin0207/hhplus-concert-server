package kr.hhplus.be.server.user.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;

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
        validateUserId(loginId);
        validatePassword(password);
        validateName(name);
        
        return new User(null, loginId, password, name, 0L, null, null);
    }

    /*
     *   포인트(Point)
     */
    public User chargePoint(long amount) {
        validateAmount(amount, "충전");

        long finalPoint = point + amount;

        if(finalPoint > MAX_BALANCE){
            throw new BusinessException(ErrorCode.USER_POINT_MAX, this.point);
        }

        return new User(id, loginId, password, name, finalPoint, createdAt, updatedAt);
    }

    public User usePoint(long amount) {
        validateAmount(amount, "사용");

        if (this.point < amount) {
            throw new BusinessException(ErrorCode.USER_POINT_INSUFFICIENT, this.point);
        }

        return new User(id, loginId, password, name, point - amount, createdAt, updatedAt);
    }

    /*
    *   검증(Validation)
    *   amount:포인트액, purpose:용도(충전/사용)
    */
    public static void validateAmount(long amount, String purpose){
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.INVALID_AMOUNT, purpose);
        }

        if(amount < MIN_TRANSACTION){
            throw new BusinessException(ErrorCode.USER_POINT_MIN, purpose);
        }

    }

    public static void validateUserId(String loginId) {
        if (loginId == null || loginId.isEmpty()) {
            throw new BusinessException(ErrorCode.REQUIRED, "로그인ID");
        }
        if (loginId.length() > 100) {
            throw new BusinessException(ErrorCode.TOO_LONG, "로그인ID", 100);
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new BusinessException(ErrorCode.REQUIRED, "패스워드");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BusinessException(ErrorCode.REQUIRED, "사용자명");
        }
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
