package com.kdt.landing.domain.user.service;

import com.kdt.landing.domain.user.dto.UserJoinDTO;
import com.kdt.landing.domain.user.entity.User;
import com.kdt.landing.domain.user.repository.UserRepository;
import com.kdt.landing.global.cosntant.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    // 서버 작동 시 Admin 계정 자동 생성되도록 하는 메서드
    @Override
    public void createAdminMember() {
        // 이미 존재하는 회원인지 확인
        if (!userRepository.existsByEmail("admin@naver.com")) {
            // 관리자 계정 생성 및 초기 설정
            User admin = User.builder()
                    .pw(passwordEncoder.encode("1234"))
                    .nickname("관리자")
                    .email("admin@genia.academy")
                    .active(1)
                    .roleSet(Collections.singleton(Role.ADMIN))
                    .role(Role.valueOf("ADMIN"))
                    .build();

            userRepository.save(admin);

            log.info("Admin 계정이 생성되었습니다.");
        } else {
            log.info("Admin 계정이 이미 존재합니다.");
        }
    }

    @Override
    public User existByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public void join(UserJoinDTO userJoinDTO) {
        User user = modelMapper.map(userJoinDTO, User.class);
        user.changePassword(passwordEncoder.encode(userJoinDTO.getPw()));
        user.addRole(Role.STUDENT);
        log.info("=======================");
        log.info(user);
        log.info(user.getRoleSet());
        userRepository.save(user);
    }

    @Override
    public void changePw(UserJoinDTO userJoinDTO) {
        Optional<User> result = userRepository.findById(Long.valueOf(String.valueOf(userJoinDTO.getNo())));
        User user = result.orElseThrow();
        user.changePassword(passwordEncoder.encode(userJoinDTO.getPw()));
        userRepository.save(user);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
