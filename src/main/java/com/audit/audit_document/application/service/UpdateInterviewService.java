package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateInterviewRequest;
import com.audit.audit_document.application.dto.InterviewQuestionRequest;
import com.audit.audit_document.application.dto.InterviewQuestionResponse;
import com.audit.audit_document.application.dto.InterviewResponse;
import com.audit.audit_document.application.usecases.UpdateInterviewUseCase;
import com.audit.audit_document.domain.entity.Interview;
import com.audit.audit_document.domain.entity.InterviewQuestion;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.InterviewQuestionRepository;
import com.audit.audit_document.domain.repository.InterviewRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateInterviewService implements UpdateInterviewUseCase {

    private final InterviewRepository interviewRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    public UpdateInterviewService(
            InterviewRepository interviewRepository,
            InterviewQuestionRepository interviewQuestionRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository) {

        this.interviewRepository = interviewRepository;
        this.interviewQuestionRepository = interviewQuestionRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
    }

    @Override
    @Transactional
    public InterviewResponse execute(Long id, CreateInterviewRequest request) {

        // Find the existing interview
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview introuvable"));

        // Find the mission
        Mission mission = missionRepository.findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException("Mission introuvable"));

        // Find the interviewed person
        Personne personneInterviewee =
                personneRepository.findById(
                        request.getPersonneIntervieweeId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Personne interviewée introuvable"));

        // Find the writer
        Personne redigeParPersonne = null;

        if (request.getRedigeParPersonneId() != null) {
            redigeParPersonne =
                    personneRepository.findById(
                            request.getRedigeParPersonneId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Personne qui a rédigé l'interview introuvable"));
        }

        // Find the supervisor
        Personne superviseParPersonne = null;

        if (request.getSuperviseParPersonneId() != null) {
            superviseParPersonne =
                    personneRepository.findById(
                            request.getSuperviseParPersonneId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Superviseur introuvable"));
        }

        // Find the validator
        Personne valideParPersonne = null;

        if (request.getValideParPersonneId() != null) {
            valideParPersonne =
                    personneRepository.findById(
                            request.getValideParPersonneId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Validateur introuvable"));
        }

        // Update interview information
        interview.setMission(mission);
        interview.setPersonneInterviewee(personneInterviewee);
        interview.setDateInterview(request.getDateInterview());
        interview.setFonction(request.getFonction());
        interview.setAnciennete(request.getAnciennete());

        interview.setRedigeParPersonne(redigeParPersonne);
        interview.setSuperviseParPersonne(superviseParPersonne);
        interview.setValideParPersonne(valideParPersonne);

        // Delete existing questions from the database
        interviewQuestionRepository.deleteByInterviewId(id);

        // Force the DELETE to be executed before inserting new questions
        interviewQuestionRepository.flush();

        // Clear questions from the entity
        interview.getQuestions().clear();

        // Add new questions
        if (request.getQuestions() != null) {

            for (InterviewQuestionRequest questionRequest
                    : request.getQuestions()) {

                InterviewQuestion question = new InterviewQuestion();

                question.setNumero(questionRequest.getNumero());
                question.setQuestion(questionRequest.getQuestion());
                question.setReponse(questionRequest.getReponse());

                interview.addQuestion(question);
            }
        }

        // Save updated interview
        Interview savedInterview = interviewRepository.save(interview);

        // Convert entity to response DTO
        return toResponse(savedInterview);
    }

    private InterviewResponse toResponse(Interview interview) {

        InterviewResponse response = new InterviewResponse();

        response.setId(interview.getId());

        // Mission
        if (interview.getMission() != null) {

            response.setMissionId(
                    interview.getMission().getId());

            response.setMissionNumero(
                    interview.getMission().getNumero());

            response.setMissionIntitule(
                    interview.getMission().getIntitule());
        }

        // Interviewed person
        if (interview.getPersonneInterviewee() != null) {

            response.setPersonneIntervieweeId(
                    interview.getPersonneInterviewee().getId());

            response.setPersonneIntervieweeNom(
                    interview.getPersonneInterviewee().getNom());

            response.setPersonneIntervieweePrenom(
                    interview.getPersonneInterviewee().getPrenom());
        }

        response.setReference(interview.getReference());
        response.setDateInterview(interview.getDateInterview());
        response.setFonction(interview.getFonction());
        response.setAnciennete(interview.getAnciennete());

        // Writer
        if (interview.getRedigeParPersonne() != null) {

            response.setRedigeParPersonneId(
                    interview.getRedigeParPersonne().getId());

            response.setRedigeParPersonneNom(
                    interview.getRedigeParPersonne().getNom());

            response.setRedigeParPersonnePrenom(
                    interview.getRedigeParPersonne().getPrenom());
        }

        // Supervisor
        if (interview.getSuperviseParPersonne() != null) {

            response.setSuperviseParPersonneId(
                    interview.getSuperviseParPersonne().getId());

            response.setSuperviseParPersonneNom(
                    interview.getSuperviseParPersonne().getNom());

            response.setSuperviseParPersonnePrenom(
                    interview.getSuperviseParPersonne().getPrenom());
        }

        // Validator
        if (interview.getValideParPersonne() != null) {

            response.setValideParPersonneId(
                    interview.getValideParPersonne().getId());

            response.setValideParPersonneNom(
                    interview.getValideParPersonne().getNom());

            response.setValideParPersonnePrenom(
                    interview.getValideParPersonne().getPrenom());
        }

        // Questions
        List<InterviewQuestionResponse> questions =
                new ArrayList<>();

        if (interview.getQuestions() != null) {

            for (InterviewQuestion question :
                    interview.getQuestions()) {

                InterviewQuestionResponse questionResponse =
                        new InterviewQuestionResponse();

                questionResponse.setId(question.getId());
                questionResponse.setNumero(question.getNumero());
                questionResponse.setQuestion(question.getQuestion());
                questionResponse.setReponse(question.getReponse());

                questions.add(questionResponse);
            }
        }

        response.setQuestions(questions);

        return response;
    }
}