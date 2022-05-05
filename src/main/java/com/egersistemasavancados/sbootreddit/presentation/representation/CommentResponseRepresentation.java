package com.egersistemasavancados.sbootreddit.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseRepresentation implements Serializable {

    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}
