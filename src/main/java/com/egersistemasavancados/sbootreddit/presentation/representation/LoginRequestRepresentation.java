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
public class LoginRequestRepresentation implements Serializable {

    private String username;
    private String password;
}
