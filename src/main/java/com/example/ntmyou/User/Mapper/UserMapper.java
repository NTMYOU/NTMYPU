package com.example.ntmyou.User.Mapper;

import com.example.ntmyou.User.DTO.UserSignupRequestDto;
import com.example.ntmyou.User.DTO.UserSignupResponseDto;
import com.example.ntmyou.User.Entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserMapper {
    public static User toEntity(UserSignupRequestDto requestDto, BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .code(requestDto.getCode())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 여기서 암호화
                .gender(requestDto.getGender())
                .age(requestDto.getAge())
                //.address(requestDto.getAddress())
                .build();
    }

    public static UserSignupResponseDto toResponseDTO(User user) {
        return new UserSignupResponseDto(
                user.getCode(),
                user.getName(),
                user.getGender(),
                //user.getAddress(),
                user.getAge()
        );
    }



}

