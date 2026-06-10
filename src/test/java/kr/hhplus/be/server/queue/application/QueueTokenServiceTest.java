package kr.hhplus.be.server.queue.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.queue.domain.enums.TokenStatus;
import kr.hhplus.be.server.queue.domain.model.QueueToken;
import kr.hhplus.be.server.queue.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.queue.domain.vo.Token;

@ExtendWith(MockitoExtension.class)
public class QueueTokenServiceTest {

    @Mock
    private QueueTokenRepository queueTokenRepository;

    @InjectMocks
    private QueueTokenService queueTokenService;

    private Long userId;
    private Long concertId;
    private String tokenValue;
    private QueueToken waitingToken;
    private QueueToken activeToken;

    @BeforeEach
    void beforeEach() {
        userId = 1L;
        concertId = 100L;
        tokenValue = "test-token-uuid";
        LocalDateTime now = LocalDateTime.now();

        waitingToken = new QueueToken(1L, userId, concertId,
                Token.of(tokenValue), TokenStatus.WAITING, null, null, now);
        activeToken = new QueueToken(1L, userId, concertId,
                Token.of(tokenValue), TokenStatus.ACTIVE, now, now.plusMinutes(5), now);
    }

    @Test
    @DisplayName("성공: 대기열 토큰 발급")
    void 토큰_발급_성공() {
        // given
        when(queueTokenRepository.findActiveOrWaitingToken(userId, concertId)).thenReturn(Optional.empty());
        when(queueTokenRepository.save(any(QueueToken.class))).thenReturn(waitingToken);

        // when
        QueueToken result = queueTokenService.issueToken(userId, concertId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.concertId()).isEqualTo(concertId);
        assertThat(result.status()).isEqualTo(TokenStatus.WAITING);

        verify(queueTokenRepository).findActiveOrWaitingToken(userId, concertId);
        verify(queueTokenRepository).save(any(QueueToken.class));
    }

    @Test
    @DisplayName("실패: 이미 대기열에 등록된 경우 토큰 재발급이 불가하다.")
    void 토큰_발급_실패_이미_존재() {
        // given
        when(queueTokenRepository.findActiveOrWaitingToken(userId, concertId))
                .thenReturn(Optional.of(waitingToken));

        // when & then
        assertThatThrownBy(() -> queueTokenService.issueToken(userId, concertId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.QUEUE_TOKEN_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("성공: ACTIVE 토큰 만료 처리")
    void 토큰_만료_성공() {
        // given
        QueueToken expiredToken = new QueueToken(activeToken.id(), userId, concertId,
                Token.of(tokenValue), TokenStatus.EXPIRED,
                activeToken.activatedAt(), activeToken.expiredAt(), activeToken.createdAt());
        when(queueTokenRepository.save(any(QueueToken.class))).thenReturn(expiredToken);

        // when
        QueueToken result = queueTokenService.expiredToken(activeToken);

        // then
        assertThat(result.status()).isEqualTo(TokenStatus.EXPIRED);
        verify(queueTokenRepository).save(any(QueueToken.class));
    }

    @Test
    @DisplayName("성공: 토큰 문자열로 토큰 조회")
    void 토큰_조회_성공() {
        // given
        when(queueTokenRepository.findByToken(tokenValue)).thenReturn(Optional.of(activeToken));

        // when
        QueueToken result = queueTokenService.getUserIdByToken(tokenValue);

        // then
        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.concertId()).isEqualTo(concertId);

        verify(queueTokenRepository).findByToken(tokenValue);
    }

    @Test
    @DisplayName("실패: 존재하지 않는 토큰 조회 시 예외가 발생한다.")
    void 토큰_조회_실패_존재하지_않음() {
        // given
        when(queueTokenRepository.findByToken(tokenValue)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> queueTokenService.getUserIdByToken(tokenValue))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.QUEUE_TOKEN_NOT_FOUND);
    }
}
