package com.egersistemasavancados.sbootreddit.repository;

import com.egersistemasavancados.sbootreddit.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
