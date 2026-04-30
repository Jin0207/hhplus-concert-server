package kr.hhplus.be.server.user.domain.model;

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
            throw new IllegalArgumentException("거래 금액은 0보다 커야 합니다.");
        }
        return new PointHistory(null, userId, type, amount, null);
    }
}
