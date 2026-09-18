package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ObjectifResponse;
import com.audit.audit_document.application.dto.TdrResponse;
import com.audit.audit_document.application.usecases.GetAllTdrsUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.repository.TdrRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllTdrsService
        implements GetAllTdrsUseCase {

    private final TdrRepository tdrRepository;

    public GetAllTdrsService(
            TdrRepository tdrRepository) {

        this.tdrRepository = tdrRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TdrResponse> execute() {

        List<Tdr> tdrs =
                tdrRepository.findAll();

        List<TdrResponse> responses =
                new ArrayList<>();

        for (Tdr tdr : tdrs) {

            responses.add(
                    buildResponse(tdr));
        }

        return responses;
    }

    private TdrResponse buildResponse(Tdr tdr) {

        TdrResponse response =
                new TdrResponse();

        response.setId(
                tdr.getId());

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

            // Structure
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