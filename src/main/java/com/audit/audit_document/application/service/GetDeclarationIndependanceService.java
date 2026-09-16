package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.GetByIdDeclarationIndependanceUseCase;
import com.audit.audit_document.domain.entity.DeclarationIndependance;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;
import org.springframework.stereotype.Service;

@Service
public class GetDeclarationIndependanceService
        implements GetByIdDeclarationIndependanceUseCase {

    private final DeclarationIndependanceRepository declarationRepository;

    public GetDeclarationIndependanceService(
            DeclarationIndependanceRepository declarationRepository) {

        this.declarationRepository = declarationRepository;
    }

    @Override
    public DeclarationIndependanceResponse execute(Long id) {

        DeclarationIndependance declaration =
                declarationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Déclaration d'indépendance introuvable : "
                                                + id
                                )
                        );

        Mission mission = declaration.getMission();
        Personne personne = declaration.getPersonne();

        DeclarationIndependanceResponse response =
                new DeclarationIndependanceResponse();

        response.setId(declaration.getId());

        // Mission Information
        response.setMissionId(mission.getId());
        response.setMissionNumero(mission.getNumero());
        response.setMissionIntitule(mission.getIntitule());

        // Personne Information
        response.setPersonneId(personne.getId());
        response.setNom(personne.getNom());
        response.setPrenom(personne.getPrenom());
        response.setMatricule(personne.getMatricule());
        response.setFonction(personne.getFonction());
        response.setService(personne.getServices());

        // Declaration Information
        response.setDateDeclaration(
                declaration.getDateDeclaration()
        );

        return response;
    }
}