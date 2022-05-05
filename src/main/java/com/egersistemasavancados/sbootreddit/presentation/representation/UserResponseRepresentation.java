package com.egersistemasavancados.sbootreddit.presentation.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseRepresentation implements Serializable {

    private String messageResponse;
}
