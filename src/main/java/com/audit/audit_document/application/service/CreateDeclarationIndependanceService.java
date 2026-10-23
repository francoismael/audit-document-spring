package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.CreateDeclarationIndependanceRequest;
import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.CreateDeclarationIndependanceUseCase;
import com.audit.audit_document.domain.entity.DeclarationIndependance;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDeclarationIndependanceService
        implements CreateDeclarationIndependanceUseCase {

    private final DeclarationIndependanceRepository declarationRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    private final MissionDocumentRules missionDocumentRules;

    public CreateDeclarationIndependanceService(
            DeclarationIndependanceRepository declarationRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository,
            MissionDocumentRules missionDocumentRules) {

        this.declarationRepository = declarationRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
        this.missionDocumentRules = missionDocumentRules;
    }

    @Override
    @Transactional
    public DeclarationIndependanceResponse execute(
        CreateDeclarationIndependanceRequest request) {

        missionDocumentRules.checkDeclaration(
                request.getMissionId(),
                request.getPersonneId(),
                request.getDateDeclaration(),
                null);

    Mission mission = missionRepository
            .findById(request.getMissionId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Mission introuvable : "
                                    + request.getMissionId()
                    )
            );

    Personne personne = personneRepository
            .findById(request.getPersonneId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Personne introuvable : "
                                    + request.getPersonneId()
                    )
            );

    DeclarationIndependance declaration =
            new DeclarationIndependance();

    declaration.setMission(mission);
    declaration.setPersonne(personne);
    declaration.setDateDeclaration(request.getDateDeclaration());

    DeclarationIndependance saved =
            declarationRepository.save(declaration);

    DeclarationIndependanceResponse response =
            new DeclarationIndependanceResponse();

    response.setId(saved.getId());

    response.setMissionId(mission.getId());
    response.setMissionNumero(mission.getNumero());
    response.setMissionIntitule(mission.getIntitule());

    response.setPersonneId(personne.getId());
    response.setNom(personne.getNom());
    response.setPrenom(personne.getPrenom());
    response.setMatricule(personne.getMatricule());
    response.setFonction(personne.getFonction());
    response.setService(personne.getServices());

    response.setDateDeclaration(saved.getDateDeclaration());

    return response;
}
}