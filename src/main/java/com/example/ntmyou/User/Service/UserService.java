package com.example.ntmyou.User.Service;

import com.example.ntmyou.Config.JWT.JwtToken;
import com.example.ntmyou.Config.JWT.JwtTokenProvider;
import com.example.ntmyou.Exception.*;
import com.example.ntmyou.User.DTO.UserLoginRequestDto;
import com.example.ntmyou.User.DTO.UserLoginResponseDto;
import com.example.ntmyou.User.DTO.UserSignupRequestDto;
import com.example.ntmyou.User.DTO.UserSignupResponseDto;
import com.example.ntmyou.User.Entity.User;
import com.example.ntmyou.User.Mapper.UserMapper;
import com.example.ntmyou.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public UserSignupResponseDto create(UserSignupRequestDto dto) {
        // 중복 조회
        if (userRepository.findByCode(dto.getCode()).isPresent()) {
            throw new UserCodeAlreadyExistsException("존재하는 코드 입니다.");
        }

        //  중복 조회
        if (userRepository.findByName(dto.getName()).isPresent()) {
            throw new UserNameAlreadyExistsException("존재하는 닉네임 입니다.");
        }


        // 비밀번호 일치하는지 확인 진행
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // Mapper를 사용하여 DTO → Entity 변환 (패스워드 암호화 포함)
        User user = UserMapper.toEntity(dto, passwordEncoder);

        // 유저 저장
        user = userRepository.save(user);

        // Entity → DTO 변환 후 반환
        return UserMapper.toResponseDTO(user);
    }

    // 로그인
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto dto) {
        // 존재하는 아이디 인지 확인
        User user = userRepository.findByCode(dto.getCode())
                .orElseThrow(() -> new UserCodeNotFoundException("존재하지 않는 코드입니다."));

        // 패스워드가 일치한지 확인
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserPasswordNotMatchesException("비밀번호가 일치하지 않습니다.");
        }

        // 계정(Credit) 확인 TRUE 면 휴먼계정 또는 정지된 계정
        if (Boolean.TRUE.equals(user.getCredit())) {
            throw new UserCreditTrueException("휴먼 계정입니다. 로그인이 불가합니다.");
        }

        // JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getCode(), null, Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return new UserLoginResponseDto(
                user.getName(),
                jwtToken.getAccessToken(),
                jwtToken.getRefreshToken()
        );
    }


}
