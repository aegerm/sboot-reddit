package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.config.jwt.JwtConfiguration;
import com.egersistemasavancados.sbootreddit.domain.JwtToken;
import com.egersistemasavancados.sbootreddit.domain.NotificationEmail;
import com.egersistemasavancados.sbootreddit.domain.User;
import com.egersistemasavancados.sbootreddit.domain.VerificationToken;
import com.egersistemasavancados.sbootreddit.presentation.representation.AuthenticationResponseRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.LoginRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.RefreshTokenRequestRepresentation;
import com.egersistemasavancados.sbootreddit.presentation.representation.RegisterUserRequestRepresentation;
import com.egersistemasavancados.sbootreddit.repository.UserRepository;
import com.egersistemasavancados.sbootreddit.repository.VerificationTokenRepository;
import com.egersistemasavancados.sbootreddit.service.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final JwtConfiguration jwtConfiguration;

    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponseRepresentation authenticationLogin(LoginRequestRepresentation login) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = this.jwtConfiguration.generateToken(userDetails.getUsername());
        return AuthenticationResponseRepresentation.builder()
                .authToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expireAt(Date.from(LocalDateTime.now().plusMinutes(jwtConfiguration.jwtExpirationInMinutes()).atZone(ZoneId.systemDefault()).toInstant()))
                .username(login.getUsername())
                .build();
    }

    public void registerUser(RegisterUserRequestRepresentation request) {
        User user = User.builder().username(request.getUsername())
                                  .email(request.getEmail())
                                  .password(this.passwordEncoder.encode(request.getPassword()))
                                  .created(Instant.now())
                                  .enabled(false)
                                  .build();

        this.userRepository.saveAndFlush(user);

        String token = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Ativar Conta", user.getEmail(), "Clique aqui para ativar a conta: http://localhost:8080/v1/api/auth/account-verification/" + token));
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = this.verificationTokenRepository.findByToken(token);
        VerificationToken myToken = verificationToken.orElseThrow(() -> new GlobalException("Invalid token!"));
        enableUser(myToken);
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByusername(currentUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User name not found!"));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public AuthenticationResponseRepresentation refreshToken(RefreshTokenRequestRepresentation representation) {
        this.refreshTokenService.validateRefreshToken(representation.getRefreshToken());
        String token = jwtConfiguration.generateToken(representation.getUsername());
        return AuthenticationResponseRepresentation.builder()
                .authToken(token)
                .refreshToken(representation.getRefreshToken())
                .expireAt(Date.from(LocalDateTime.now().plusMinutes(jwtConfiguration.jwtExpirationInMinutes()).atZone(ZoneId.systemDefault()).toInstant()))
                .username(representation.getUsername())
                .build();
    }

    private void enableUser(VerificationToken verificationToken) {
        User user = this.userRepository.findByusername(verificationToken.getUser().getUsername()).orElseThrow(() -> new GlobalException("User not found!"));
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder().token(token).user(user).build();
        this.verificationTokenRepository.save(verificationToken);
        return token;
    }
}
