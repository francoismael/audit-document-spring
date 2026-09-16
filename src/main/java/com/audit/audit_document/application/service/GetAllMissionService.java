package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.GetAllMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllMissionService implements GetAllMissionUseCase {

    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;

    public GetAllMissionService(
            MissionRepository missionRepository,
            MissionPersonneRepository missionPersonneRepository) {

        this.missionRepository = missionRepository;
        this.missionPersonneRepository = missionPersonneRepository;
    }

    @Override
    public List<MissionResponse> execute() {

        // find all mission
        List<Mission> missions = missionRepository.findAll();

        List<MissionResponse> responses = new ArrayList<>();

        for (Mission mission : missions) {

            MissionResponse response = new MissionResponse();

            // Mission information
            response.setId(mission.getId());
            response.setNumero(mission.getNumero());
            response.setIntitule(mission.getIntitule());
            response.setObjet(mission.getObjet());
            response.setDateDebut(mission.getDateDebut());
            response.setDateFin(mission.getDateFin());
            response.setDateSignature(mission.getDateSignature());

            // structure information
            if (mission.getStructure() != null) {

                response.setStructureId(
                        mission.getStructure().getId()
                );

                response.setStructureNom(
                        mission.getStructure().getNom()
                );
            }

            // find personne in the mission 
            List<MissionPersonne> missionPersonnes =
                    missionPersonneRepository.findByMissionId(
                            mission.getId()
                    );

            List<MissionPersonneResponse> personnes =
                    new ArrayList<>();

            for (MissionPersonne missionPersonne : missionPersonnes) {

                MissionPersonneResponse personneResponse =
                        new MissionPersonneResponse();

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

            responses.add(response);
        }

        return responses;
    }
}