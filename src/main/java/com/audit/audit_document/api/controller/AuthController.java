package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.ChangePasswordRequest;
import com.audit.audit_document.application.dto.LoginRequest;
import com.audit.audit_document.application.dto.LoginResponse;
import com.audit.audit_document.application.dto.RegisterRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.ChangePasswordUseCase;
import com.audit.audit_document.application.usecases.GetCurrentAccountUseCase;
import com.audit.audit_document.application.usecases.LoginUseCase;
import com.audit.audit_document.application.usecases.RegisterUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    private final RegisterUseCase registerUseCase;

    private final GetCurrentAccountUseCase getCurrentAccountUseCase;

    private final ChangePasswordUseCase changePasswordUseCase;

    public AuthController(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            GetCurrentAccountUseCase getCurrentAccountUseCase,
            ChangePasswordUseCase changePasswordUseCase) {

        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.getCurrentAccountUseCase = getCurrentAccountUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }

    // Login failures are turned into a 401 JSON error by GlobalExceptionHandler.
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    // Public sign-up: the account is a user, inactive until an administrator activates it.
    @PostMapping("/register")
    public ResponseEntity<UtilisateurResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerUseCase.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UtilisateurResponse> getCurrentAccount() {

        return ResponseEntity.ok(getCurrentAccountUseCase.getCurrentAccount());
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {

        changePasswordUseCase.changePassword(request);

        return ResponseEntity.noContent().build();
    }
}
