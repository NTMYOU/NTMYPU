package com.example.ntmyou.User.Entity;

import com.example.ntmyou.Config.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(length = 8, nullable = false, unique = true)
    private String code; // ID 코드

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(nullable = false)
    private String gender; // 성별

    @Column(nullable = false)
    private String age; // 나이

    @Column(nullable = false)
    private String address;  // 지역
    @Column
    private LocalDateTime sDay; // 가입날짜

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    // 가입날짜 자동으로 들어가게 하기
    // PrePersist -> Entity가 DB 저장전에 호출 되기 때문에 자동으로 저장 됨
    @PrePersist
    public void prePersist() {
        // 가입날짜
        if (this.sDay == null) {
            this.sDay = LocalDateTime.now();
        }
        // 일반회원
        if (this.role == null) {
            this.role = Role.USER;
        }

    }
}
