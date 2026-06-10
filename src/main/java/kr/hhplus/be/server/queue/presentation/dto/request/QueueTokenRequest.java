package kr.hhplus.be.server.queue.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "대기열 토큰 발급 요청")
public record QueueTokenRequest(

    @NotNull
    @Schema(description = "콘서트 ID", example = "1")
    Long concertId

) {}
