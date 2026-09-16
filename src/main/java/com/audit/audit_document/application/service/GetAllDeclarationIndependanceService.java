package com.audit.audit_document.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.GetAllDeclarationIndependanceUseCase;
import com.audit.audit_document.domain.entity.DeclarationIndependance;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;

@Service
public class GetAllDeclarationIndependanceService
        implements GetAllDeclarationIndependanceUseCase {

    private final DeclarationIndependanceRepository declarationRepository;

    public GetAllDeclarationIndependanceService(
            DeclarationIndependanceRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    @Override
    public List<DeclarationIndependanceResponse> execute() {

        List<DeclarationIndependance> declarations =
                declarationRepository.findAll();

        return declarations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private DeclarationIndependanceResponse toResponse(
            DeclarationIndependance declaration) {

        Mission mission = declaration.getMission();
        Personne personne = declaration.getPersonne();

        DeclarationIndependanceResponse response =
                new DeclarationIndependanceResponse();

        response.setId(declaration.getId());

        response.setMissionId(mission.getId());
        response.setMissionNumero(mission.getNumero());
        response.setMissionIntitule(mission.getIntitule());

        response.setPersonneId(personne.getId());
        response.setNom(personne.getNom());
        response.setPrenom(personne.getPrenom());
        response.setMatricule(personne.getMatricule());
        response.setFonction(personne.getFonction());
        response.setService(personne.getServices());

        response.setDateDeclaration(
                declaration.getDateDeclaration());

        return response;
    }
}