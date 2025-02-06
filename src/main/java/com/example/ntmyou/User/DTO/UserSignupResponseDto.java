package com.example.ntmyou.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupResponseDto {
    private String Code; // 코드
    private String name; // 닉네임
    private String gender; // 성별
    private String address; // 지역
    private String age; // 나이
}
