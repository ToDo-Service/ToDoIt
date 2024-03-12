package com.service.todoit.repository;


import com.service.todoit.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // select * from user where email = ?
    Optional<User> findByEmail(String email);

}
