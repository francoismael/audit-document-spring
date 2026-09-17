package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ReunionPersonneResponse;
import com.audit.audit_document.application.dto.ReunionResponse;
import com.audit.audit_document.application.usecases.GetReunionByIdUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.entity.ReunionPersonne;
import com.audit.audit_document.domain.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetReunionByIdService
        implements GetReunionByIdUseCase {

    private final ReunionRepository reunionRepository;

    public GetReunionByIdService(
            ReunionRepository reunionRepository) {

        this.reunionRepository = reunionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ReunionResponse execute(Long id) {

        Reunion reunion = reunionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Réunion introuvable"));

        return toResponse(reunion);
    }

    private ReunionResponse toResponse(Reunion reunion) {

        ReunionResponse response =
                new ReunionResponse();

        response.setId(reunion.getId());

        if (reunion.getMission() != null) {

            response.setMissionId(
                    reunion.getMission().getId());

            response.setMissionNumero(
                    reunion.getMission().getNumero());

            response.setMissionIntitule(
                    reunion.getMission().getIntitule());
        }

        response.setType(reunion.getType());
        response.setDateReunion(
                reunion.getDateReunion());
        response.setHeureDebut(
                reunion.getHeureDebut());
        response.setHeureFin(
                reunion.getHeureFin());
        response.setLieu(reunion.getLieu());
        response.setObservations(
                reunion.getObservations());

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

        List<ReunionPersonneResponse> participants =
                new ArrayList<>();

        for (ReunionPersonne participant
                : reunion.getParticipants()) {

            ReunionPersonneResponse participantResponse =
                    new ReunionPersonneResponse();

            participantResponse.setId(
                    participant.getId());

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

            participantResponse.setRole(
                    participant.getRole());

            participantResponse.setTypeParticipant(
                    participant.getTypeParticipant());

            participants.add(participantResponse);
        }

        response.setParticipants(participants);

        return response;
    }
}