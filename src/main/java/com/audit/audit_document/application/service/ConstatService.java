package com.audit.audit_document.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.CauseRequest;
import com.audit.audit_document.application.dto.CauseResponse;
import com.audit.audit_document.application.dto.ConsequenceRequest;
import com.audit.audit_document.application.dto.ConsequenceResponse;
import com.audit.audit_document.application.dto.ConstatResponse;
import com.audit.audit_document.application.dto.CreateConstatRequest;
import com.audit.audit_document.application.dto.RecommandationRequest;
import com.audit.audit_document.application.dto.RecommandationResponse;
import com.audit.audit_document.application.dto.RisqueRequest;
import com.audit.audit_document.application.dto.RisqueResponse;
import com.audit.audit_document.application.usecases.CreateConstatUseCase;
import com.audit.audit_document.application.usecases.DeleteConstatUseCase;
import com.audit.audit_document.application.usecases.GetAllConstatsUseCase;
import com.audit.audit_document.application.usecases.GetConstatByIdUseCase;
import com.audit.audit_document.application.usecases.GetConstatsByTestUseCase;
import com.audit.audit_document.application.usecases.UpdateConstatUseCase;
import com.audit.audit_document.domain.entity.Cause;
import com.audit.audit_document.domain.entity.Consequence;
import com.audit.audit_document.domain.entity.Constat;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Recommandation;
import com.audit.audit_document.domain.entity.Risque;
import com.audit.audit_document.domain.entity.Test;
import com.audit.audit_document.domain.repository.ConstatRepository;
import com.audit.audit_document.domain.repository.TestRepository;

