package com.egersistemasavancados.sbootreddit.presentation;

import com.egersistemasavancados.sbootreddit.presentation.representation.PostRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.PostResponseRepresentation;
import com.egersistemasavancados.sbootreddit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequestRepresentation representation) {
        this.postService.postCreated(representation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseRepresentation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.postService.findPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseRepresentation>> findAll() {
        return ResponseEntity.ok(this.postService.findAllPost());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponseRepresentation>> findPostBySubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(this.postService.findPostBySubreddit(id));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<PostResponseRepresentation>> findPostByUsername(@PathVariable String username) {
        return ResponseEntity.ok(this.postService.findPostByUsername(username));
    }
}
