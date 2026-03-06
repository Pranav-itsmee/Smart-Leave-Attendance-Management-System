package com.pranav.leave.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pranav.leave.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}