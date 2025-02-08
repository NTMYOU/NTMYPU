package com.example.ntmyou.User.DTO;

import com.example.ntmyou.Config.Enum.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "코드를 입력해 주세요.")
    @Size(min = 4, max = 20, message = "코드는 4자 이상 20자 이하로 입력해야 합니다.")
    private String code;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해야 합니다.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(
            regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 8~20자로 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해 주세요.")
    private String passwordConfirm;

    private Gender gender;

    @NotNull(message = "나이를 입력해 주세요.")
    @Min(18) @Max(100)
    private Integer age;

    //private String address; // 개인정보 수정에서 추가 할 수 있게 변경

}
