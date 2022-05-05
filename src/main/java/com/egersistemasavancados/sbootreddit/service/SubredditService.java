package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.Subreddit;
import com.egersistemasavancados.sbootreddit.mapper.SubredditMapper;
import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditResponseRepresentation;
import com.egersistemasavancados.sbootreddit.repository.SubredditRepository;
import com.egersistemasavancados.sbootreddit.service.exception.SubredditNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    public SubredditResponseRepresentation save(SubredditRequestRepresentation representation) {
        Subreddit subreddit = subredditMapper.mapRepresentationToSubreddit(representation);

        Subreddit saved = this.subredditRepository.saveAndFlush(subreddit);

        return SubredditResponseRepresentation.builder().id(saved.getId()).name(saved.getName()).description(saved.getDescription()).build();
    }

    public List<SubredditResponseRepresentation> findAll() {
        return this.subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToRepresentation).collect(Collectors.toList());
    }

    public SubredditResponseRepresentation findById(Long id) {
        Subreddit subreddit = this.subredditRepository.findById(id).orElseThrow(() -> new SubredditNotFoundException(id.toString()));
        return subredditMapper.mapSubredditToRepresentation(subreddit);
    }
}
