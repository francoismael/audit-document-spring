package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateTdrRequest;
import com.audit.audit_document.application.dto.ObjectifRequest;
import com.audit.audit_document.application.dto.ObjectifResponse;
import com.audit.audit_document.application.dto.TdrResponse;
import com.audit.audit_document.application.usecases.CreateTdrUseCase;

import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Tdr;

import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.ObjectifRepository;
import com.audit.audit_document.domain.repository.TdrRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateTdrService
        implements CreateTdrUseCase {

    private final TdrRepository tdrRepository;
    private final MissionRepository missionRepository;
    private final ObjectifRepository objectifRepository;

    public CreateTdrService(
            TdrRepository tdrRepository,
            MissionRepository missionRepository,
            ObjectifRepository objectifRepository) {

        this.tdrRepository = tdrRepository;
        this.missionRepository = missionRepository;
        this.objectifRepository = objectifRepository;
    }

    @Override
    @Transactional
    public TdrResponse execute(CreateTdrRequest request) {

        /*
         * 1. Vérifier que la mission existe
         */
        Mission mission =
                missionRepository.findById(request.getMissionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Mission introuvable : "
                                        + request.getMissionId()));

        /*
         * 2. Vérifier qu'un TDR n'existe pas déjà
         *    pour cette mission
         */
        if (tdrRepository.existsByMissionId(
                request.getMissionId())) {

            throw new RuntimeException(
                    "Un TDR existe déjà pour cette mission.");
        }

        /*
         * 3. Créer le TDR
         */
        Tdr tdr = new Tdr();

        tdr.setMission(mission);

        tdr.setContexte(
                request.getContexte());

        tdr.setCompetence(
                request.getCompetence());

        tdr.setPerimetre(
                request.getPerimetre());

        tdr.setPeriodeObservation(
                request.getPeriodeObservation());

        tdr.setLieu(
                request.getLieu());

        tdr.setMethodologieTravail(
                request.getMethodologieTravail());

        tdr.setResultatsAttendus(
                request.getResultatsAttendus());

        tdr.setLivrables(
                request.getLivrables());

        /*
         * 4. Sauvegarder le TDR
         *
         * Cette sauvegarde permet à PostgreSQL
         * de générer l'ID du TDR.
         */
        Tdr savedTdr =
                tdrRepository.save(tdr);

        /*
         * 5. Créer les objectifs
         *
         * Le numéro est généré automatiquement
         * pour chaque objectif de ce TDR.
         */
        if (request.getObjectifs() != null) {

            for (ObjectifRequest objectifRequest :
                    request.getObjectifs()) {

                /*
                 * Récupérer le prochain numéro
                 * disponible pour ce TDR.
                 */
                Integer nextNumero =
                        objectifRepository.getNextNumero(
                                savedTdr.getId());

                /*
                 * Exemple :
                 * 1 → OBJ-01
                 * 2 → OBJ-02
                 * 3 → OBJ-03
                 */
                String numero =
                        String.format(
                                "OBJ-%02d",
                                nextNumero);

                Objectif objectif =
                        new Objectif();

                objectif.setNumero(numero);

                objectif.setDescriptions(
                        objectifRequest.getDescriptions());

                /*
                 * Définit automatiquement
                 * la relation objectif → TDR.
                 */
                savedTdr.addObjectif(objectif);
            }
        }

        /*
         * 6. Sauvegarder à nouveau le TDR
         *
         * Grâce à CascadeType.ALL dans Tdr,
         * les objectifs sont également sauvegardés.
         */
        savedTdr =
                tdrRepository.save(savedTdr);

        /*
         * 7. Construire et retourner la réponse
         */
        return buildResponse(savedTdr);
    }

    /*
     * Construction de la réponse
     */
    private TdrResponse buildResponse(Tdr tdr) {

        TdrResponse response =
                new TdrResponse();

        response.setId(
                tdr.getId());

        Mission mission =
                tdr.getMission();

        /*
         * Informations récupérées automatiquement
         * depuis la mission.
         */
        if (mission != null) {

            response.setMissionId(
                    mission.getId());

            response.setMissionNumero(
                    mission.getNumero());

            response.setMissionIntitule(
                    mission.getIntitule());

            response.setMissionObjet(
                    mission.getObjet());

            /*
             * Informations de la structure
             * récupérées depuis la mission.
             */
            if (mission.getStructure() != null) {

                response.setStructureId(
                        mission.getStructure().getId());

                response.setStructureNom(
                        mission.getStructure().getNom());
            }
        }

        /*
         * Informations propres au TDR
         */
        response.setContexte(
                tdr.getContexte());

        response.setCompetence(
                tdr.getCompetence());

        response.setPerimetre(
                tdr.getPerimetre());

        response.setPeriodeObservation(
                tdr.getPeriodeObservation());

        response.setLieu(
                tdr.getLieu());

        response.setMethodologieTravail(
                tdr.getMethodologieTravail());

        response.setResultatsAttendus(
                tdr.getResultatsAttendus());

        response.setLivrables(
                tdr.getLivrables());

        /*
         * Conversion des objectifs
         * Entity → Response DTO
         */
        List<ObjectifResponse> objectifs =
                new ArrayList<>();

        for (Objectif objectif :
                tdr.getObjectifs()) {

            ObjectifResponse objectifResponse =
                    new ObjectifResponse();

            objectifResponse.setId(
                    objectif.getId());

            objectifResponse.setNumero(
                    objectif.getNumero());

            objectifResponse.setDescriptions(
                    objectif.getDescriptions());

            objectifs.add(
                    objectifResponse);
        }

        response.setObjectifs(
                objectifs);

        return response;
    }
}