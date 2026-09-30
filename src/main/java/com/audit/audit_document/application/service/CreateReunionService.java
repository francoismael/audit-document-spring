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

        validateType(request.getType());

        Mission mission = missionRepository
                .findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Mission introuvable"));

        boolean reunionExiste = reunionRepository
                .findAll()
                .stream()
                .anyMatch(reunion ->
                        reunion.getMission() != null
                                && reunion.getMission().getId()
                                .equals(mission.getId())
                                && reunion.getType() != null
                                && reunion.getType()
                                .equals(request.getType()));

        if (reunionExiste) {
            throw new RuntimeException(
                    "Une réunion de type "
                            + request.getType()
                            + " existe déjà pour cette mission");
        }

        Reunion reunion = new Reunion();

        reunion.setMission(mission);
        reunion.setType(request.getType());
        reunion.setDateReunion(request.getDateReunion());
        reunion.setHeureDebut(request.getHeureDebut());
        reunion.setHeureFin(request.getHeureFin());
        reunion.setHeureLevee(request.getHeureLevee());
        reunion.setLieu(request.getLieu());

        /* 
           REUNION D'OUVERTURE
            */

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

        /* 
           REUNION DE CLOTURE
            */

        reunion.setRemerciements(
                request.getRemerciements());

        reunion.setRappelPerimetre(
                request.getRappelPerimetre());

        reunion.setSyntheseConstats(
                request.getSyntheseConstats());

        reunion.setPointsFortsIdentifies(
                request.getPointsFortsIdentifies());

        reunion.setResumeResultatsVerification(
                request.getResumeResultatsVerification());

        reunion.setResumeRecommandationsPlansActions(
                request.getResumeRecommandationsPlansActions());

        reunion.setObservationsCommentaires(
                request.getObservationsCommentaires());

        /* 
           PARTICIPANTS
            */

        if (request.getParticipants() != null) {

            for (ReunionPersonneRequest participantRequest
                    : request.getParticipants()) {

                Personne personne = personneRepository
                        .findById(
                                participantRequest.getPersonneId())
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

    private void validateType(String type) {

        if (!"OUVERTURE".equals(type)
                && !"CLOTURE".equals(type)) {

            throw new RuntimeException(
                    "Le type de réunion doit être OUVERTURE ou CLOTURE");
        }
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

        response.setHeureLevee(
                reunion.getHeureLevee());

        response.setLieu(
                reunion.getLieu());

        /* 
           REUNION D'OUVERTURE
            */

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

        /* 
           REUNION DE CLOTURE
            */

        response.setRemerciements(
                reunion.getRemerciements());

        response.setRappelPerimetre(
                reunion.getRappelPerimetre());

        response.setSyntheseConstats(
                reunion.getSyntheseConstats());

        response.setPointsFortsIdentifies(
                reunion.getPointsFortsIdentifies());

        response.setResumeResultatsVerification(
                reunion.getResumeResultatsVerification());

        response.setResumeRecommandationsPlansActions(
                reunion.getResumeRecommandationsPlansActions());

        response.setObservationsCommentaires(
                reunion.getObservationsCommentaires());

        /* 
           PARTICIPANTS
            */

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