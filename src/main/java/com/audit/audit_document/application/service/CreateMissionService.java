package com.audit.audit_document.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionPersonneRequest;
import com.audit.audit_document.application.usecases.CreateMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

@Service
public class CreateMissionService implements CreateMissionUseCase {

    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;
    private final PersonneRepository personneRepository;
    private final StructureRepository structureRepository;

    public CreateMissionService(
            MissionRepository missionRepository,
            MissionPersonneRepository missionPersonneRepository,
            PersonneRepository personneRepository,
            StructureRepository structureRepository) {

        this.missionRepository = missionRepository;
        this.missionPersonneRepository = missionPersonneRepository;
        this.personneRepository = personneRepository;
        this.structureRepository = structureRepository;
    }

    @Override
    @Transactional
    public Mission execute(CreateMissionRequest request) {

        // Find the structure
        Structure structure = structureRepository
                .findById(request.getStructureId())
                .orElseThrow(() ->
                        new RuntimeException("Structure introuvable"));

        // Create mission
        Mission mission = new Mission();

        // Generate mission number automatically
        long number = missionRepository.getNextNumero();
        String numero = String.format("%03d", number);

        mission.setNumero(numero);

        mission.setIntitule(request.getIntitule());
        mission.setObjet(request.getObjet());
        mission.setDateDebut(request.getDateDebut());
        mission.setDateFin(request.getDateFin());
        mission.setDateSignature(request.getDateSignature());
        mission.setStructure(structure);

        Mission missionSaved = missionRepository.save(mission);

        // Add people assigned to the mission
        if (request.getPersonnes() != null) {

            for (MissionPersonneRequest personneRequest
                    : request.getPersonnes()) {

                Personne personne = personneRepository
                        .findById(personneRequest.getPersonneId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Personne introuvable : "
                                        + personneRequest.getPersonneId()));

                MissionPersonne missionPersonne =
                        new MissionPersonne();

                missionPersonne.setMission(missionSaved);
                missionPersonne.setPersonne(personne);
                missionPersonne.setRoles(personneRequest.getRoles());

                missionPersonneRepository.save(missionPersonne);
            }
        }

        return missionSaved;
    }
}