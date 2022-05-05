package com.egersistemasavancados.sbootreddit.mapper;

import com.egersistemasavancados.sbootreddit.domain.Comment;
import com.egersistemasavancados.sbootreddit.domain.Post;
import com.egersistemasavancados.sbootreddit.domain.User;
import com.egersistemasavancados.sbootreddit.presentation.representation.CommentRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.CommentResponseRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "text", source = "representation.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment toMap(CommentRequestRepresentation representation, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentRequestRepresentation toRepresentation(Comment comment);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentResponseRepresentation toResponse(Comment comment);
}
