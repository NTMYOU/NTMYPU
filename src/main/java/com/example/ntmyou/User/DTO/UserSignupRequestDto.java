package com.example.ntmyou.User.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "코드를 입력해 주세요.")
    private String code;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력 값")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "성별을 입력해 주세요.")
    private String gender;

    @NotBlank(message = "나이를 입력해 주세요.")
    private String age;

    @NotBlank(message = "지역을 입력해 주세요.")
    private String address;


}
