package kr.hhplus.be.server.seat.domain.vo;

public class SeatNumber {

    private static final int MIN = 1;
    private static final int MAX = 50;

    private final int value;

    private SeatNumber(int value) {
        if (value < MIN || value > MAX) {
            throw new IllegalArgumentException(
                    "좌석 번호는 " + MIN + "~" + MAX + " 범위여야 합니다. 입력값: " + value);
        }
        this.value = value;
    }

    public static SeatNumber of(int value) {
        return new SeatNumber(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatNumber that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
