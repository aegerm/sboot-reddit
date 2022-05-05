package com.egersistemasavancados.sbootreddit.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtConfiguration {

    @Autowired
    private JwtProperties jwtProperties;
    private SecretKey key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(jwtProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String getUserToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(String subject) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS512);

        return Jwts.builder().setHeader(header)
                             .setSubject(subject)
                             .setIssuedAt(new Date())
                             .setExpiration(Date.from(LocalDateTime.now().plusMinutes(jwtProperties.getExpiration()).atZone(ZoneId.systemDefault()).toInstant()))
                             .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Token invalido: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Token expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Formato do token não suportado: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token não reconhecido: " + ex.getMessage());
        }

        return false;
    }

    public Long jwtExpirationInMinutes() {
        return jwtProperties.getExpiration();
    }
}
