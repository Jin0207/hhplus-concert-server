package kr.hhplus.be.server.queue.domain.vo;

import java.util.UUID;

public class Token {

    private final String value;

    private Token(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("토큰 값은 비어있을 수 없습니다.");
        }
        this.value = value;
    }

    public static Token generate() {
        return new Token(UUID.randomUUID().toString());
    }

    public static Token of(String value) {
        return new Token(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return value.equals(token.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
