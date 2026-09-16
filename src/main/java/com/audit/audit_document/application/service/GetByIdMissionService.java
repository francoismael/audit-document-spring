package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.GetByIdMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetByIdMissionService implements GetByIdMissionUseCase {

    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;

    public GetByIdMissionService(
            MissionRepository missionRepository,
            MissionPersonneRepository missionPersonneRepository) {

        this.missionRepository = missionRepository;
        this.missionPersonneRepository = missionPersonneRepository;
    }

    @Override
    public MissionResponse execute(Long missionId) {

        Mission mission = missionRepository
                .findById(missionId)
                .orElseThrow(() -> new RuntimeException(
                        "Mission introuvable : " + missionId
                ));

        MissionResponse response = new MissionResponse();

        response.setId(mission.getId());
        response.setNumero(mission.getNumero());
        response.setIntitule(mission.getIntitule());
        response.setObjet(mission.getObjet());
        response.setDateDebut(mission.getDateDebut());
        response.setDateFin(mission.getDateFin());
        response.setDateSignature(mission.getDateSignature());

        if (mission.getStructure() != null) {
            response.setStructureId(mission.getStructure().getId());
            response.setStructureNom(mission.getStructure().getNom());
        }

        List<MissionPersonne> missionPersonnes =
                missionPersonneRepository.findByMissionId(missionId);

        List<MissionPersonneResponse> personnes = new ArrayList<>();

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

        return response;
    }
}