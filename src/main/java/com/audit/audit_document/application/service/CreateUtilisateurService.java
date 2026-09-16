package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.CreateUtilisateurUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateUtilisateurService implements CreateUtilisateurUseCase {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateUtilisateurService(
            UtilisateurRepository utilisateurRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurResponse execute(CreateUtilisateurRequest request) {

        if (utilisateurRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setUsername(request.getUsername());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        utilisateur.setRole(request.getRole());
        utilisateur.setActif(true);
        utilisateur.setDateCreation(LocalDateTime.now());

        Utilisateur saved = utilisateurRepository.save(utilisateur);

        return new UtilisateurResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole(),
                saved.getActif(),
                saved.getDateCreation()
        );
    }
}