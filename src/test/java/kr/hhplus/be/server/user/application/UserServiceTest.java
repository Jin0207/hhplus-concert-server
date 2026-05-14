package kr.hhplus.be.server.user.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import kr.hhplus.be.server.user.domain.model.User;
import kr.hhplus.be.server.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;

    private Long userId;
    private User user;
    private long initPoint;

    @BeforeEach
    void beforeEach() {
        userId = 1L;
        initPoint = 1_000L;
        user = new User(userId, "test1", "", "사용자1", initPoint, LocalDateTime.now(), null);
    }

    @Test
    @DisplayName("성공: 사용자 조회")
    void 사용자_조회_성공(){
        // given
        when( userRepository.findById(userId) ).thenReturn( Optional.of(user) );

        // when
        User result = userService.getUser(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(userId);
    }

    @Test
    @DisplayName("실패: 존재하지 않는 사용자 조회 시 예외가 발생한다.")
    void 사용자_조회_실패(){
        // given
        when(userRepository.findById(userId)).thenReturn( Optional.empty() );
        // when&then
        assertThatThrownBy(() -> userService.getUser(userId))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND);
    }

    @Test
    @DisplayName("성공: 사용자 포인트 충전")
    void 포인트_충전_성공(){
        // given
        long amount = 5_000L;
        long total = initPoint + amount;
        User chargedUser = new User(userId, "test1", "pwd", "테스트", total, LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findByIdWithLock(userId)).thenReturn( Optional.of(user) );
        when(userRepository.save(any(User.class))).thenReturn(chargedUser);

        // when
        User result = userService.chargePoint(userId, amount);

        //then
        assertThat(result.point()).isEqualTo(total);
        
        verify(userRepository).findByIdWithLock(userId);
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("실패: 포인트 충전 최소 포인트 미만")
    void 충전_최소_포인트_미만_예외(){
        // given
        long amount = 990L;

        assertThatThrownBy(() -> userService.chargePoint(userId, amount))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_POINT_MIN);
    }

    @Test
    @DisplayName("실패: 포인트 충전 보유 포인트 초과")
    void 충전_보유_포인트_초과_예외(){
        // given
        long amount = 1_000_000L;
        when(userRepository.findByIdWithLock(userId)).thenReturn( Optional.of(user) );

        assertThatThrownBy(() -> userService.chargePoint(userId, amount))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_POINT_MAX);
    }

    @Test
    @DisplayName("실패: 포인트 충전 0 미만")
    void 충전_포인트_0_미만_예외(){
        // given
        long amount = 0L;

        assertThatThrownBy(() -> userService.chargePoint(userId, amount))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_AMOUNT);
    }
}
