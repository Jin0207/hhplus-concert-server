package kr.hhplus.be.server.reservation.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.reservation.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

@Schema(description = "좌석 예약 응답")
public record ReservationResponse(
        @Schema(description = "예약 ID", example = "1")
        Long id,

        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "일정 ID", example = "1")
        Long scheduleId,

        @Schema(description = "좌석 ID", example = "1")
        Long seatId,

        @Schema(description = "예약 상태", example = "PENDING")
        ReservationStatus status,

        @Schema(description = "임시 배정 만료 시각", example = "2025-06-01T19:05:00")
        LocalDateTime expiresAt
) {}
