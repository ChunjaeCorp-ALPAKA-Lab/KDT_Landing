package com.kdt.landing.domain.user.service;

import com.kdt.landing.domain.user.dto.UserJoinDTO;
import com.kdt.landing.domain.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

    public void createAdminMember();
    public User existByEmail(String email);
    public void join(UserJoinDTO userJoinDTO) ;
    public void changePw(UserJoinDTO userJoinDTO);
    public PasswordEncoder passwordEncoder();
}
