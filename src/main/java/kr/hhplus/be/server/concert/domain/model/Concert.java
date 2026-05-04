package kr.hhplus.be.server.concert.domain.model;

import java.time.LocalDateTime;

public record Concert(
        Long id,
        String title,
        String venue,
        String artist,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Concert create(String title, String venue, String artist) {
        return new Concert(null, title, venue, artist, null, null);
    }
}
