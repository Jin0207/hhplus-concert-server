package kr.hhplus.be.server.common.exception;

public enum ErrorCode {

    // ===================== Common =====================
    REQUIRED("CM001", "%s은(는) 필수 입력입니다.", 400),
    TOO_LONG("CM002", "%s은(는) %d자 이내 입력하셔야합니다.", 400),
    INVALID_AMOUNT("CM003", "%s 금액은 0보다 커야 합니다.", 400),
    NOT_FOUND("CM004", "%s을(를) 찾을 수 없습니다.", 404),

    // ===================== User =====================
    USER_POINT_INSUFFICIENT("U001", "포인트 잔액이 부족합니다. (현재 잔액: %d원)", 409),
    USER_POINT_MAX("U002", "최대 보유포인트는 1,000,000원입니다. (현재 잔액: %d원)", 409),
    USER_POINT_MIN("U003", "최소 %s 포인트는 1,000원입니다.", 400),

    // ===================== Concert =====================
    CONCERT_NOT_FOUND("C001", "콘서트를 찾을 수 없습니다.", 404),
    CONCERT_SCHEDULE_NOT_FOUND("C002", "콘서트 일정을 찾을 수 없습니다.", 404),
    CONCERT_SCHEDULE_NOT_OPEN("C003", "예약 가능한 일정이 아닙니다.", 409),
    CONCERT_SCHEDULE_NO_SEATS("C004", "잔여 좌석이 없습니다.", 409),

    // ===================== Seat =====================
    SEAT_NOT_FOUND("S001", "좌석을 찾을 수 없습니다.", 404),
    SEAT_NOT_AVAILABLE("S002", "예약 가능한 좌석이 아닙니다.", 409),
    SEAT_NOT_RESERVED("S003", "임시 배정 상태의 좌석이 아닙니다.", 409),
    SEAT_INVALID_PRICE("S004", "좌석 가격은 0 이상이어야 합니다.", 400),

    // ===================== Queue =====================
    QUEUE_TOKEN_NOT_FOUND("Q001", "대기열 토큰을 찾을 수 없습니다.", 401),
    QUEUE_TOKEN_NOT_ACTIVE("Q002", "활성화된 토큰이 아닙니다.", 401),
    QUEUE_TOKEN_ALREADY_EXPIRED("Q003", "이미 만료된 토큰입니다.", 401),
    QUEUE_TOKEN_NOT_WAITING("Q004", "WAITING 상태의 토큰만 활성화할 수 있습니다.", 409),
    QUEUE_TOKEN_ALREADY_EXISTS("Q005", "이미 해당 콘서트의 대기열에 등록되어 있습니다.", 409),

    // ===================== Reservation =====================
    RESERVATION_NOT_FOUND("R001", "예약을 찾을 수 없습니다.", 404),
    RESERVATION_NOT_PENDING("R002", "PENDING 상태의 예약이 아닙니다.", 409),
    RESERVATION_ALREADY_CONFIRMED("R003", "확정된 예약은 취소할 수 없습니다.", 409),
    RESERVATION_EXPIRED("R004", "만료된 예약입니다.", 409),

    // ===================== Payment =====================
    PAYMENT_NOT_FOUND("P001", "결제 내역을 찾을 수 없습니다.", 404),
    PAYMENT_INVALID_AMOUNT("P002", "결제 금액은 0보다 커야 합니다.", 400),
    PAYMENT_NOT_SUCCESS("P003", "결제 완료 상태에서만 환불할 수 있습니다.", 409);

    private final String code;
    private final String message;
    private final int httpStatus;

    ErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String formatMessage(Object... args) {
        return String.format(message, args);
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getHttpStatus() { return httpStatus; }
}
