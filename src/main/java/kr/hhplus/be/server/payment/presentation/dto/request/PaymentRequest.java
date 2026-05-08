package kr.hhplus.be.server.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "결제 요청")
public record PaymentRequest(
        @NotNull
        @Schema(description = "예약 ID", example = "1")
        Long reservationId
) {}
