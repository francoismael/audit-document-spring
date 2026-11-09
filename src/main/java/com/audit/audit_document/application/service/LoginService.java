package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.LoginRequest;
import com.audit.audit_document.application.dto.LoginResponse;
import com.audit.audit_document.application.usecases.LoginUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import com.audit.audit_document.infrastructure.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private static final String INVALID_CREDENTIALS =
            "Nom d'utilisateur ou mot de passe incorrect";

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

        if (request == null
                || request.getUsername() == null
                || request.getPassword() == null) {

            throw new BadCredentialsException(INVALID_CREDENTIALS);
        }

        Utilisateur utilisateur = utilisateurRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BadCredentialsException(INVALID_CREDENTIALS));

        // The password is checked first so a disabled account is not revealed to strangers.
        if (!passwordEncoder.matches(
                request.getPassword(),
                utilisateur.getPassword()
        )) {
            throw new BadCredentialsException(INVALID_CREDENTIALS);
        }

        if (!Boolean.TRUE.equals(utilisateur.getActif())) {
            throw new DisabledException(
                    "Ce compte n'est pas actif : un administrateur doit l'activer.");
        }

        String token = jwtService.generateToken(utilisateur);

        return new LoginResponse(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getRole(),
                token
        );
    }
}
