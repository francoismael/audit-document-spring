package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateTdrRequest;
import com.audit.audit_document.application.dto.ObjectifRequest;
import com.audit.audit_document.application.dto.ObjectifResponse;
import com.audit.audit_document.application.dto.TdrResponse;
import com.audit.audit_document.application.usecases.UpdateTdrUseCase;

import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Tdr;

import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.ObjectifRepository;
import com.audit.audit_document.domain.repository.TdrRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UpdateTdrService
        implements UpdateTdrUseCase {

    private final TdrRepository tdrRepository;
    private final MissionRepository missionRepository;
    private final ObjectifRepository objectifRepository;

    public UpdateTdrService(
            TdrRepository tdrRepository,
            MissionRepository missionRepository,
            ObjectifRepository objectifRepository) {

        this.tdrRepository = tdrRepository;
        this.missionRepository = missionRepository;
        this.objectifRepository = objectifRepository;
    }

    @Override
    @Transactional
    public TdrResponse execute(
            Long id,
            CreateTdrRequest request) {

        /*
         * 1. Find the existing TDR
         */
        Tdr tdr =
                tdrRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "TDR introuvable : " + id));

        /*
         * 2. Find the mission
         */
        Mission mission =
                missionRepository.findById(
                        request.getMissionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Mission introuvable : "
                                        + request.getMissionId()));

        /*
         * 3. Check if the mission already belongs
         *    to another TDR
         */
        if (!mission.getId().equals(
                tdr.getMission().getId())) {

            if (tdrRepository.existsByMissionId(
                    request.getMissionId())) {

                throw new RuntimeException(
                        "Un TDR existe déjà pour cette mission.");
            }
        }

        /*
         * 4. Update mission
         */
        tdr.setMission(mission);

        /*
         * 5. Update TDR fields
         */
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
         * 6. Get existing objectives
         */
        List<Objectif> existingObjectifs =
                objectifRepository.findByTdrId(
                        tdr.getId());

        /*
         * IDs of objectives received in the request.
         *
         * Used to know which existing objectives
         * must be kept.
         */
        Set<Long> requestObjectifIds =
                new HashSet<>();

        /*
         * 7. Find the next available objective number
         *
         * Example:
         *
         * Existing:
         * OBJ-01
         * OBJ-03
         *
         * nextNumero = 4
         */
        int nextNumero = getNextNumero(existingObjectifs);

        /*
         * 8. Process objectives from the request
         */
        if (request.getObjectifs() != null) {

            for (ObjectifRequest objectifRequest :
                    request.getObjectifs()) {

                /*
                 * Existing objective
                 */
                if (objectifRequest.getId() != null) {

                    Objectif existingObjectif =
                            findExistingObjectif(
                                    existingObjectifs,
                                    objectifRequest.getId());

                    /*
                     * Verify that the objective belongs
                     * to this TDR.
                     */
                    if (existingObjectif == null) {

                        throw new RuntimeException(
                                "Objectif introuvable ou "
                                + "n'appartenant pas à ce TDR : "
                                + objectifRequest.getId());
                    }

                    /*
                     * Remember this objective.
                     */
                    requestObjectifIds.add(
                            existingObjectif.getId());

                    /*
                     * Update only the description.
                     *
                     * ID and numero are preserved.
                     */
                    existingObjectif.setDescriptions(
                            objectifRequest.getDescriptions());

                } else {

                    /*
                     * New objective
                     */
                    Objectif newObjectif =
                            new Objectif();

                    /*
                     * Generate a new number.
                     */
                    String numero =
                            String.format(
                                    "OBJ-%02d",
                                    nextNumero);

                    newObjectif.setNumero(
                            numero);

                    newObjectif.setDescriptions(
                            objectifRequest.getDescriptions());

                    /*
                     * Link the objective to the TDR.
                     */
                    tdr.addObjectif(newObjectif);

                    /*
                     * Prepare the next number.
                     */
                    nextNumero++;
                }
            }
        }

        /*
         * 9. Delete objectives that are no longer
         *    present in the request.
         */
        List<Objectif> objectifsToDelete =
                new ArrayList<>();

        for (Objectif objectif :
                existingObjectifs) {

            if (!requestObjectifIds.contains(
                    objectif.getId())) {

                objectifsToDelete.add(objectif);
            }
        }

        /*
         * Remove the objectives from the TDR.
         */
        for (Objectif objectif :
                objectifsToDelete) {

            tdr.removeObjectif(objectif);
        }

        /*
         * 10. Save the TDR
         *
         * Existing objectives are updated.
         * New objectives are inserted.
         */
        Tdr savedTdr =
                tdrRepository.save(tdr);

        /*
         * 11. Build response
         */
        return buildResponse(savedTdr);
    }

    /*
     * Find the highest objective number
     * currently used by this TDR.
     */
    private int getNextNumero(
            List<Objectif> objectifs) {

        int maxNumero = 0;

        for (Objectif objectif :
                objectifs) {

            String numero =
                    objectif.getNumero();

            if (numero == null) {
                continue;
            }

            /*
             * Example:
             *
             * OBJ-01
             *      ↑
             * Extract 01
             */
            try {

                String numberPart =
                        numero.substring(4);

                int number =
                        Integer.parseInt(numberPart);

                if (number > maxNumero) {
                    maxNumero = number;
                }

            } catch (Exception ignored) {
                /*
                 * Ignore invalid existing numbers.
                 */
            }
        }

        return maxNumero + 1;
    }

    /*
     * Find an objective by ID among
     * the objectives of the current TDR.
     */
    private Objectif findExistingObjectif(
            List<Objectif> objectifs,
            Long objectifId) {

        for (Objectif objectif :
                objectifs) {

            if (objectif.getId().equals(
                    objectifId)) {

                return objectif;
            }
        }

        return null;
    }

    private TdrResponse buildResponse(Tdr tdr) {

        TdrResponse response =
                new TdrResponse();

        response.setId(
                tdr.getId());

        /*
         * Mission
         */
        Mission mission =
                tdr.getMission();

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
             * Structure
             */
            if (mission.getStructure() != null) {

                response.setStructureId(
                        mission.getStructure().getId());

                response.setStructureNom(
                        mission.getStructure().getNom());
            }
        }

        /*
         * TDR
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
         * Objectifs
         */
        List<ObjectifResponse> objectifResponses =
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

            objectifResponses.add(
                    objectifResponse);
        }

        response.setObjectifs(
                objectifResponses);

        return response;
    }
}