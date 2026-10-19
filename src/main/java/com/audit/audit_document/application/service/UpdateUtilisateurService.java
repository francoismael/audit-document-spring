package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        if (!utilisateur.getUsername().equals(request.getUsername())
                && utilisateurRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Ce nom d'utilisateur existe déjà");
        }

        if (!utilisateur.getEmail().equals(request.getEmail())
                && utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("Cette adresse e-mail existe déjà");
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