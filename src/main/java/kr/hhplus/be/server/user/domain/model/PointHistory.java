package kr.hhplus.be.server.user.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.user.domain.enums.PointType;

import java.time.LocalDateTime;

public record PointHistory(
        Long id,
        Long userId,
        PointType type,
        long amount,
        LocalDateTime createdAt
) {
    public static PointHistory create(Long userId, PointType type, long amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.INVALID_AMOUNT, "거래");
        }
        return new PointHistory(null, userId, type, amount, null);
    }
}
