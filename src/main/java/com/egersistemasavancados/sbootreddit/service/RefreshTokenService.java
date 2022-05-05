package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.RefreshToken;
import com.egersistemasavancados.sbootreddit.repository.RefreshTokenRepository;
import com.egersistemasavancados.sbootreddit.service.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return this.refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        this.refreshTokenRepository.findByToken(token).orElseThrow(() -> new GlobalException("Invalid refresh token"));
    }

    public void deleteToken(String token) {
        this.refreshTokenRepository.deleteByToken(token);
    }
}
