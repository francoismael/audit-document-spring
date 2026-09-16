package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.ChangeUtilisateurStatusUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangeUtilisateurStatusService implements ChangeUtilisateurStatusUseCase {

    private final UtilisateurRepository utilisateurRepository;

    public ChangeUtilisateurStatusService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurResponse execute(Long id, Boolean actif) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        utilisateur.setActif(actif);

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