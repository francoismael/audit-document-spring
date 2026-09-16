package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.GetUtilisateurByIdUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUtilisateurByIdService implements GetUtilisateurByIdUseCase {

    private final UtilisateurRepository utilisateurRepository;

    public GetUtilisateurByIdService(
            UtilisateurRepository utilisateurRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurResponse execute(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur not found")
                );

        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getUsername(),
                utilisateur.getEmail(),
                utilisateur.getRole(),
                utilisateur.getActif(),
                utilisateur.getDateCreation()
        );
    }
}