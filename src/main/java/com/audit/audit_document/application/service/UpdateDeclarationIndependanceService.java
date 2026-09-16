package com.audit.audit_document.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.audit_document.application.dto.CreateDeclarationIndependanceRequest;
import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.UpdateDeclarationIndependanceUseCase;
import com.audit.audit_document.domain.entity.DeclarationIndependance;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;

@Service
public class UpdateDeclarationIndependanceService
        implements UpdateDeclarationIndependanceUseCase {

    private final DeclarationIndependanceRepository declarationRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    public UpdateDeclarationIndependanceService(
            DeclarationIndependanceRepository declarationRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository) {

        this.declarationRepository = declarationRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
    }

    @Override
    @Transactional
    public DeclarationIndependanceResponse execute(
            Long id,
            CreateDeclarationIndependanceRequest request) {

        DeclarationIndependance declaration =
                declarationRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(
                                "Déclaration d'indépendance introuvable : " + id));

        Mission mission =
                missionRepository.findById(request.getMissionId())
                        .orElseThrow(() -> new RuntimeException(
                                "Mission introuvable : " + request.getMissionId()));

        Personne personne =
                personneRepository.findById(request.getPersonneId())
                        .orElseThrow(() -> new RuntimeException(
                                "Personne introuvable : " + request.getPersonneId()));

        declaration.setMission(mission);
        declaration.setPersonne(personne);
        declaration.setDateDeclaration(request.getDateDeclaration());

        DeclarationIndependance updated =
                declarationRepository.update(declaration);

        DeclarationIndependanceResponse response =
                new DeclarationIndependanceResponse();

        response.setId(updated.getId());

        response.setMissionId(mission.getId());
        response.setMissionNumero(mission.getNumero());
        response.setMissionIntitule(mission.getIntitule());

        response.setPersonneId(personne.getId());
        response.setNom(personne.getNom());
        response.setPrenom(personne.getPrenom());
        response.setMatricule(personne.getMatricule());
        response.setFonction(personne.getFonction());
        response.setService(personne.getServices());

        response.setDateDeclaration(updated.getDateDeclaration());

        return response;
    }
}