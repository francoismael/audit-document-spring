package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.LoginRequest;
import com.audit.audit_document.application.dto.LoginResponse;
import com.audit.audit_document.application.usecases.LoginUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import com.audit.audit_document.infrastructure.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(
            UtilisateurRepository utilisateurRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse execute(LoginRequest request) {

        Utilisateur utilisateur = utilisateurRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Username or password incorrect"));

        if (!utilisateur.getActif()) {
            throw new RuntimeException("Utilisateur désactivé");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                utilisateur.getPassword()
        )) {
            throw new RuntimeException("Username or password incorrect");
        }

        String token = jwtService.generateToken(utilisateur);

        return new LoginResponse(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getEmail(),
                utilisateur.getRole(),
                token
        );
    }
}