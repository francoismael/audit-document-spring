package com.audit.audit_document.application.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.DecisionMaintienRecommandationRequest;
import com.audit.audit_document.application.dto.RecommandationResponse;
import com.audit.audit_document.application.usecases.DeciderMaintienRecommandationUseCase;
import com.audit.audit_document.domain.entity.Recommandation;
import com.audit.audit_document.domain.entity.Reponse;
import com.audit.audit_document.domain.repository.RecommandationRepository;
import com.audit.audit_document.domain.repository.ReponseRepository;

@Service
@Transactional
public class RecommandationDecisionService
        implements DeciderMaintienRecommandationUseCase {

    private final RecommandationRepository recommandationRepository;
    private final ReponseRepository reponseRepository;

    public RecommandationDecisionService(
            RecommandationRepository recommandationRepository,
            ReponseRepository reponseRepository) {

        this.recommandationRepository =
                recommandationRepository;

        this.reponseRepository =
                reponseRepository;
    }


    @Override
    public RecommandationResponse decider(
            Long recommandationId,
            DecisionMaintienRecommandationRequest request) {

        if (request == null) {

            throw new RuntimeException(
                    "La requête de décision est obligatoire."
            );
        }

        if (request.getMaintenue() == null) {

            throw new RuntimeException(
                    "La valeur de maintien est obligatoire."
            );
        }

        Recommandation recommandation =
                recommandationRepository
                        .findById(recommandationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recommandation introuvable avec l'id : "
                                                + recommandationId
                                )
                        );


        if (!Boolean.TRUE.equals(
                recommandation.getRetenue())) {

            throw new RuntimeException(
                    "Cette recommandation n'est pas retenue."
            );
        }



        List<Reponse> reponses =
                reponseRepository
                        .findByRecommandationId(
                                recommandationId
                        );

        if (reponses == null
                || reponses.isEmpty()) {

            throw new RuntimeException(
                    "Impossible de décider le maintien : "
                    + "aucune réponse n'a été enregistrée "
                    + "pour cette recommandation."
            );
        }



        recommandation.setMaintenue(
                request.getMaintenue()
        );

        Recommandation updated =
                recommandationRepository.save(
                        recommandation
                );

        return toResponse(updated);
    }


    private RecommandationResponse toResponse(
            Recommandation recommandation) {

        RecommandationResponse response =
                new RecommandationResponse();

        response.setId(
                recommandation.getId()
        );

        response.setDescription(
                recommandation.getDescription()
        );

        response.setRetenue(
                recommandation.getRetenue()
        );

        response.setMaintenue(
                recommandation.getMaintenue()
        );

        return response;
    }
}