package com.egersistemasavancados.sbootreddit.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RefreshTokenRequestRepresentation implements Serializable {

    private String refreshToken;
    private String username;
}
