package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
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
         * 1. Check that the mission exists
         */
        Mission mission =
                missionRepository.findById(request.getMissionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Mission introuvable : "
                                        + request.getMissionId()));

        /*
         * 2. Check that a TDR does not already exist
         *    for this mission
         */
        if (tdrRepository.existsByMissionId(
                request.getMissionId())) {

            throw new ConflictException(
                    "Un TDR existe déjà pour cette mission.");
        }

        /*
         * 3. Create the TDR
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
         * 4. Save the TDR
         *
         * This save lets PostgreSQL
         * generate the TDR ID.
         */
        Tdr savedTdr =
                tdrRepository.save(tdr);

        /*
         * 5. Create the objectives
         *
         * The number is generated automatically
         * for each objective of this TDR.
         */
        if (request.getObjectifs() != null) {

            for (ObjectifRequest objectifRequest :
                    request.getObjectifs()) {

                /*
                 * Get the next number
                 * available for this TDR.
                 */
                Integer nextNumero =
                        objectifRepository.getNextNumero(
                                savedTdr.getId());

                /*
                 * Example:
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
                 * Automatically sets
                 * the objective → TDR relation.
                 */
                savedTdr.addObjectif(objectif);
            }
        }

        /*
         * 6. Save the TDR again
         *
         * Thanks to CascadeType.ALL in Tdr,
         * the objectives are saved as well.
         */
        savedTdr =
                tdrRepository.save(savedTdr);

        /*
         * 7. Build and return the response
         */
        return buildResponse(savedTdr);
    }

    /*
     * Build the response
     */
    private TdrResponse buildResponse(Tdr tdr) {

        TdrResponse response =
                new TdrResponse();

        response.setId(
                tdr.getId());

        Mission mission =
                tdr.getMission();

        /*
         * Information retrieved automatically
         * from the mission.
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
             * Structure information
             * retrieved from the mission.
             */
            if (mission.getStructure() != null) {

                response.setStructureId(
                        mission.getStructure().getId());

                response.setStructureNom(
                        mission.getStructure().getNom());
            }
        }

        /*
         * TDR-specific information
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
         * Convert the objectives
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