package kr.hhplus.be.server.user.domain.validator;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.user.domain.model.User;

public class UserValidator {

    public static void validateAmount(long amount, String purpose){
        if (amount <= 0) {
            // %s 금액은 0보다 커야 합니다.
            throw new BusinessException(ErrorCode.INVALID_AMOUNT, purpose);
        }

        if(amount < User.MIN_TRANSACTION){
            // 최소 %s 포인트는 1,000원입니다.
            throw new BusinessException(ErrorCode.USER_POINT_MIN, purpose);
        }

    }

    public static void validateUserId(String loginId) {
        if (loginId == null || loginId.isEmpty()) {
            // %s은(는) 필수 입력입니다.
            throw new BusinessException(ErrorCode.REQUIRED, "로그인ID");
        }
        if (loginId.length() > 100) {
            // %s은(는) %d자 이내 입력하셔야합니다.
            throw new BusinessException(ErrorCode.TOO_LONG, "로그인ID", 100);
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            // %s은(는) 필수 입력입니다.
            throw new BusinessException(ErrorCode.REQUIRED, "패스워드");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isEmpty()) {
            // %s은(는) 필수 입력입니다.
            throw new BusinessException(ErrorCode.REQUIRED, "사용자명");
        }
    }
}
