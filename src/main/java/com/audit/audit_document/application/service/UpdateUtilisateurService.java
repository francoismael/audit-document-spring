package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UpdateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.UpdateUtilisateurUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUtilisateurService implements UpdateUtilisateurUseCase {

    private final UtilisateurRepository utilisateurRepository;

    public UpdateUtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurResponse execute(Long id, UpdateUtilisateurRequest request) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        if (!utilisateur.getUsername().equals(request.getUsername())
                && utilisateurRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (!utilisateur.getEmail().equals(request.getEmail())
                && utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        utilisateur.setUsername(request.getUsername());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setRole(request.getRole());

        Utilisateur updated = utilisateurRepository.save(utilisateur);

        return new UtilisateurResponse(
                updated.getId(),
                updated.getUsername(),
                updated.getEmail(),
                updated.getRole(),
                updated.getActif(),
                updated.getDateCreation()
        );
    }
}