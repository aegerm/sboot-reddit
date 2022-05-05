package com.egersistemasavancados.sbootreddit.repository;

import com.egersistemasavancados.sbootreddit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);
}
