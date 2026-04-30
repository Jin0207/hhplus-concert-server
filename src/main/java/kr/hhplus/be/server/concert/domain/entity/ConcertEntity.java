package kr.hhplus.be.server.concert.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.concert.domain.model.Concert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ConcertEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "venue", nullable = false, length = 200)
    private String venue;

    @Column(name = "artist", nullable = false, length = 100)
    private String artist;

    public static ConcertEntity from(Concert concert) {
        return ConcertEntity.builder()
            .id(concert.id())
            .title(concert.title())
            .venue(concert.venue())
            .artist(concert.artist())
            .build();
    }

    public Concert toModel() {
        return new Concert(this.id, this.title, this.venue, this.artist, this.getCreatedAt());
    }
}
