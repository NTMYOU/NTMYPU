package com.example.ntmyou.User.Entity;

import com.example.ntmyou.Config.Enum.Gender;
import com.example.ntmyou.Config.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 20, nullable = false, unique = true)
    private String code; // ID 코드

    @Column(nullable = false, unique = true)
    private String name; // 닉네임

    @Column(nullable = false)
    private String password; // 패스워드

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE; // 기본 설정은 남자

    @Column(nullable = false)
    private Integer age; // 나이

    @Column
    private String region;  // 지역

    @Column
    private LocalDateTime sDay; // 가입날짜

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // 기본 값 일반회원

    @Column
    private Boolean credit; // 정지된 계정 여부


    @Column(length = 1000)
    private String mainImgUrl;  // 대표 이미지 URL

    @ElementCollection
    @CollectionTable(name = "user_images", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "image_url", length = 1000)
    private List<String> imageUrls = new ArrayList<>();

    @Column
    private String mbti; // MBTI 형식

    // 가입날짜 자동으로 들어가게 하기
    // PrePersist -> Entity가 DB 저장전에 호출 되기 때문에 자동으로 저장 됨
    @PrePersist
    public void prePersist() {
        // 가입날짜
        if (this.sDay == null) {
            this.sDay = LocalDateTime.now();
        }

        if (this.gender == null) {
            this.gender = Gender.MALE;
        }

        if (this.role == null) {
            this.role = Role.USER;
        }

        if (this.credit == null) {
            this.credit = false;
        }

    }
}
