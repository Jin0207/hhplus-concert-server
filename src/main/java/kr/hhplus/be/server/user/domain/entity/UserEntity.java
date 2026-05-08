package kr.hhplus.be.server.user.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true, length = 100)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "point", nullable = false)
    private long point;

    /*
    *  변환
    */

    public static UserEntity from(User user){
        return UserEntity.builder()
            .id(user.id())
            .loginId(user.loginId())
            .password(user.password())
            .name(user.name())
            .point(user.point())
            .build();
    }

    public User toModel(){
        return new User(this.id, this.loginId, this.password, this.name, this.point, this.getCreatedAt(), this.getUpdatedAt());
    }
}