package com.egersistemasavancados.sbootreddit.repository;

import com.egersistemasavancados.sbootreddit.domain.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String name);
}
