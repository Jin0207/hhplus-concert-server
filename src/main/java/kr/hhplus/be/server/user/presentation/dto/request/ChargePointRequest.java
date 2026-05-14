package kr.hhplus.be.server.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.hhplus.be.server.user.domain.model.User;

@Schema(description = "포인트 충전 요청")
public record ChargePointRequest(
        @Schema(description = "충전할 포인트 금액 (최소 1,000 / 최대 1,000,000)", example = "10_000L")
        @Min(value = User.MIN_TRANSACTION, message = "충전 금액은 최소 1,000원 이상이어야 합니다.")
        @Max(value = User.MAX_BALANCE, message = "충전 금액은 최대 1,000,000원입니다.")
        long amount
) {}
