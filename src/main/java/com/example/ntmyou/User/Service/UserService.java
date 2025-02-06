package com.example.ntmyou.User.Service;

import com.example.ntmyou.Exception.UserCodeAlreadyExistsException;
import com.example.ntmyou.Exception.UserNameAlreadyExistsException;
import com.example.ntmyou.User.DTO.UserSignupRequestDto;
import com.example.ntmyou.User.DTO.UserSignupResponseDto;
import com.example.ntmyou.User.Entity.User;
import com.example.ntmyou.User.Mapper.UserMapper;
import com.example.ntmyou.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 트렌잭션은 db변경이 발생하는 부분에 최소한으로 적용하는 것이 성능적으로 유리하여 수정 함
    // 회원가입 create는 단순 조회만 할 수 있도록
    public UserSignupResponseDto create(UserSignupRequestDto dto) {
        // 중복 조회
        userRepository.findByCode(dto.getCode())
                .ifPresent(user -> { throw new UserCodeAlreadyExistsException("이미 존재하는 코드입니다. 다시 확인해 주세요"); });

        userRepository.findByName(dto.getName())
                .ifPresent(user -> { throw new UserNameAlreadyExistsException("이미 존재하는 닉네임입니다. 다시 확인해 주세요"); });

        User user = UserMapper.toEntity(dto, passwordEncoder);

        return saveUser(user);
    }

    // 회원가입 로직
    @Transactional
    public UserSignupResponseDto saveUser(User user) {
        User saveUser = userRepository.save(user);
        return UserMapper.toResponseDTO(saveUser);
    }

}
