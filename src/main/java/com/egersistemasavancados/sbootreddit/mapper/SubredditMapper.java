package com.egersistemasavancados.sbootreddit.mapper;

import com.egersistemasavancados.sbootreddit.domain.Post;
import com.egersistemasavancados.sbootreddit.domain.Subreddit;
import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.SubredditResponseRepresentation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditResponseRepresentation mapSubredditToRepresentation(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapRepresentationToSubreddit(SubredditRequestRepresentation representation);
}
