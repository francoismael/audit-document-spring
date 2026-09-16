package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.InterviewQuestionResponse;
import com.audit.audit_document.application.dto.InterviewResponse;
import com.audit.audit_document.application.usecases.GetAllInterviewsUseCase;
import com.audit.audit_document.domain.entity.Interview;
import com.audit.audit_document.domain.entity.InterviewQuestion;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.InterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllInterviewsService implements GetAllInterviewsUseCase {

    private final InterviewRepository interviewRepository;

    public GetAllInterviewsService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewResponse> execute() {

        List<Interview> interviews = interviewRepository.findAll();

        List<InterviewResponse> responses = new ArrayList<>();

        for (Interview interview : interviews) {

            InterviewResponse response = new InterviewResponse();

            // Interview
            response.setId(interview.getId());
            response.setReference(interview.getReference());
            response.setDateInterview(interview.getDateInterview());
            response.setFonction(interview.getFonction());
            response.setAnciennete(interview.getAnciennete());

            // Mission
            if (interview.getMission() != null) {
                response.setMissionId(interview.getMission().getId());
                response.setMissionNumero(
                        interview.getMission().getNumero()
                );
                response.setMissionIntitule(
                        interview.getMission().getIntitule()
                );
            }

            // Interviewed person
            setInterviewedPerson(
                    response,
                    interview.getPersonneInterviewee()
            );

            // Writer
            setWriter(
                    response,
                    interview.getRedigeParPersonne()
            );

            // Supervisor
            setSupervisor(
                    response,
                    interview.getSuperviseParPersonne()
            );

            // Validator
            setValidator(
                    response,
                    interview.getValideParPersonne()
            );

            // Questions
            List<InterviewQuestionResponse> questionResponses =
                    new ArrayList<>();

            for (InterviewQuestion question : interview.getQuestions()) {

                InterviewQuestionResponse questionResponse =
                        new InterviewQuestionResponse();

                questionResponse.setId(question.getId());
                questionResponse.setNumero(question.getNumero());
                questionResponse.setQuestion(question.getQuestion());
                questionResponse.setReponse(question.getReponse());

                questionResponses.add(questionResponse);
            }

            response.setQuestions(questionResponses);

            responses.add(response);
        }

        return responses;
    }

    private void setInterviewedPerson(
            InterviewResponse response,
            Personne personne) {

        if (personne == null) {
            return;
        }

        response.setPersonneIntervieweeId(personne.getId());
        response.setPersonneIntervieweeNom(personne.getNom());
        response.setPersonneIntervieweePrenom(personne.getPrenom());
    }

    private void setWriter(
            InterviewResponse response,
            Personne personne) {

        if (personne == null) {
            return;
        }

        response.setRedigeParPersonneId(personne.getId());
        response.setRedigeParPersonneNom(personne.getNom());
        response.setRedigeParPersonnePrenom(personne.getPrenom());
    }

    private void setSupervisor(
            InterviewResponse response,
            Personne personne) {

        if (personne == null) {
            return;
        }

        response.setSuperviseParPersonneId(personne.getId());
        response.setSuperviseParPersonneNom(personne.getNom());
        response.setSuperviseParPersonnePrenom(personne.getPrenom());
    }

    private void setValidator(
            InterviewResponse response,
            Personne personne) {

        if (personne == null) {
            return;
        }

        response.setValideParPersonneId(personne.getId());
        response.setValideParPersonneNom(personne.getNom());
        response.setValideParPersonnePrenom(personne.getPrenom());
    }
}