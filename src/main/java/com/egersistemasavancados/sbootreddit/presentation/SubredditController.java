package com.egersistemasavancados.sbootreddit.presentation;

import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditResponseRepresentation;
import com.egersistemasavancados.sbootreddit.service.SubredditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/subreddit")
@RequiredArgsConstructor
public class SubredditController {

    private final SubredditService service;

    @PostMapping
    public ResponseEntity<SubredditResponseRepresentation> createSubreddit(@RequestBody SubredditRequestRepresentation representation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(representation));
    }

    @GetMapping
    public ResponseEntity<List<SubredditResponseRepresentation>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditResponseRepresentation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findById(id));
    }
}
