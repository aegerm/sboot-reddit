package com.egersistemasavancados.sbootreddit.presentation;

import com.egersistemasavancados.sbootreddit.presentation.representation.VoteRequestRepresentation;
import com.egersistemasavancados.sbootreddit.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteRequestRepresentation representation) {
        this.voteService.vote(representation);
        return ResponseEntity.ok().build();
    }
}
