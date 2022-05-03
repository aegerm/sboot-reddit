package com.egersistemasavancados.sbootreddit.repository;

import com.egersistemasavancados.sbootreddit.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
