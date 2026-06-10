package kr.hhplus.be.server.reservation.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.reservation.application.ReservationFacade;
import kr.hhplus.be.server.reservation.domain.model.Reservation;
import kr.hhplus.be.server.reservation.presentation.dto.request.ReservationRequest;
import kr.hhplus.be.server.reservation.presentation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "Reservation", description = "예약 관리 API")
public class ReservationController {

        private final ReservationFacade reservationFacade;

        @Operation(summary = "좌석 예약 요청", description = "대기열 토큰을 검증하고 좌석을 임시 배정합니다. 배정 후 5분 내 결제하지 않으면 자동 만료됩니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "예약 성공",
                        content = @Content(schema = @Schema(implementation = ReservationResponse.class))),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                        content = @Content(schema = @Schema(hidden = true))),
                @ApiResponse(responseCode = "404", description = "좌석 또는 일정을 찾을 수 없음",
                        content = @Content(schema = @Schema(hidden = true))),
                @ApiResponse(responseCode = "409", description = "이미 예약된 좌석",
                        content = @Content(schema = @Schema(hidden = true)))
        })
        @PostMapping
        public ResponseEntity<ReservationResponse> createReservation(
                @Parameter(description = "대기열 토큰", example = "550e8400-e29b-41d4-a716-446655440000")
                @RequestHeader("Queue-Token") String token,
                @Valid @RequestBody ReservationRequest request
        ) {
                Reservation reservation = reservationFacade.reserveSeat(token, request.scheduleId(), request.seatId());

                return ResponseEntity.ok(new ReservationResponse(
                        reservation.id(), reservation.userId(), reservation.scheduleId(),
                        reservation.seatId(), reservation.status(), reservation.expiresAt()
                ));
        }
}
