package kr.hhplus.be.server.queue.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import kr.hhplus.be.server.queue.application.QueueTokenService;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.presentation.dto.request.QueueTokenRequest;
import kr.hhplus.be.server.queue.presentation.dto.response.QueueTokenResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
@Tag(name = "Queue", description = "대기열 토큰 API")
public class QueueController {

        private final QueueTokenService queueTokenService;

        @Operation(summary = "대기열 토큰 발급", description = "콘서트 예약을 위한 토큰을 발급합니다. WAITING 상태로 발급되며, 순서가 되면 ACTIVE로 전환됩니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "토큰 발급 성공",
                        content = @Content(schema = @Schema(implementation = QueueTokenResponse.class))),
                @ApiResponse(responseCode = "400", description = "잘못된 요청",
                        content = @Content(schema = @Schema(hidden = true)))
                })
        @PostMapping
        public ResponseEntity<QueueTokenResponse> issueToken(
                @Parameter(description = "유저 ID", example = "1")
                @RequestHeader("User-Id") Long userId,
                @Valid @RequestBody QueueTokenRequest request
        ) {
                QueueToken queueToken = queueTokenService.issueToken(userId, request.concertId());
                return ResponseEntity.ok(QueueTokenResponse.from(queueToken));
        }

        @Operation(summary = "대기열 상태 조회",
                description = "현재 토큰의 상태를 반환합니다. ACTIVE가 될 때까지 폴링합니다.")
        @GetMapping("/status")
        public ResponseEntity<QueueTokenResponse> getTokenStatus(
                @Parameter(description = "발급받은 토큰 값")
                @RequestHeader("Queue-Token") String token
        ) {
        QueueToken queueToken = queueTokenService.getUserIdByToken(token);
        return ResponseEntity.ok(QueueTokenResponse.from(queueToken));
        }
}
