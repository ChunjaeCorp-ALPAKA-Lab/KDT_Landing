package com.kdt.landing.domain.user.repository;

import com.kdt.landing.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorty extends JpaRepository<User, String> {
}
