package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteUtilisateurUseCase;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUtilisateurService implements DeleteUtilisateurUseCase {

    private final UtilisateurRepository utilisateurRepository;

    public DeleteUtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void execute(Long id) {

        utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        utilisateurRepository.deleteById(id);
    }
}