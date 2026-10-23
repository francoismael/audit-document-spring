package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionPersonneRequest;
import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.UpdateMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.MissionRole;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UpdateMissionService implements UpdateMissionUseCase {

    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;
    private final PersonneRepository personneRepository;
    private final StructureRepository structureRepository;

    public UpdateMissionService(
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
    public MissionResponse execute(
            Long id,
            CreateMissionRequest request) {

        MissionRequestValidator.validate(request);

        // find mission
        Mission mission = missionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mission introuvable : " + id
                        ));

        // find structure
        Structure structure = structureRepository
                .findById(request.getStructureId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Structure introuvable : "
                                        + request.getStructureId()
                        ));

        // update mission information
        mission.setIntitule(request.getIntitule());
        mission.setObjet(request.getObjet());
        mission.setDateDebut(request.getDateDebut());
        mission.setDateFin(request.getDateFin());
        mission.setDateSignature(request.getDateSignature());
        mission.setStructure(structure);

        Mission missionUpdated =
                missionRepository.save(mission);

        updateTeam(missionUpdated, request.getPersonnes());

        // create response
        MissionResponse response = new MissionResponse();

        response.setId(missionUpdated.getId());
        response.setNumero(missionUpdated.getNumero());
        response.setIntitule(missionUpdated.getIntitule());
        response.setObjet(missionUpdated.getObjet());
        response.setDateDebut(missionUpdated.getDateDebut());
        response.setDateFin(missionUpdated.getDateFin());
        response.setDateSignature(
                missionUpdated.getDateSignature()
        );

        response.setStructureId(
                structure.getId()
        );

        response.setStructureNom(
                structure.getNom()
        );

        // list personne after modification
        List<MissionPersonne> nouvellesPersonnes =
                missionPersonneRepository.findByMissionId(id);

        List<MissionPersonneResponse> personnes =
                new ArrayList<>();

        for (MissionPersonne missionPersonne
                : nouvellesPersonnes) {

            MissionPersonneResponse personneResponse =
                    new MissionPersonneResponse();

            personneResponse.setMissionPersonneId(
                    missionPersonne.getId()
            );

            personneResponse.setPersonneId(
                    missionPersonne.getPersonne().getId()
            );

            personneResponse.setNom(
                    missionPersonne.getPersonne().getNom()
            );

            personneResponse.setPrenom(
                    missionPersonne.getPersonne().getPrenom()
            );

            personneResponse.setFonction(
                    missionPersonne.getPersonne().getFonction()
            );

            personneResponse.setServices(
                    missionPersonne.getPersonne().getServices()
            );

            personneResponse.setRoles(
                    missionPersonne.getRoles()
            );

            personnes.add(personneResponse);
        }

        response.setPersonnes(personnes);

        return response;
    }

    /*
     * Applies the new team without recreating the members that stay:
     * their missionPersonneId is used by the business documents (tests, PV d'affirmation...).
     */
    private void updateTeam(
            Mission mission,
            List<MissionPersonneRequest> requestedMembers) {

        List<MissionPersonneRequest> requested =
                requestedMembers != null
                        ? requestedMembers
                        : new ArrayList<>();

        Set<String> requestedKeys = new HashSet<>();

        for (MissionPersonneRequest member : requested) {
            requestedKeys.add(memberKey(
                    member.getPersonneId(),
                    MissionRole.fromValue(member.getRoles()).name()));
        }

        Set<String> existingKeys = new HashSet<>();

        for (MissionPersonne existing
                : missionPersonneRepository.findByMissionId(mission.getId())) {

            String key = memberKey(
                    existing.getPersonne().getId(),
                    existing.getRoles());

            if (requestedKeys.contains(key)) {
                existingKeys.add(key);
            } else {
                missionPersonneRepository.deleteById(existing.getId());
            }
        }

        for (MissionPersonneRequest member : requested) {

            String role = MissionRole.fromValue(member.getRoles()).name();

            if (existingKeys.contains(memberKey(member.getPersonneId(), role))) {
                continue;
            }

            Personne personne = personneRepository
                    .findById(member.getPersonneId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Personne introuvable : "
                                            + member.getPersonneId()));

            MissionPersonne missionPersonne = new MissionPersonne();
            missionPersonne.setMission(mission);
            missionPersonne.setPersonne(personne);
            missionPersonne.setRoles(role);

            missionPersonneRepository.save(missionPersonne);
        }
    }

    private static String memberKey(Long personneId, String role) {
        return personneId + ":" + role;
    }
}
