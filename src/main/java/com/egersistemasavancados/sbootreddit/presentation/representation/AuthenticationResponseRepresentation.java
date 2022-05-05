package com.egersistemasavancados.sbootreddit.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseRepresentation implements Serializable {

    private String authToken;
    private String refreshToken;
    private Date expireAt;
    private String username;
}