@Service
@Transactional
public class ConstatService implements
        CreateConstatUseCase,
        GetConstatByIdUseCase,
        GetAllConstatsUseCase,
        GetConstatsByTestUseCase,
        UpdateConstatUseCase,
        DeleteConstatUseCase {

    private final ConstatRepository constatRepository;
    private final TestRepository testRepository;

    public ConstatService(
            ConstatRepository constatRepository,
            TestRepository testRepository) {

        this.constatRepository = constatRepository;
        this.testRepository = testRepository;
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public ConstatResponse create(
            CreateConstatRequest request) {

        validateRequest(request);

        Test test = trouverTest(
                request.getTestId()
        );

        /*
         * Une seule recommandation peut être retenue
         * pour un même constat.
         */
        validerRecommandationsRetenues(
                request.getRecommandations()
        );

        /*
         * Génération automatique de la référence.
         * Exemple : CST001, CST002, CST003...
         */
        Long number =
                constatRepository.getNextReference();

        String reference =
                String.format(
                        "CST%03d",
                        number
                );

        Constat constat =
                new Constat();

        constat.setTest(test);

        constat.setReference(
                reference
        );

        constat.setDescriptions(
                request.getDescriptions()
        );

        constat.setNiveauRisque(
                request.getNiveauRisque()
        );

        constat.setDirectionServiceConcerne(
                request.getDirectionServiceConcerne()
        );

        /*
         * Création des enfants.
         */
        ajouterCauses(
                constat,
                request.getCauses()
        );

        ajouterRisques(
                constat,
                request.getRisques()
        );

        ajouterConsequences(
                constat,
                request.getConsequences()
        );

        ajouterRecommandations(
                constat,
                request.getRecommandations()
        );

        Constat saved =
                constatRepository.save(
                        constat
                );

        return toResponse(saved);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Override
    public ConstatResponse getById(
            Long id) {

        Constat constat =
                constatRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Constat introuvable avec l'id : "
                                                + id
                                )
                        );

        return toResponse(
                constat
        );
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Override
    public List<ConstatResponse> getAll() {

        return constatRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // GET BY TEST
    // =========================================================

    @Override
    public List<ConstatResponse> getByTestId(
            Long testId) {

        trouverTest(testId);

        return constatRepository
                .findByTestId(testId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public ConstatResponse update(
            Long id,
            CreateConstatRequest request) {

        validateRequest(request);

        /*
         * Vérifie qu'il n'y a pas plusieurs
         * recommandations retenues.
         */
        validerRecommandationsRetenues(
                request.getRecommandations()
        );

        Constat constat =
                constatRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Constat introuvable avec l'id : "
                                                + id
                                )
                        );

        Test test =
                trouverTest(
                        request.getTestId()
                );

        /*
         * Mise à jour des informations
         * du constat.
         */
        constat.setTest(test);

        constat.setDescriptions(
                request.getDescriptions()
        );

        constat.setNiveauRisque(
                request.getNiveauRisque()
        );

        constat.setDirectionServiceConcerne(
                request.getDirectionServiceConcerne()
        );

        /*
         * Mise à jour des enfants.
         */
        mettreAJourCauses(
                constat,
                request.getCauses()
        );

        mettreAJourRisques(
                constat,
                request.getRisques()
        );

        mettreAJourConsequences(
                constat,
                request.getConsequences()
        );

        mettreAJourRecommandations(
                constat,
                request.getRecommandations()
        );

        Constat updated =
                constatRepository.save(
                        constat
                );

        return toResponse(
                updated
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Override
    public void delete(
            Long id) {

        Constat constat =
                constatRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Constat introuvable avec l'id : "
                                                + id
                                )
                        );

        constatRepository.deleteById(
                constat.getId()
        );
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateRequest(
            CreateConstatRequest request) {

        if (request == null) {

            throw new RuntimeException(
                    "La requête ne peut pas être null."
            );
        }

        if (request.getTestId() == null) {

            throw new RuntimeException(
                    "Le testId est obligatoire."
            );
        }

        if (request.getDescriptions() == null
                || request.getDescriptions()
                        .trim()
                        .isEmpty()) {

            throw new RuntimeException(
                    "La description du constat est obligatoire."
            );
        }

        if (request.getCauses() == null) {

            request.setCauses(
                    new ArrayList<>()
            );
        }

        if (request.getRisques() == null) {

            request.setRisques(
                    new ArrayList<>()
            );
        }

        if (request.getConsequences() == null) {

            request.setConsequences(
                    new ArrayList<>()
            );
        }

        if (request.getRecommandations() == null) {

            request.setRecommandations(
                    new ArrayList<>()
            );
        }
    }

    // =========================================================
    // VALIDATION DES RECOMMANDATIONS RETENUES
    // =========================================================

    private void validerRecommandationsRetenues(
            List<RecommandationRequest> requests) {

        int nombreRetenues = 0;

        for (RecommandationRequest request :
                requests) {

            if (request == null) {
                continue;
            }

            if (Boolean.TRUE.equals(
                    request.getRetenue())) {

                nombreRetenues++;
            }
        }

        if (nombreRetenues > 1) {

            throw new RuntimeException(
                    "Une seule recommandation peut être retenue pour un constat."
            );
        }
    }

    // =========================================================
    // TEST
    // =========================================================

    private Test trouverTest(
            Long testId) {

        return testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Test introuvable avec l'id : "
                                        + testId
                        )
                );
    }

    // =========================================================
    // CREATE CHILDREN
    // =========================================================

    private void ajouterCauses(
            Constat constat,
            List<CauseRequest> requests) {

        for (CauseRequest request :
                requests) {

            Cause cause =
                    new Cause();

            cause.setDescriptions(
                    request.getDescriptions()
            );

            constat.addCause(
                    cause
            );
        }
    }

    private void ajouterRisques(
            Constat constat,
            List<RisqueRequest> requests) {

        for (RisqueRequest request :
                requests) {

            Risque risque =
                    new Risque();

            risque.setDescriptions(
                    request.getDescriptions()
            );

            risque.setNiveau(
                    request.getNiveau()
            );

            constat.addRisque(
                    risque
            );
        }
    }

    private void ajouterConsequences(
            Constat constat,
            List<ConsequenceRequest> requests) {

        for (ConsequenceRequest request :
                requests) {

            Consequence consequence =
                    new Consequence();

            consequence.setDescriptions(
                    request.getDescriptions()
            );

            constat.addConsequence(
                    consequence
            );
        }
    }

    private void ajouterRecommandations(
            Constat constat,
            List<RecommandationRequest> requests) {

        for (RecommandationRequest request :
                requests) {

            if (request == null) {
                continue;
            }

            Recommandation recommandation =
                    new Recommandation();

            recommandation.setDescription(
                    request.getDescription()
            );

            /*
             * Si retenue est null,
             * on considère false.
             */
            Boolean retenue =
                    request.getRetenue() != null
                            ? request.getRetenue()
                            : false;

            recommandation.setRetenue(
                    retenue
            );

            /*
             * Une nouvelle recommandation
             * n'est pas encore maintenue.
             */
            recommandation.setMaintenue(
                    null
            );

            constat.addRecommandation(
                    recommandation
            );
        }
    }

    // =========================================================
    // UPDATE CHILDREN
    // =========================================================

    private void mettreAJourCauses(
            Constat constat,
            List<CauseRequest> requests) {

        Set<Long> idsRecus =
                new HashSet<>();

        for (CauseRequest request :
                requests) {

            if (request.getId() == null) {

                Cause cause =
                        new Cause();

                cause.setDescriptions(
                        request.getDescriptions()
                );

                constat.addCause(
                        cause
                );

            } else {

                Cause cause =
                        trouverCauseDuConstat(
                                constat,
                                request.getId()
                        );

                cause.setDescriptions(
                        request.getDescriptions()
                );

                idsRecus.add(
                        request.getId()
                );
            }
        }

        supprimerCausesAbsentes(
                constat,
                idsRecus
        );
    }

    private void mettreAJourRisques(
            Constat constat,
            List<RisqueRequest> requests) {

        Set<Long> idsRecus =
                new HashSet<>();

        for (RisqueRequest request :
                requests) {

            if (request.getId() == null) {

                Risque risque =
                        new Risque();

                risque.setDescriptions(
                        request.getDescriptions()
                );

                risque.setNiveau(
                        request.getNiveau()
                );

                constat.addRisque(
                        risque
                );

            } else {

                Risque risque =
                        trouverRisqueDuConstat(
                                constat,
                                request.getId()
                        );

                risque.setDescriptions(
                        request.getDescriptions()
                );

                risque.setNiveau(
                        request.getNiveau()
                );

                idsRecus.add(
                        request.getId()
                );
            }
        }

        supprimerRisquesAbsentes(
                constat,
                idsRecus
        );
    }

    private void mettreAJourConsequences(
            Constat constat,
            List<ConsequenceRequest> requests) {

        Set<Long> idsRecus =
                new HashSet<>();

        for (ConsequenceRequest request :
                requests) {

            if (request.getId() == null) {

                Consequence consequence =
                        new Consequence();

                consequence.setDescriptions(
                        request.getDescriptions()
                );

                constat.addConsequence(
                        consequence
                );

            } else {

                Consequence consequence =
                        trouverConsequenceDuConstat(
                                constat,
                                request.getId()
                        );

                consequence.setDescriptions(
                        request.getDescriptions()
                );

                idsRecus.add(
                        request.getId()
                );
            }
        }

        supprimerConsequencesAbsentes(
                constat,
                idsRecus
        );
    }

    private void mettreAJourRecommandations(
            Constat constat,
            List<RecommandationRequest> requests) {

        Set<Long> idsRecus =
                new HashSet<>();

        for (RecommandationRequest request :
                requests) {

            if (request == null) {
                continue;
            }

            /*
             * ==========================================
             * NOUVELLE RECOMMANDATION
             * ==========================================
             */
            if (request.getId() == null) {

                Recommandation recommandation =
                        new Recommandation();

                recommandation.setDescription(
                        request.getDescription()
                );

                Boolean retenue =
                        request.getRetenue() != null
                                ? request.getRetenue()
                                : false;

                recommandation.setRetenue(
                        retenue
                );

                /*
                 * Une nouvelle recommandation
                 * n'est pas encore maintenue.
                 */
                recommandation.setMaintenue(
                        null
                );

                constat.addRecommandation(
                        recommandation
                );

            }

            /*
             * ==========================================
             * RECOMMANDATION EXISTANTE
             * ==========================================
             */
            else {

                Recommandation recommandation =
                        trouverRecommandationDuConstat(
                                constat,
                                request.getId()
                        );

                recommandation.setDescription(
                        request.getDescription()
                );

                Boolean retenue =
                        request.getRetenue() != null
                                ? request.getRetenue()
                                : false;

                recommandation.setRetenue(
                        retenue
                );

                /*
                 * Si la recommandation n'est plus retenue,
                 * aucune décision de maintien ne doit rester.
                 */
                if (!retenue) {

                    recommandation.setMaintenue(
                            null
                    );
                }

                /*
                 * Si retenue = true,
                 * on conserve l'ancienne valeur de
                 * maintenue.
                 */
                idsRecus.add(
                        request.getId()
                );
            }
        }

        supprimerRecommandationsAbsentes(
                constat,
                idsRecus
        );
    }

    // =========================================================
    // FIND CHILDREN
    // =========================================================

    private Cause trouverCauseDuConstat(
            Constat constat,
            Long causeId) {

        return constat.getCauses()
                .stream()
                .filter(cause ->
                        cause.getId() != null
                                && cause.getId()
                                        .equals(causeId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "La cause "
                                        + causeId
                                        + " n'appartient pas au constat "
                                        + constat.getId()
                        )
                );
    }

    private Risque trouverRisqueDuConstat(
            Constat constat,
            Long risqueId) {

        return constat.getRisques()
                .stream()
                .filter(risque ->
                        risque.getId() != null
                                && risque.getId()
                                        .equals(risqueId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Le risque "
                                        + risqueId
                                        + " n'appartient pas au constat "
                                        + constat.getId()
                        )
                );
    }

    private Consequence trouverConsequenceDuConstat(
            Constat constat,
            Long consequenceId) {

        return constat.getConsequences()
                .stream()
                .filter(consequence ->
                        consequence.getId() != null
                                && consequence.getId()
                                        .equals(consequenceId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "La conséquence "
                                        + consequenceId
                                        + " n'appartient pas au constat "
                                        + constat.getId()
                        )
                );
    }

    private Recommandation trouverRecommandationDuConstat(
            Constat constat,
            Long recommandationId) {

        return constat.getRecommandations()
                .stream()
                .filter(recommandation ->
                        recommandation.getId() != null
                                && recommandation.getId()
                                        .equals(recommandationId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "La recommandation "
                                        + recommandationId
                                        + " n'appartient pas au constat "
                                        + constat.getId()
                        )
                );
    }

    // =========================================================
    // DELETE CHILDREN NOT SENT
    // =========================================================

    private void supprimerCausesAbsentes(
            Constat constat,
            Set<Long> idsRecus) {

        constat.getCauses()
                .removeIf(cause ->
                        cause.getId() != null
                                && !idsRecus.contains(
                                        cause.getId()
                                )
                );
    }

    private void supprimerRisquesAbsentes(
            Constat constat,
            Set<Long> idsRecus) {

        constat.getRisques()
                .removeIf(risque ->
                        risque.getId() != null
                                && !idsRecus.contains(
                                        risque.getId()
                                )
                );
    }

    private void supprimerConsequencesAbsentes(
            Constat constat,
            Set<Long> idsRecus) {

        constat.getConsequences()
                .removeIf(consequence ->
                        consequence.getId() != null
                                && !idsRecus.contains(
                                        consequence.getId()
                                )
                );
    }

    private void supprimerRecommandationsAbsentes(
            Constat constat,
            Set<Long> idsRecus) {

        constat.getRecommandations()
                .removeIf(recommandation ->
                        recommandation.getId() != null
                                && !idsRecus.contains(
                                        recommandation.getId()
                                )
                );
    }

    // =========================================================
    // MAPPING RESPONSE
    // =========================================================

    private ConstatResponse toResponse(
            Constat constat) {

        ConstatResponse response =
                new ConstatResponse();

        // =====================================================
        // CONSTAT
        // =====================================================

        response.setId(
                constat.getId()
        );

        response.setReference(
                constat.getReference()
        );

        response.setDescriptions(
                constat.getDescriptions()
        );

        response.setNiveauRisque(
                constat.getNiveauRisque()
        );

        response.setDirectionServiceConcerne(
                constat.getDirectionServiceConcerne()
        );

        // =====================================================
        // TEST
        // =====================================================

        Test test =
                constat.getTest();

        if (test != null) {

            response.setTestId(
                    test.getId()
            );

            response.setTestReference(
                    test.getReference()
            );

            // =================================================
            // LIGNE DU PROGRAMME
            // =================================================

            LigneProgramme ligneProgramme =
                    test.getLigneProgramme();

            if (ligneProgramme != null) {

                response.setNumeroControle(
                        ligneProgramme.getNumeroControle()
                );

                response.setDomaineCycle(
                        ligneProgramme.getDomaineCycle()
                );

                // =============================================
                // OBJECTIF
                // =============================================

                Objectif objectif =
                        ligneProgramme.getObjectif();

                if (objectif != null) {

                    response.setObjectifId(
                            objectif.getId()
                    );

                    response.setObjectifNumero(
                            objectif.getNumero()
                    );

                    response.setObjectifDescription(
                            objectif.getDescriptions()
                    );
                }
            }
        }

        // =====================================================
        // CAUSES
        // =====================================================

        response.setCauses(
                constat.getCauses()
                        .stream()
                        .map(this::toCauseResponse)
                        .collect(Collectors.toList())
        );

        // =====================================================
        // RISQUES
        // =====================================================

        response.setRisques(
                constat.getRisques()
                        .stream()
                        .map(this::toRisqueResponse)
                        .collect(Collectors.toList())
        );

        // =====================================================
        // CONSEQUENCES
        // =====================================================

        response.setConsequences(
                constat.getConsequences()
                        .stream()
                        .map(this::toConsequenceResponse)
                        .collect(Collectors.toList())
        );

        // =====================================================
        // RECOMMANDATIONS
        // =====================================================

        response.setRecommandations(
                constat.getRecommandations()
                        .stream()
                        .map(this::toRecommandationResponse)
                        .collect(Collectors.toList())
        );

        return response;
    }

    // =========================================================
    // CAUSE RESPONSE
    // =========================================================

    private CauseResponse toCauseResponse(
            Cause cause) {

        CauseResponse response =
                new CauseResponse();

        response.setId(
                cause.getId()
        );

        response.setDescriptions(
                cause.getDescriptions()
        );

        return response;
    }

    // =========================================================
    // RISQUE RESPONSE
    // =========================================================

    private RisqueResponse toRisqueResponse(
            Risque risque) {

        RisqueResponse response =
                new RisqueResponse();

        response.setId(
                risque.getId()
        );

        response.setDescriptions(
                risque.getDescriptions()
        );

        response.setNiveau(
                risque.getNiveau()
        );

        return response;
    }

    // =========================================================
    // CONSEQUENCE RESPONSE
    // =========================================================

    private ConsequenceResponse toConsequenceResponse(
            Consequence consequence) {

        ConsequenceResponse response =
                new ConsequenceResponse();

        response.setId(
                consequence.getId()
        );

        response.setDescriptions(
                consequence.getDescriptions()
        );

        return response;
    }

    // =========================================================
    // RECOMMANDATION RESPONSE
    // =========================================================

    private RecommandationResponse toRecommandationResponse(
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