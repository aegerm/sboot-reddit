package com.egersistemasavancados.sbootreddit.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestRepresentation implements Serializable {

    private Long postId;
    private String postName;
    private String subredditName;
    private String url;
    private String description;
}
