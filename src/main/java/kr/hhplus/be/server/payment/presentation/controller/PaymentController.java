package kr.hhplus.be.server.payment.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.payment.application.PaymentFacade;
import kr.hhplus.be.server.payment.domain.model.Payment;
import kr.hhplus.be.server.payment.presentation.dto.request.PaymentRequest;
import kr.hhplus.be.server.payment.presentation.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Payment", description = "결제 관리 API")
public class PaymentController {

        private final PaymentFacade paymentFacade;

        @Operation(summary = "결제 처리", description = "예약 건을 결제하고 결제 내역을 생성합니다. 결제 금액은 예약 시점에 확정된 좌석 가격을 사용합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "결제 성공",
                        content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                        content = @Content(schema = @Schema(hidden = true))),
                @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없음",
                        content = @Content(schema = @Schema(hidden = true))),
                @ApiResponse(responseCode = "409", description = "포인트 잔액 부족 또는 만료된 예약",
                        content = @Content(schema = @Schema(hidden = true)))
        })
        @PostMapping
        public ResponseEntity<PaymentResponse> createPayment(
                @Parameter(description = "대기열 토큰", example = "550e8400-e29b-41d4-a716-446655440000")
                @RequestHeader("Queue-Token") String token,
                @Valid @RequestBody PaymentRequest request
        ) {
                Payment payment = paymentFacade.processPayment(token, request.reservationId());
                return ResponseEntity.ok(PaymentResponse.from(payment));
        }
}
