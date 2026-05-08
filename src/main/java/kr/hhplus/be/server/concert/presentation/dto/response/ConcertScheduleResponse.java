package kr.hhplus.be.server.concert.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.concert.domain.enums.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "콘서트 일정 응답")
public record ConcertScheduleResponse(
        @Schema(description = "일정 ID", example = "1")
        Long id,
        @Schema(description = "콘서트 ID", example = "1")
        Long concertId,
        @Schema(description = "공연 날짜", example = "2025-06-01")
        LocalDate concertDate,
        @Schema(description = "공연 시작 시간", example = "19:00:00")
        LocalTime startTime,
        @Schema(description = "예약 가능 좌석 수", example = "30")
        int availableSeats,
        @Schema(description = "일정 상태", example = "OPEN")
        ScheduleStatus status
) {}
