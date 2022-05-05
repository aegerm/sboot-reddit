package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.Post;
import com.egersistemasavancados.sbootreddit.domain.User;
import com.egersistemasavancados.sbootreddit.mapper.CommentMapper;
import com.egersistemasavancados.sbootreddit.presentation.representation.CommentRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.CommentResponseRepresentation;
import com.egersistemasavancados.sbootreddit.repository.CommentRepository;
import com.egersistemasavancados.sbootreddit.repository.PostRepository;
import com.egersistemasavancados.sbootreddit.repository.UserRepository;
import com.egersistemasavancados.sbootreddit.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;

    public void saveComment(CommentRequestRepresentation representation) {
        Post post = this.postRepository.findById(representation.getPostId()).orElseThrow(() -> new PostNotFoundException(representation.getPostId().toString()));
        this.commentRepository.save(this.commentMapper.toMap(representation, post, this.authService.getCurrentUser()));
    }

    public List<CommentResponseRepresentation> findAllCommentsForPost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return this.commentRepository.findByPost(post).stream().map(commentMapper::toResponse).collect(Collectors.toList());
    }

    public List<CommentResponseRepresentation> findAllCommentsForUser(String username) {
        User user = this.userRepository.findByusername(username).orElseThrow(() -> new UsernameNotFoundException("User name not found!"));
        return this.commentRepository.findAllByUser(user).stream().map(commentMapper::toResponse).collect(Collectors.toList());
    }
}
