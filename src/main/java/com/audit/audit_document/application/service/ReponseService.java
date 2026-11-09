package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
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

    private final MissionAccess missionAccess;

    public ReponseService(
            ReponseRepository reponseRepository,
            RecommandationRepository recommandationRepository,
            MissionAccess missionAccess) {

        this.reponseRepository = reponseRepository;
        this.recommandationRepository =
                recommandationRepository;
        this.missionAccess = missionAccess;
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public ReponseResponse create(
            CreateReponseRequest request) {

        validateRequest(request);

        Recommandation recommandation =
                findRecommandation(
                        request.getRecommandationId()
                );

        /*
         * A response can only be created
         * for a retained recommendation.
         */
        if (!Boolean.TRUE.equals(
                recommandation.getRetenue())) {

            throw new IllegalArgumentException(
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
                                new ResourceNotFoundException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        missionAccess.checkAccess(missionIdOf(reponse));

        return toResponse(reponse);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ReponseResponse> getAll() {

        return missionAccess
                .keepVisible(reponseRepository.findAll(), ReponseService::missionIdOf)
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
         * Check that the recommendation exists.
         */
        findRecommandation(
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
                                new ResourceNotFoundException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        missionAccess.checkAccess(missionIdOf(reponse));

        Recommandation nouvelleRecommandation =
                findRecommandation(
                        request.getRecommandationId()
                );

        /*
         * The response must always be linked
         * to a retained recommendation.
         */
        if (!Boolean.TRUE.equals(
                nouvelleRecommandation.getRetenue())) {

            throw new IllegalArgumentException(
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
                                new ResourceNotFoundException(
                                        "Réponse introuvable avec l'id : "
                                                + id
                                )
                        );

        Recommandation recommandation =
                reponse.getRecommandation();

        reponseRepository.deleteById(
                reponse.getId()
        );

        /*
         * The maintenance decision needs a response:
         * without any response left, it is cleared.
         */
        if (reponseRepository
                .findByRecommandationId(recommandation.getId())
                .isEmpty()) {

            recommandation.setMaintenue(null);
            recommandationRepository.save(recommandation);
        }
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateRequest(
            CreateReponseRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "La requête ne peut pas être null."
            );
        }

        if (request.getRecommandationId() == null) {

            throw new IllegalArgumentException(
                    "La recommandation est obligatoire."
            );
        }

        if (request.getDescriptions() == null
                || request.getDescriptions()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "La description de la réponse est obligatoire."
            );
        }
    }

    // =========================================================
    // FIND RECOMMANDATION
    // =========================================================

    // The recommendation must belong to a mission the connected account sees.
    private Recommandation findRecommandation(
            Long recommandationId) {

        Recommandation recommandation = recommandationRepository
                .findById(recommandationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recommandation introuvable avec l'id : "
                                        + recommandationId
                        )
                );

        missionAccess.checkAccess(ConstatService.missionIdOf(recommandation.getConstat()));

        return recommandation;
    }

    private static Long missionIdOf(Reponse reponse) {

        return ConstatService.missionIdOf(reponse.getRecommandation().getConstat());
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