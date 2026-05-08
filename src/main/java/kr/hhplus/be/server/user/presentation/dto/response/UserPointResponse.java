package kr.hhplus.be.server.user.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 포인트 응답")
public record UserPointResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,
        @Schema(description = "현재 보유 포인트", example = "50_000L")
        long point
) {}
