package com.audit.audit_document.application.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.CreateReponseRequest;
import com.audit.audit_document.application.dto.ReponseResponse;
import com.audit.audit_document.application.usecases.CreateReponseUseCase;
import com.audit.audit_document.application.usecases.DeleteReponseUseCase;
import com.audit.audit_document.application.usecases.GetAllReponsesUseCase;
import com.audit.audit_document.application.usecases.GetReponseByIdUseCase;
import com.audit.audit_document.application.usecases.GetReponsesByRecommandationUseCase;
import com.audit.audit_document.application.usecases.UpdateReponseUseCase;
import com.audit.audit_document.domain.entity.Recommandation;
import com.audit.audit_document.domain.entity.Reponse;
import com.audit.audit_document.domain.repository.RecommandationRepository;
import com.audit.audit_document.domain.repository.ReponseRepository;

@Service
@Transactional
public class ReponseService implements
        CreateReponseUseCase,
        GetReponseByIdUseCase,
        GetAllReponsesUseCase,
        GetReponsesByRecommandationUseCase,
        UpdateReponseUseCase,
        DeleteReponseUseCase {

    private final ReponseRepository reponseRepository;
    private final RecommandationRepository recommandationRepository;

    public ReponseService(
            ReponseRepository reponseRepository,
            RecommandationRepository recommandationRepository) {

        this.reponseRepository = reponseRepository;
        this.recommandationRepository =
                recommandationRepository;
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public ReponseResponse create(
            CreateReponseRequest request) {

        validateRequest(request);

        Recommandation recommandation =
                trouverRecommandation(
                        request.getRecommandationId()
                );

        /*
         * Une réponse ne peut être créée
         * que pour une recommandation retenue.
         */
        if (!Boolean.TRUE.equals(
                recommandation.getRetenue())) {

            throw new RuntimeException(
                    "Impossible de créer une réponse : "
                    + "la recommandation n'est pas retenue."
            );
        }

        Reponse reponse =
                new Reponse();

        reponse.setRecommandation(
                recommandation
        );

        reponse.setDescriptions(
                request.getDescriptions()
        );

        reponse.setDateReponse(
                request.getDateReponse()
        );

        Reponse saved =
                reponseRepository.save(
                        reponse
                );

        return toResponse(saved);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public ReponseResponse getById(
            Long id) {

        Reponse reponse =
                reponseRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        return toResponse(reponse);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ReponseResponse> getAll() {

        return reponseRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // GET BY RECOMMANDATION
    // =========================================================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ReponseResponse> getByRecommandationId(
            Long recommandationId) {

        /*
         * Vérifier que la recommandation existe.
         */
        trouverRecommandation(
                recommandationId
        );

        return reponseRepository
                .findByRecommandationId(
                        recommandationId
                )
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public ReponseResponse update(
            Long id,
            CreateReponseRequest request) {

        validateRequest(request);

        Reponse reponse =
                reponseRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        Recommandation nouvelleRecommandation =
                trouverRecommandation(
                        request.getRecommandationId()
                );

        /*
         * La réponse doit toujours être liée
         * à une recommandation retenue.
         */
        if (!Boolean.TRUE.equals(
                nouvelleRecommandation.getRetenue())) {

            throw new RuntimeException(
                    "Impossible de modifier la réponse : "
                    + "la recommandation n'est pas retenue."
            );
        }

        reponse.setRecommandation(
                nouvelleRecommandation
        );

        reponse.setDescriptions(
                request.getDescriptions()
        );

        reponse.setDateReponse(
                request.getDateReponse()
        );

        Reponse updated =
                reponseRepository.save(
                        reponse
                );

        return toResponse(updated);
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Override
    public void delete(Long id) {

        Reponse reponse =
                reponseRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        reponseRepository.deleteById(
                reponse.getId()
        );
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateRequest(
            CreateReponseRequest request) {

        if (request == null) {

            throw new RuntimeException(
                    "La requête ne peut pas être null."
            );
        }

        if (request.getRecommandationId() == null) {

            throw new RuntimeException(
                    "La recommandation est obligatoire."
            );
        }

        if (request.getDescriptions() == null
                || request.getDescriptions()
                        .trim()
                        .isEmpty()) {

            throw new RuntimeException(
                    "La description de la réponse est obligatoire."
            );
        }
    }

    // =========================================================
    // FIND RECOMMANDATION
    // =========================================================

    private Recommandation trouverRecommandation(
            Long recommandationId) {

        return recommandationRepository
                .findById(recommandationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recommandation introuvable avec l'id : "
                                        + recommandationId
                        )
                );
    }

    // =========================================================
    // MAPPING RESPONSE
    // =========================================================

    private ReponseResponse toResponse(
            Reponse reponse) {

        ReponseResponse response =
                new ReponseResponse();

        response.setId(
                reponse.getId()
        );

        Recommandation recommandation =
                reponse.getRecommandation();

        if (recommandation != null) {

            response.setRecommandationId(
                    recommandation.getId()
            );

            response.setRecommandationDescription(
                    recommandation.getDescription()
            );
        }

        response.setDescriptions(
                reponse.getDescriptions()
        );

        response.setDateReponse(
                reponse.getDateReponse()
        );

        return response;
    }
}