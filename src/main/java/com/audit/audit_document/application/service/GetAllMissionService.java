package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.GetAllMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.MissionStatus;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@Service
public class GetAllMissionService implements GetAllMissionUseCase {

    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;

    private final MissionAccess missionAccess;

    public GetAllMissionService(
            MissionRepository missionRepository,
            MissionPersonneRepository missionPersonneRepository,
            MissionAccess missionAccess) {

        this.missionRepository = missionRepository;
        this.missionPersonneRepository = missionPersonneRepository;
        this.missionAccess = missionAccess;
    }

    @Override
    public List<MissionResponse> execute(String query, String statut) {

        MissionStatus wantedStatus = parseStatut(statut);
        LocalDate today = LocalDate.now();

        List<MissionResponse> responses = new ArrayList<>();

        Predicate<Long> visibleMissions = missionAccess.visibleMissions();

        for (Mission mission : missionRepository.findAll()) {

            String structureNom =
                    mission.getStructure() != null ? mission.getStructure().getNom() : null;

            if (!visibleMissions.test(mission.getId())
                    || !SearchText.matches(query, mission.getNumero(), mission.getIntitule(), structureNom)
                    || (wantedStatus != null && MissionStatus.of(mission, today) != wantedStatus)) {
                continue;
            }

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

                personneResponse.setSexe(
                        missionPersonne.getPersonne().getSexe()
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

    private MissionStatus parseStatut(String statut) {

        if (statut == null || statut.trim().isEmpty()) {
            return null;
        }

        try {
            return MissionStatus.valueOf(statut.trim().toUpperCase(Locale.ROOT));

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException(
                    "Statut de mission inconnu : " + statut
                            + ". Valeurs possibles : A_VENIR, EN_COURS, TERMINEE, SANS_DATES.");
        }
    }
}