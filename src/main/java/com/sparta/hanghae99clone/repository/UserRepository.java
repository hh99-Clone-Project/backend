package com.sparta.hanghae99clone.repository;

import com.sparta.hanghae99clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByUsername(String username);


    Optional<User> findByUsername(String username);
}
