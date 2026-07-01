package kr.hhplus.be.server.payment.presentation.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.payment.domain.enums.PaymentStatus;
import kr.hhplus.be.server.payment.domain.model.Payment;

@Schema(description = "결제 응답")
public record PaymentResponse(
        @Schema(description = "결제 ID", example = "1")
        Long id,

        @Schema(description = "예약 ID", example = "1")
        Long reservationId,

        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "결제 금액", example = "50000")
        long amount,

        @Schema(description = "결제 상태", example = "SUCCESS")
        PaymentStatus status,

        @Schema(description = "결제 완료 시각", example = "2025-06-01T19:03:00")
        LocalDateTime paidAt
) {
        public static PaymentResponse from(Payment payment) {
                return new PaymentResponse(payment.id(), payment.reservationId(), payment.userId()
                        , payment.amount(), payment.status(), payment.paidAt());
        }

}
