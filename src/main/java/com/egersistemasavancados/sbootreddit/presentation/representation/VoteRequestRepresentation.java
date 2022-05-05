package com.egersistemasavancados.sbootreddit.presentation.representation;

import com.egersistemasavancados.sbootreddit.domain.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequestRepresentation {

    private VoteType voteType;
    private Long postId;
}
