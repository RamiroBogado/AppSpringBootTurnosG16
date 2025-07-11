package com.unla.tp_oo2_g16.RestControllers;

import com.unla.tp_oo2_g16.dtos.LoginRequestDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok("Login exitoso: " + loginRequest.username());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
