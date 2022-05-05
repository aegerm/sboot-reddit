package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.Post;
import com.egersistemasavancados.sbootreddit.domain.Vote;
import com.egersistemasavancados.sbootreddit.presentation.representation.VoteRequestRepresentation;
import com.egersistemasavancados.sbootreddit.repository.PostRepository;
import com.egersistemasavancados.sbootreddit.repository.VoteRepository;
import com.egersistemasavancados.sbootreddit.service.exception.GlobalException;
import com.egersistemasavancados.sbootreddit.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.egersistemasavancados.sbootreddit.domain.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public void vote(VoteRequestRepresentation representation) {
        Post post = this.postRepository.findById(representation.getPostId()).orElseThrow(() -> new PostNotFoundException(representation.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = this.voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(representation.getVoteType())) {
            throw new GlobalException("You have already vote!");
        }

        if (UPVOTE.equals(representation.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        this.voteRepository.save(mapToVote(representation, post));
        this.postRepository.save(post);
    }

    private Vote mapToVote(VoteRequestRepresentation representation, Post post) {
        return Vote.builder()
                    .voteType(representation.getVoteType())
                    .post(post)
                    .user(authService.getCurrentUser())
                    .build();
    }
}
