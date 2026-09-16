package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateInterviewRequest;
import com.audit.audit_document.application.dto.InterviewQuestionRequest;
import com.audit.audit_document.application.dto.InterviewQuestionResponse;
import com.audit.audit_document.application.dto.InterviewResponse;
import com.audit.audit_document.application.usecases.CreateInterviewUseCase;
import com.audit.audit_document.domain.entity.Interview;
import com.audit.audit_document.domain.entity.InterviewQuestion;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.InterviewRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateInterviewService implements CreateInterviewUseCase {

    private final InterviewRepository interviewRepository;
    private final MissionRepository missionRepository;
    private final PersonneRepository personneRepository;

    public CreateInterviewService(
            InterviewRepository interviewRepository,
            MissionRepository missionRepository,
            PersonneRepository personneRepository) {

        this.interviewRepository = interviewRepository;
        this.missionRepository = missionRepository;
        this.personneRepository = personneRepository;
    }

    @Override
    @Transactional
    public InterviewResponse execute(CreateInterviewRequest request) {

        // Find the mission
        Mission mission = missionRepository.findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException("Mission introuvable"));

        // Find the interviewed person
        Personne personneInterviewee =
                personneRepository.findById(request.getPersonneIntervieweeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Personne interviewée introuvable"));

        // Find the person who wrote the interview
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

        // Create the interview
        Interview interview = new Interview();

        interview.setMission(mission);
        interview.setPersonneInterviewee(personneInterviewee);

        // Generate the interview reference automatically
        long number = interviewRepository.getNextReferenceNumber();
        String reference = String.format("FI-%03d", number);

        interview.setReference(reference);

        interview.setDateInterview(request.getDateInterview());
        interview.setFonction(request.getFonction());
        interview.setAnciennete(request.getAnciennete());

        interview.setRedigeParPersonne(redigeParPersonne);
        interview.setSuperviseParPersonne(superviseParPersonne);
        interview.setValideParPersonne(valideParPersonne);

        // Add questions
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

        // Save interview and questions
        System.out.println("========== CREATE INTERVIEW ==========");
System.out.println("ID AVANT SAVE       = " + interview.getId());
System.out.println("REFERENCE           = " + interview.getReference());
System.out.println("MISSION ID          = " + interview.getMission().getId());
System.out.println("PERSONNE INTERVIEW  = " + interview.getPersonneInterviewee().getId());
System.out.println("======================================");
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
                    interview.getMission().getId()
            );

            response.setMissionNumero(
                    interview.getMission().getNumero()
            );

            response.setMissionIntitule(
                    interview.getMission().getIntitule()
            );
        }

        // Interviewed person
        if (interview.getPersonneInterviewee() != null) {

            response.setPersonneIntervieweeId(
                    interview.getPersonneInterviewee().getId()
            );

            response.setPersonneIntervieweeNom(
                    interview.getPersonneInterviewee().getNom()
            );

            response.setPersonneIntervieweePrenom(
                    interview.getPersonneInterviewee().getPrenom()
            );
        }

        // Interview information
        response.setReference(interview.getReference());
        response.setDateInterview(interview.getDateInterview());
        response.setFonction(interview.getFonction());
        response.setAnciennete(interview.getAnciennete());

        // Writer
        if (interview.getRedigeParPersonne() != null) {

            response.setRedigeParPersonneId(
                    interview.getRedigeParPersonne().getId()
            );

            response.setRedigeParPersonneNom(
                    interview.getRedigeParPersonne().getNom()
            );

            response.setRedigeParPersonnePrenom(
                    interview.getRedigeParPersonne().getPrenom()
            );
        }

        // Supervisor
        if (interview.getSuperviseParPersonne() != null) {

            response.setSuperviseParPersonneId(
                    interview.getSuperviseParPersonne().getId()
            );

            response.setSuperviseParPersonneNom(
                    interview.getSuperviseParPersonne().getNom()
            );

            response.setSuperviseParPersonnePrenom(
                    interview.getSuperviseParPersonne().getPrenom()
            );
        }

        // Validator
        if (interview.getValideParPersonne() != null) {

            response.setValideParPersonneId(
                    interview.getValideParPersonne().getId()
            );

            response.setValideParPersonneNom(
                    interview.getValideParPersonne().getNom()
            );

            response.setValideParPersonnePrenom(
                    interview.getValideParPersonne().getPrenom()
            );
        }

        // Questions
        List<InterviewQuestionResponse> questions = new ArrayList<>();

        if (interview.getQuestions() != null) {

            for (InterviewQuestion question : interview.getQuestions()) {

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