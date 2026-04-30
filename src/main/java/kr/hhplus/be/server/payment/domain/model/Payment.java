package kr.hhplus.be.server.payment.domain.model;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.payment.domain.enums.PaymentStatus;

import java.time.LocalDateTime;

public record Payment(
        Long id,
        Long reservationId,
        Long userId,
        long amount,
        PaymentStatus status,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {
    public static Payment create(Long reservationId, Long userId, long amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.PAYMENT_INVALID_AMOUNT);
        }
        return new Payment(null, reservationId, userId, amount, PaymentStatus.FAILED, null, null);
    }

    public Payment success() {
        return new Payment(id, reservationId, userId, amount, PaymentStatus.SUCCESS, LocalDateTime.now(), createdAt);
    }

    public Payment refund() {
        if (this.status != PaymentStatus.SUCCESS) {
            throw new BusinessException(ErrorCode.PAYMENT_NOT_SUCCESS);
        }
        return new Payment(id, reservationId, userId, amount, PaymentStatus.REFUNDED, paidAt, createdAt);
    }

    public boolean isSuccess() {
        return this.status == PaymentStatus.SUCCESS;
    }
}
