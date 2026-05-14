package kr.hhplus.be.server.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import kr.hhplus.be.server.user.application.UserService;
import kr.hhplus.be.server.user.domain.model.User;
import kr.hhplus.be.server.user.presentation.dto.request.ChargePointRequest;
import kr.hhplus.be.server.user.presentation.dto.response.UserPointResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {

        private final UserService userService;

        @Operation(summary = "포인트 충전", description = "사용자의 포인트를 충전합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "포인트 충전 성공",
                        content = @Content(schema = @Schema(implementation = UserPointResponse.class))),
                @ApiResponse(responseCode = "400", description = "잘못된 충전 금액",
                        content = @Content(schema = @Schema(hidden = true))),
                @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                        content = @Content(schema = @Schema(hidden = true)))
        })
        @PostMapping("/{id}/charge")
        public ResponseEntity<UserPointResponse> chargePoint(
                @Parameter(description = "사용자 ID", example = "1") @PathVariable Long id,
                @Valid @RequestBody ChargePointRequest request
        ) {
                User chargedUser = userService.chargePoint(id, request.amount());
                return ResponseEntity.ok(UserPointResponse.from(chargedUser));
        }

        @Operation(summary = "포인트 조회", description = "사용자의 현재 보유 포인트를 조회합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "포인트 조회 성공",
                        content = @Content(schema = @Schema(implementation = UserPointResponse.class))),
                @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                        content = @Content(schema = @Schema(hidden = true)))
        })
        @GetMapping("/{id}/point")
        public ResponseEntity<UserPointResponse> getPoint(
        @Parameter(description = "사용자 ID", example = "1") @Valid @PathVariable Long id
        ) {
                User user = userService.getUser(id);
                return ResponseEntity.ok(UserPointResponse.from(user));
        }
}
