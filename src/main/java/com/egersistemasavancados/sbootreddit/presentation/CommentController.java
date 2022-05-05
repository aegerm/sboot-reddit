package com.egersistemasavancados.sbootreddit.presentation;

import com.egersistemasavancados.sbootreddit.presentation.representation.CommentRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.CommentResponseRepresentation;
import com.egersistemasavancados.sbootreddit.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequestRepresentation representation) {
        this.service.saveComment(representation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentResponseRepresentation>> findAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(this.service.findAllCommentsForPost(postId));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CommentResponseRepresentation>> findAllCommentsForUser(@PathVariable String username) {
        return ResponseEntity.ok(this.service.findAllCommentsForUser(username));
    }
}
