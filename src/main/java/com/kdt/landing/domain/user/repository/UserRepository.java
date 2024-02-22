package com.kdt.landing.domain.user.repository;

import com.kdt.landing.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = :email")
    User getId(@Param("email") String email);

    @Query("select u from User u where u.pw = :pw")
    User getPassword(@Param("pw") String pw);

    @Query("select u from User u where u.email =:email")
    User existsUserByEmail(String email);
    boolean existsByEmail(String email);
}
