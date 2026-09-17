package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateReunionRequest;
import com.audit.audit_document.application.dto.ReunionPersonneRequest;
import com.audit.audit_document.application.dto.ReunionPersonneResponse;
import com.audit.audit_document.application.dto.ReunionResponse;
import com.audit.audit_document.application.usecases.UpdateReunionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.entity.ReunionPersonne;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import com.audit.audit_document.domain.repository.ReunionPersonneRepository;
import com.audit.audit_document.domain.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateReunionService
        implements UpdateReunionUseCase {

    private final ReunionRepository reunionRepository;
    private final ReunionPersonneRepository reunionPersonneRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    public UpdateReunionService(
            ReunionRepository reunionRepository,
            ReunionPersonneRepository reunionPersonneRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository) {

        this.reunionRepository = reunionRepository;
        this.reunionPersonneRepository =
                reunionPersonneRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
    }

    @Override
    @Transactional
    public ReunionResponse execute(
            Long id,
            CreateReunionRequest request) {

        Reunion reunion = reunionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Réunion introuvable"));

        Mission mission = missionRepository
                .findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Mission introuvable"));

        reunion.setMission(mission);
        reunion.setType(request.getType());
        reunion.setDateReunion(
                request.getDateReunion());
        reunion.setHeureDebut(
                request.getHeureDebut());
        reunion.setHeureFin(
                request.getHeureFin());
        reunion.setLieu(request.getLieu());
        reunion.setObservations(
                request.getObservations());

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

        // Remove existing participants before recreating them.
        reunionPersonneRepository.deleteByReunionId(id);
        reunionPersonneRepository.flush();

        reunion.getParticipants().clear();

        if (request.getParticipants() != null) {

            for (ReunionPersonneRequest participantRequest
                    : request.getParticipants()) {

                Personne personne =
                        personneRepository
                                .findById(
                                        participantRequest
                                                .getPersonneId())
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Personne introuvable"));

                ReunionPersonne participant =
                        new ReunionPersonne();

                participant.setPersonne(personne);

                participant.setRole(
                        participantRequest.getRole());

                participant.setTypeParticipant(
                        participantRequest
                                .getTypeParticipant());

                reunion.addParticipant(participant);
            }
        }

        Reunion savedReunion =
                reunionRepository.save(reunion);

        return toResponse(savedReunion);
    }

    private ReunionResponse toResponse(
            Reunion reunion) {

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