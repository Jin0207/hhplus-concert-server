package kr.hhplus.be.server.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.concert.application.ConcertFacade;
import kr.hhplus.be.server.concert.presentation.dto.response.ConcertScheduleResponse;
import kr.hhplus.be.server.concert.presentation.dto.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
@Tag(name = "Concert", description = "콘서트 관리 API")
public class ConcertController {

        private final ConcertFacade concertFacade;

        @Operation(summary = "예약 가능 날짜 조회", description = "콘서트의 예약 가능한 일정 목록을 조회합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "일정 조회 성공",
                content = @Content(schema = @Schema(implementation = ConcertScheduleResponse.class))),
                @ApiResponse(responseCode = "404", description = "콘서트를 찾을 수 없음",
                content = @Content(schema = @Schema(hidden = true)))
        })
        @GetMapping("/{concertId}/schedules")
        public ResponseEntity<List<ConcertScheduleResponse>> getAvailableSchedules(
                @Parameter(description = "콘서트 ID", example = "1") @PathVariable Long concertId
        ) {
                List<ConcertScheduleResponse> response = concertFacade.getAvailableSchedules(concertId)
                .stream()
                .map(ConcertScheduleResponse::from)
                .toList();
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "예약 가능 좌석 조회", description = "특정 일정의 예약 가능한 좌석 목록을 조회합니다. 좌석 번호는 1~50입니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "좌석 조회 성공",
                content = @Content(schema = @Schema(implementation = SeatResponse.class))),
                @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음",
                content = @Content(schema = @Schema(hidden = true)))
        })
        @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
        public ResponseEntity<List<SeatResponse>> getAvailableSeats(
                @Parameter(description = "콘서트 ID", example = "1") @PathVariable Long concertId,
                @Parameter(description = "일정 ID", example = "1") @PathVariable Long scheduleId
        ) {
                List<SeatResponse> response = concertFacade.getAvailableSeats(scheduleId)
                .stream()
                .map(SeatResponse::from)
                .toList();
                return ResponseEntity.ok(response);
        }
}