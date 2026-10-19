package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.usecases.GetMissionPersonnesByMissionUseCase;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetMissionPersonnesByMissionService
        implements GetMissionPersonnesByMissionUseCase {

    private final MissionPersonneRepository missionPersonneRepository;

    public GetMissionPersonnesByMissionService(
            MissionPersonneRepository missionPersonneRepository) {

        this.missionPersonneRepository =
                missionPersonneRepository;
    }

    @Override
    public List<MissionPersonneResponse> getByMissionId(
            Long missionId) {

        if (missionId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la mission est obligatoire."
            );
        }

        List<MissionPersonne> missionPersonnes =
                missionPersonneRepository
                        .findByMissionId(missionId);

        List<MissionPersonneResponse> responses =
                new ArrayList<>();

        for (MissionPersonne missionPersonne :
                missionPersonnes) {

            MissionPersonneResponse response =
                    new MissionPersonneResponse();

            /*
             * ID of the mission_personne relation
             *
             * Example: 72
             */
            response.setMissionPersonneId(
                    missionPersonne.getId()
            );

            /*
             * Person information
             */
            if (missionPersonne.getPersonne() != null) {

                response.setPersonneId(
                        missionPersonne
                                .getPersonne()
                                .getId()
                );

                response.setNom(
                        missionPersonne
                                .getPersonne()
                                .getNom()
                );

                response.setPrenom(
                        missionPersonne
                                .getPersonne()
                                .getPrenom()
                );

                response.setFonction(
                        missionPersonne
                                .getPersonne()
                                .getFonction()
                );

                response.setServices(
                        missionPersonne
                                .getPersonne()
                                .getServices()
                );
            }

            /*
             * Role of the person in the mission
             */
            response.setRoles(
                    missionPersonne.getRoles()
            );

            responses.add(response);
        }

        return responses;
    }
}