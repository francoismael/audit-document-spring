package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ObjectifResponse;
import com.audit.audit_document.application.dto.TdrResponse;
import com.audit.audit_document.application.usecases.GetTdrByIdUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.repository.TdrRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTdrByIdService
        implements GetTdrByIdUseCase {

    private final TdrRepository tdrRepository;

    public GetTdrByIdService(
            TdrRepository tdrRepository) {

        this.tdrRepository = tdrRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TdrResponse execute(Long id) {

        Tdr tdr =
                tdrRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "TDR introuvable : " + id));

        return buildResponse(tdr);
    }

    private TdrResponse buildResponse(Tdr tdr) {

        TdrResponse response =
                new TdrResponse();

        response.setId(tdr.getId());

        // Mission
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

            // find structure by mission
            if (mission.getStructure() != null) {

                response.setStructureId(
                        mission.getStructure().getId());

                response.setStructureNom(
                        mission.getStructure().getNom());
            }
        }

        // TDR
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

        // Objectifs
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