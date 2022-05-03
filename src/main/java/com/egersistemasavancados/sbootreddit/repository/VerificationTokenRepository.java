package com.egersistemasavancados.sbootreddit.repository;

import com.egersistemasavancados.sbootreddit.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
