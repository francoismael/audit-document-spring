package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.LoginRequest;
import com.audit.audit_document.application.dto.LoginResponse;
import com.audit.audit_document.application.usecases.LoginUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    try {
        return ResponseEntity.ok(
                loginUseCase.execute(request)
        );

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
    }
}