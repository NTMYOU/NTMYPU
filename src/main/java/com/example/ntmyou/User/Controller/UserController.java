package com.example.ntmyou.User.Controller;

import com.example.ntmyou.User.DTO.UserLoginRequestDto;
import com.example.ntmyou.User.DTO.UserLoginResponseDto;
import com.example.ntmyou.User.DTO.UserSignupRequestDto;
import com.example.ntmyou.User.DTO.UserSignupResponseDto;
import com.example.ntmyou.User.Repository.UserRepository;
import com.example.ntmyou.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> createSignup
            (@RequestBody @Valid UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 회원가입 이메일 중복 확인
    @GetMapping("/codeCheck")
    public ResponseEntity<?> emailCheck(@RequestParam String code) {
        boolean exists = userRepository.existsByCode(code);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 코드 입니다.");
        }
        return ResponseEntity.ok("SUCCESS");
    }

    // 회원가입 닉네임 중복 확인
    @GetMapping("/nameCheck")
    public ResponseEntity<?> nickName(@RequestParam String name) {
        boolean exists = userRepository.existsByName(name);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임 입니다.");
        }
        return ResponseEntity.ok("SUCCESS");
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        UserLoginResponseDto loginResponseDto = userService.login(requestDto);
        return ResponseEntity.ok(loginResponseDto);
    }
}
