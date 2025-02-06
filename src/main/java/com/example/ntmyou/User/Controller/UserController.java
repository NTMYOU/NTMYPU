package com.example.ntmyou.User.Controller;

import com.example.ntmyou.User.DTO.UserSignupRequestDto;
import com.example.ntmyou.User.DTO.UserSignupResponseDto;
import com.example.ntmyou.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserSignupResponseDto> createSignup
            (@RequestBody @Valid UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
