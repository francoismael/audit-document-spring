package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ReunionPersonneResponse;
import com.audit.audit_document.application.dto.ReunionResponse;
import com.audit.audit_document.application.usecases.GetAllReunionsUseCase;
import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.entity.ReunionPersonne;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.ReunionPersonneRepository;
import com.audit.audit_document.domain.repository.ReunionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllReunionsService
        implements GetAllReunionsUseCase {

    private final ReunionRepository reunionRepository;
    private final ReunionPersonneRepository reunionPersonneRepository;

    public GetAllReunionsService(
            ReunionRepository reunionRepository,
            ReunionPersonneRepository reunionPersonneRepository) {

        this.reunionRepository = reunionRepository;
        this.reunionPersonneRepository = reunionPersonneRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReunionResponse> execute() {

        List<Reunion> reunions =
                reunionRepository.findAll();

        List<ReunionResponse> responses =
                new ArrayList<>();

        for (Reunion reunion : reunions) {

            responses.add(
                    buildResponse(reunion));
        }

        return responses;
    }

    private ReunionResponse buildResponse(Reunion reunion) {

        ReunionResponse response =
                new ReunionResponse();

        response.setId(reunion.getId());

        // Mission
        if (reunion.getMission() != null) {

            response.setMissionId(
                    reunion.getMission().getId());

            response.setMissionNumero(
                    reunion.getMission().getNumero());

            response.setMissionIntitule(
                    reunion.getMission().getIntitule());
        }

        // Reunion
        response.setType(
                reunion.getType());

        response.setDateReunion(
                reunion.getDateReunion());

        response.setHeureDebut(
                reunion.getHeureDebut());

        response.setHeureFin(
                reunion.getHeureFin());

        response.setLieu(
                reunion.getLieu());

        response.setObservations(
                reunion.getObservations());

        // Points abordés
        response.setPointsDaiIntroduction(
                reunion.getPointsDaiIntroduction());

        response.setPointsDaiPresentationMission(
                reunion.getPointsDaiPresentationMission());

        response.setPointsDaiMethodologie(
                reunion.getPointsDaiMethodologie());

        response.setPointsInterlocuteursIntroduction(
                reunion.getPointsInterlocuteursIntroduction());

        response.setPointsInterlocuteursProcessus(
                reunion.getPointsInterlocuteursProcessus());

        response.setPointsInterlocuteursOrganisation(
                reunion.getPointsInterlocuteursOrganisation());

        // Participants
        List<ReunionPersonne> participants =
                reunionPersonneRepository
                        .findByReunionId(reunion.getId());

        List<ReunionPersonneResponse> participantResponses =
                new ArrayList<>();

        for (ReunionPersonne participant : participants) {

            ReunionPersonneResponse participantResponse =
                    new ReunionPersonneResponse();

            participantResponse.setId(
                    participant.getId());

            participantResponse.setRole(
                    participant.getRole());

            participantResponse.setTypeParticipant(
                    participant.getTypeParticipant());

            Personne personne =
                    participant.getPersonne();

            if (personne != null) {

                participantResponse.setPersonneId(
                        personne.getId());

                participantResponse.setNom(
                        personne.getNom());

                participantResponse.setPrenom(
                        personne.getPrenom());

                participantResponse.setMatricule(
                        personne.getMatricule());

                participantResponse.setFonction(
                        personne.getFonction());

                participantResponse.setService(
                        personne.getServices());
            }

            participantResponses.add(
                    participantResponse);
        }

        response.setParticipants(
                participantResponses);

        return response;
    }
}