package com.egersistemasavancados.sbootreddit.config.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "secret.app")
@Data
@NoArgsConstructor
public class JwtProperties {

    private String jwtSecret;
    private long expiration;
}
