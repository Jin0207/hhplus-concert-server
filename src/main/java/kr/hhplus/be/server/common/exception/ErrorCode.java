package kr.hhplus.be.server.common.exception;

public enum ErrorCode {

    // ===================== Common =====================
    REQUIRED("CM001", "%s은(는) 필수 입력입니다."),
    TOO_LONG("CM002", "%s은(는) %d자 이내 입력하셔야합니다."),
    INVALID_AMOUNT("CM003", "%s 금액은 0보다 커야 합니다."),
    NOT_FOUND("CM004", "%s을(를) 찾을 수 없습니다."),

    // ===================== User =====================
    USER_POINT_INSUFFICIENT("U001", "포인트 잔액이 부족합니다. (현재 잔액: %d원)"),
    USER_POINT_MAX("U002", "최대 보유포인트는 1,000,000원입니다. (현재 잔액: %d원)"),
    USER_POINT_USE_MIN("U003", "최소 충전포인트는 1,000원입니다."),

    // ===================== Concert =====================
    CONCERT_NOT_FOUND("C001", "콘서트를 찾을 수 없습니다."),
    CONCERT_SCHEDULE_NOT_FOUND("C002", "콘서트 일정을 찾을 수 없습니다."),
    CONCERT_SCHEDULE_NOT_OPEN("C003", "예약 가능한 일정이 아닙니다."),
    CONCERT_SCHEDULE_NO_SEATS("C004", "잔여 좌석이 없습니다."),

    // ===================== Seat =====================
    SEAT_NOT_FOUND("S001", "좌석을 찾을 수 없습니다."),
    SEAT_NOT_AVAILABLE("S002", "예약 가능한 좌석이 아닙니다."),
    SEAT_NOT_RESERVED("S003", "임시 배정 상태의 좌석이 아닙니다."),
    SEAT_INVALID_PRICE("S004", "좌석 가격은 0 이상이어야 합니다."),

    // ===================== Queue =====================
    QUEUE_TOKEN_NOT_FOUND("Q001", "대기열 토큰을 찾을 수 없습니다."),
    QUEUE_TOKEN_NOT_ACTIVE("Q002", "활성화된 토큰이 아닙니다."),
    QUEUE_TOKEN_ALREADY_EXPIRED("Q003", "이미 만료된 토큰입니다."),
    QUEUE_TOKEN_NOT_WAITING("Q004", "WAITING 상태의 토큰만 활성화할 수 있습니다."),

    // ===================== Reservation =====================
    RESERVATION_NOT_FOUND("R001", "예약을 찾을 수 없습니다."),
    RESERVATION_NOT_PENDING("R002", "PENDING 상태의 예약이 아닙니다."),
    RESERVATION_ALREADY_CONFIRMED("R003", "확정된 예약은 취소할 수 없습니다."),
    RESERVATION_EXPIRED("R004", "만료된 예약입니다."),

    // ===================== Payment =====================
    PAYMENT_NOT_FOUND("P001", "결제 내역을 찾을 수 없습니다."),
    PAYMENT_INVALID_AMOUNT("P002", "결제 금액은 0보다 커야 합니다."),
    PAYMENT_NOT_SUCCESS("P003", "결제 완료 상태에서만 환불할 수 있습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 동적 메시지 생성
     * @param args 메시지 포맷팅에 사용될 인자들
     * @return 포맷팅된 메시지
     */
    public String formatMessage(Object... args) {
        return String.format(message, args);
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
