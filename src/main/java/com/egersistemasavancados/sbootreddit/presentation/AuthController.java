package com.egersistemasavancados.sbootreddit.presentation;

import com.egersistemasavancados.sbootreddit.presentation.representation.*;
import com.egersistemasavancados.sbootreddit.service.AuthService;
import com.egersistemasavancados.sbootreddit.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseRepresentation> login(@RequestBody LoginRequestRepresentation request) {
        return ResponseEntity.ok(this.authService.authenticationLogin(request));
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserResponseRepresentation> registerUser(@RequestBody RegisterUserRequestRepresentation request) {
        this.authService.registerUser(request);
        return ResponseEntity.ok(new UserResponseRepresentation("Usuário criado com sucesso!"));
    }

    @GetMapping("/account-verification/{token}")
    public ResponseEntity<UserResponseRepresentation> accountVerification(@PathVariable String token) {
        this.authService.verifyAccount(token);
        return ResponseEntity.ok(new UserResponseRepresentation("Conta verificada com sucesso!"));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponseRepresentation> refreshToken(@RequestBody RefreshTokenRequestRepresentation representation) {
        return ResponseEntity.ok(this.authService.refreshToken(representation));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequestRepresentation representation) {
        this.service.deleteToken(representation.getRefreshToken());
        return ResponseEntity.ok("Token deleted successfully!!!");
    }
}
