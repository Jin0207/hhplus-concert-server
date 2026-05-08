package kr.hhplus.be.server.reservation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "좌석 예약 요청")
public record ReservationRequest(
        @NotNull
        @Schema(description = "콘서트 일정 ID", example = "1")
        Long scheduleId,

        @NotNull
        @Schema(description = "좌석 ID", example = "1")
        Long seatId
) {}
