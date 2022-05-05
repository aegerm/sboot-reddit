package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.Post;
import com.egersistemasavancados.sbootreddit.domain.Subreddit;
import com.egersistemasavancados.sbootreddit.domain.User;
import com.egersistemasavancados.sbootreddit.mapper.PostMapper;
import com.egersistemasavancados.sbootreddit.presentation.representation.PostRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.PostResponseRepresentation;
import com.egersistemasavancados.sbootreddit.repository.PostRepository;
import com.egersistemasavancados.sbootreddit.repository.SubredditRepository;
import com.egersistemasavancados.sbootreddit.repository.UserRepository;
import com.egersistemasavancados.sbootreddit.service.exception.PostNotFoundException;
import com.egersistemasavancados.sbootreddit.service.exception.SubredditNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void postCreated(PostRequestRepresentation representation) {
        Subreddit subreddit = this.subredditRepository.findByName(representation.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException("Subreddit not found!"));
        User currentUser = this.authService.getCurrentUser();
        this.postRepository.save(postMapper.map(representation, subreddit, currentUser));
    }

    public PostResponseRepresentation findPostById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToRepresentation(post);
    }

    public List<PostResponseRepresentation> findAllPost() {
        return this.postRepository.findAll().stream().map(postMapper::mapToRepresentation).collect(Collectors.toList());
    }

    public List<PostResponseRepresentation> findPostBySubreddit(Long subredditId) {
        Subreddit subreddit = this.subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = this.postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToRepresentation).collect(Collectors.toList());
    }

    public List<PostResponseRepresentation> findPostByUsername(String username) {
        User user = this.userRepository.findByusername(username).orElseThrow(() -> new UsernameNotFoundException("User name not found!"));
        return this.postRepository.findAllByUser(user).stream().map(postMapper::mapToRepresentation).collect(Collectors.toList());
    }
}
