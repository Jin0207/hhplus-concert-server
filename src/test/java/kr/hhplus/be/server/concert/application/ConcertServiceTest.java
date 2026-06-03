package kr.hhplus.be.server.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.concert.domain.model.Concert;
import kr.hhplus.be.server.concert.domain.repository.ConcertRepository;


@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    @DisplayName("성공: 콘서트 정보 조회")
    void 콘서트_조회() {
        // given
        Long concertId = 1L;
        Concert concert = new Concert(concertId, "콘서트1", "콘서트홀1", "가수1", LocalDateTime.now(), null);
        
        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));

        // when
        Concert result = concertService.getConcert(concertId);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(concertId);
    }

    @Test
    @DisplayName("실패: 콘서트 정보 조회")
    void 콘서트_조회_실패() {
        Long concertId = 1L;

        when(concertRepository.findById(concertId)).thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> concertService.getConcert(concertId))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.CONCERT_NOT_FOUND);
    }
}
