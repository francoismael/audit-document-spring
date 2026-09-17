package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateReunionRequest;
import com.audit.audit_document.application.dto.ReunionPersonneRequest;
import com.audit.audit_document.application.dto.ReunionPersonneResponse;
import com.audit.audit_document.application.dto.ReunionResponse;
import com.audit.audit_document.application.usecases.CreateReunionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.entity.ReunionPersonne;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import com.audit.audit_document.domain.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateReunionService implements CreateReunionUseCase {

    private final ReunionRepository reunionRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    public CreateReunionService(
            ReunionRepository reunionRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository) {

        this.reunionRepository = reunionRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
    }

    @Override
    @Transactional
    public ReunionResponse execute(CreateReunionRequest request) {

        Mission mission = missionRepository
                .findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException("Mission introuvable"));

        Reunion reunion = new Reunion();

        reunion.setMission(mission);
        reunion.setType(request.getType());
        reunion.setDateReunion(request.getDateReunion());
        reunion.setHeureDebut(request.getHeureDebut());
        reunion.setHeureFin(request.getHeureFin());
        reunion.setLieu(request.getLieu());
        reunion.setObservations(request.getObservations());

        reunion.setPointsDaiIntroduction(
                request.getPointsDaiIntroduction());

        reunion.setPointsDaiPresentationMission(
                request.getPointsDaiPresentationMission());

        reunion.setPointsDaiMethodologie(
                request.getPointsDaiMethodologie());

        reunion.setPointsInterlocuteursIntroduction(
                request.getPointsInterlocuteursIntroduction());

        reunion.setPointsInterlocuteursProcessus(
                request.getPointsInterlocuteursProcessus());

        reunion.setPointsInterlocuteursOrganisation(
                request.getPointsInterlocuteursOrganisation());

        if (request.getParticipants() != null) {

            for (ReunionPersonneRequest participantRequest
                    : request.getParticipants()) {

                Personne personne = personneRepository
                        .findById(participantRequest.getPersonneId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Personne introuvable"));

                ReunionPersonne participant =
                        new ReunionPersonne();

                participant.setPersonne(personne);
                participant.setRole(
                        participantRequest.getRole());

                participant.setTypeParticipant(
                        participantRequest.getTypeParticipant());

                reunion.addParticipant(participant);
            }
        }

        Reunion savedReunion =
                reunionRepository.save(reunion);

        return toResponse(savedReunion);
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

        if (reunion.getParticipants() != null) {

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
        }

        response.setParticipants(participants);

        return response;
    }
}