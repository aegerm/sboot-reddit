package com.egersistemasavancados.sbootreddit.mapper;

import com.egersistemasavancados.sbootreddit.domain.*;
import com.egersistemasavancados.sbootreddit.presentation.representation.PostRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.PostResponseRepresentation;
import com.egersistemasavancados.sbootreddit.repository.CommentRepository;
import com.egersistemasavancados.sbootreddit.repository.VoteRepository;
import com.egersistemasavancados.sbootreddit.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "requestRepresentation.description")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequestRepresentation requestRepresentation, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "upVote", expression = "java(isUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isDownVoted(post))")
    public abstract PostResponseRepresentation mapToRepresentation(Post post);

    Integer commentCount(Post post) {
        return this.commentRepository.findByPost(post).size();
    }

    boolean isUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> vote = this.voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

            return vote.filter(v -> v.getVoteType().equals(voteType)).isPresent();
        }

        return false;
    }
}
