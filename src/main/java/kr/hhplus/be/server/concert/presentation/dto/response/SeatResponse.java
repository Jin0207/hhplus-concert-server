package kr.hhplus.be.server.concert.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.seat.domain.enums.SeatStatus;

@Schema(description = "좌석 응답")
public record SeatResponse(
        @Schema(description = "좌석 ID", example = "1")
        Long id,
        @Schema(description = "일정 ID", example = "1")
        Long scheduleId,
        @Schema(description = "좌석 번호 (1~50)", example = "15")
        int seatNumber,
        @Schema(description = "좌석 가격", example = "50000")
        long price,
        @Schema(description = "좌석 상태", example = "AVAILABLE")
        SeatStatus status
) {}
