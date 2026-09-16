package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.GetAllUtilisateursUseCase;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUtilisateursService implements GetAllUtilisateursUseCase {

    private final UtilisateurRepository utilisateurRepository;

    public GetAllUtilisateursService(
            UtilisateurRepository utilisateurRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<UtilisateurResponse> execute() {

        return utilisateurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private UtilisateurResponse toResponse(Utilisateur utilisateur) {

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