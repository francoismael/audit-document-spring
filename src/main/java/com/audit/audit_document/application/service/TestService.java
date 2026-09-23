package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateTestRequest;
import com.audit.audit_document.application.dto.EchantillonRequest;
import com.audit.audit_document.application.dto.EchantillonResponse;
import com.audit.audit_document.application.dto.TestResponse;
import com.audit.audit_document.application.usecases.CreateTestUseCase;
import com.audit.audit_document.application.usecases.DeleteTestUseCase;
import com.audit.audit_document.application.usecases.GetAllTestsUseCase;
import com.audit.audit_document.application.usecases.GetTestByIdUseCase;
import com.audit.audit_document.application.usecases.GetTestsByLigneProgrammeUseCase;
import com.audit.audit_document.application.usecases.UpdateTestUseCase;
import com.audit.audit_document.domain.entity.Echantillon;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.Test;
import com.audit.audit_document.domain.repository.EchantillonRepository;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.TestRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class TestService implements
        CreateTestUseCase,
        GetTestByIdUseCase,
        GetAllTestsUseCase,
        GetTestsByLigneProgrammeUseCase,
        UpdateTestUseCase,
        DeleteTestUseCase {

    private final TestRepository testRepository;
    private final EchantillonRepository echantillonRepository;
    private final LigneProgrammeRepository ligneProgrammeRepository;
    private final MissionPersonneRepository missionPersonneRepository;

    public TestService(
            TestRepository testRepository,
            EchantillonRepository echantillonRepository,
            LigneProgrammeRepository ligneProgrammeRepository,
            MissionPersonneRepository missionPersonneRepository) {

        this.testRepository = testRepository;
        this.echantillonRepository = echantillonRepository;
        this.ligneProgrammeRepository = ligneProgrammeRepository;
        this.missionPersonneRepository = missionPersonneRepository;
    }

    // CREATE


    @Override
    public TestResponse create(
            CreateTestRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Les données du test sont obligatoires."
            );
        }

        if (request.getLigneProgrammeId() == null) {
            throw new IllegalArgumentException(
                    "La ligne du programme est obligatoire."
            );
        }

        LigneProgramme ligneProgramme =
                ligneProgrammeRepository
                        .findById(request.getLigneProgrammeId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "LigneProgramme introuvable avec l'id : "
                                                + request.getLigneProgrammeId()
                                )
                        );

        Test test = new Test();

        /*
         * Référence automatique du test.
         * Exemple : FT001, FT002, FT003...
         */
        test.setReference(
                testRepository.getNextReference()
        );

        test.setLigneProgramme(
                ligneProgramme
        );

        /*
         * Données propres au test.
         */
        remplirDonneesTest(
                test,
                request
        );

        /*
         * Personnes du contrôle qualité.
         */
        test.setAuditeur(
                trouverMissionPersonne(
                        request.getAuditeurMissionPersonneId()
                )
        );

        test.setChefMission(
                trouverMissionPersonne(
                        request.getChefMissionMissionPersonneId()
                )
        );

        test.setSuperviseur(
                trouverMissionPersonne(
                        request.getSuperviseurMissionPersonneId()
                )
        );

        /*
         * Première sauvegarde :
         * nécessaire pour obtenir l'ID du Test.
         */
        Test testSauvegarde =
                testRepository.save(test);

        /*
         * Création des échantillons.
         */
        ajouterNouveauxEchantillons(
                testSauvegarde,
                request.getEchantillons()
        );

        /*
         * Sauvegarde finale avec les échantillons.
         */
        Test resultat =
                testRepository.save(testSauvegarde);

        return convertirEnResponse(resultat);
    }

    // GET BY ID


    @Override
    @Transactional(readOnly = true)
    public TestResponse getById(Long id) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Test introuvable avec l'id : "
                                                + id
                                )
                        );

        return convertirEnResponse(test);
    }

    // GET ALL


    @Override
    @Transactional(readOnly = true)
    public List<TestResponse> getAll() {

        List<Test> tests =
                testRepository.findAll();

        List<TestResponse> responses =
                new ArrayList<>();

        for (Test test : tests) {

            responses.add(
                    convertirEnResponse(test)
            );
        }

        return responses;
    }


    // GET BY LIGNE PROGRAMME


    @Override
    @Transactional(readOnly = true)
    public List<TestResponse> getByLigneProgrammeId(
            Long ligneProgrammeId) {

        /*
         * Vérifier que la ligne existe.
         */
        ligneProgrammeRepository
                .findById(ligneProgrammeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "LigneProgramme introuvable avec l'id : "
                                        + ligneProgrammeId
                        )
                );

        List<Test> tests =
                testRepository.findByLigneProgrammeId(
                        ligneProgrammeId
                );

        List<TestResponse> responses =
                new ArrayList<>();

        for (Test test : tests) {

            responses.add(
                    convertirEnResponse(test)
            );
        }

        return responses;
    }

    // UPDATE


    @Override
    public TestResponse update(
            Long id,
            CreateTestRequest request) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Test introuvable avec l'id : "
                                                + id
                                )
                        );

        if (request == null) {
            throw new IllegalArgumentException(
                    "Les données du test sont obligatoires."
            );
        }

        if (request.getLigneProgrammeId() == null) {
            throw new IllegalArgumentException(
                    "La ligne du programme est obligatoire."
            );
        }

        LigneProgramme ligneProgramme =
                ligneProgrammeRepository
                        .findById(
                                request.getLigneProgrammeId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "LigneProgramme introuvable avec l'id : "
                                                + request.getLigneProgrammeId()
                                )
                        );


        test.setLigneProgramme(
                ligneProgramme
        );

        /*
         * Mise à jour des données du test.
         */
        remplirDonneesTest(
                test,
                request
        );

        /*
         * Mise à jour des personnes.
         */
        test.setAuditeur(
                trouverMissionPersonne(
                        request.getAuditeurMissionPersonneId()
                )
        );

        test.setChefMission(
                trouverMissionPersonne(
                        request.getChefMissionMissionPersonneId()
                )
        );

        test.setSuperviseur(
                trouverMissionPersonne(
                        request.getSuperviseurMissionPersonneId()
                )
        );

        /*
         * Mise à jour des échantillons.
         */
        mettreAJourEchantillons(
                test,
                request.getEchantillons()
        );

        Test resultat =
                testRepository.save(test);

        return convertirEnResponse(resultat);
    }

    // DELETE


    @Override
    public void delete(Long id) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Test introuvable avec l'id : "
                                                + id
                                )
                        );

        testRepository.deleteById(
                test.getId()
        );
    }


    // GET DATA TEST


    private void remplirDonneesTest(
            Test test,
            CreateTestRequest request) {

        test.setDateTest(
                request.getDateTest()
        );

        test.setProcedureRealisee(
                request.getProcedureRealisee()
        );

        test.setResumeAnomalies(
                request.getResumeAnomalies()
        );

        test.setResultatTest(
                request.getResultatTest()
        );

        test.setRisqueMaitrise(
                request.getRisqueMaitrise()
        );

        test.setRecommandations(
                request.getRecommandations()
        );

        test.setCommentairesChefMission(
                request.getCommentairesChefMission()
        );

        test.setCommentairesSuperviseur(
                request.getCommentairesSuperviseur()
        );

        test.setDateAudit(
                request.getDateAudit()
        );

        test.setDateRevueChefMission(
                request.getDateRevueChefMission()
        );

        test.setDateRevueSuperviseur(
                request.getDateRevueSuperviseur()
        );
    }


    // GET DATA MISSION PERSONNE 


    private MissionPersonne trouverMissionPersonne(
            Long id) {

        /*
         * Le champ est facultatif.
         */
        if (id == null) {
            return null;
        }

        return missionPersonneRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "MissionPersonne introuvable avec l'id : "
                                        + id
                        )
                );
    }


    
    // ADD NEW ECHANTILLON


    private void ajouterNouveauxEchantillons(
            Test test,
            List<EchantillonRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return;
        }

        /*
         * Numéro interne de l'échantillon :
         * 1, 2, 3...
         */
        Integer prochainNumero =
                echantillonRepository.getNextNumero(
                        test.getId()
                );

        for (EchantillonRequest request : requests) {

            Echantillon echantillon =
                    new Echantillon();

            echantillon.setTest(test);

            /*
             * Numéro automatique.
             */
            echantillon.setNumero(
                    prochainNumero
            );

            prochainNumero++;

            /*
             * Référence automatique.
             * Exemple : DOS-001, DOS-002...
             */
            echantillon.setReference(
                    echantillonRepository.getNextReference()
            );

            echantillon.setAnomalieDetectee(
                    request.getAnomalieDetectee() != null
                            ? request.getAnomalieDetectee()
                            : false
            );

            echantillon.setObservation(
                    request.getObservation()
            );

            test.addEchantillon(
                    echantillon
            );
        }
    }


    //MAJ ÉCHANTILLONS


    private void mettreAJourEchantillons(
            Test test,
            List<EchantillonRequest> requests) {

        /*
         * Si null :
         * supprimer tous les anciens échantillons.
         */
        if (requests == null) {

            List<Echantillon> anciens =
                    new ArrayList<>(
                            test.getEchantillons()
                    );

            for (Echantillon echantillon : anciens) {

                test.removeEchantillon(
                        echantillon
                );
            }

            return;
        }

        /*
         * Indexer les anciens échantillons
         * par leur ID.
         */
        Map<Long, Echantillon> anciensParId =
                new HashMap<>();

        for (Echantillon echantillon :
                test.getEchantillons()) {

            if (echantillon.getId() != null) {

                anciensParId.put(
                        echantillon.getId(),
                        echantillon
                );
            }
        }

        /*
         * IDs reçus du frontend.
         */
        Set<Long> idsRecus =
                new HashSet<>();

        /*
         * Prochain numéro d'échantillon.
         */
        Integer prochainNumero =
                echantillonRepository.getNextNumero(
                        test.getId()
                );

        /*
         * Parcourir les échantillons reçus.
         */
        for (EchantillonRequest request :
                requests) {

            /*
             * ====================================================
             * CAS 1 : ÉCHANTILLON EXISTANT
             * ====================================================
             */
            if (request.getId() != null) {

                Echantillon ancien =
                        anciensParId.get(
                                request.getId()
                        );

                /*
                 * Vérifier que l'échantillon appartient bien
                 * au Test courant.
                 */
                if (ancien == null) {

                    throw new IllegalArgumentException(
                            "L'échantillon avec l'id "
                                    + request.getId()
                                    + " n'appartient pas au test "
                                    + test.getId()
                    );
                }

                idsRecus.add(
                        ancien.getId()
                );

                /*
                 * IMPORTANT :
                 *
                 * On ne modifie ni :
                 * - numero
                 * - reference
                 *
                 * Ils restent stables.
                 */
                ancien.setAnomalieDetectee(
                        request.getAnomalieDetectee() != null
                                ? request.getAnomalieDetectee()
                                : false
                );

                ancien.setObservation(
                        request.getObservation()
                );
            }

            /*
             * ====================================================
             * CAS 2 : NOUVEL ÉCHANTILLON
             * ====================================================
             */
            else {

                Echantillon nouveau =
                        new Echantillon();

                nouveau.setTest(test);

                /*
                 * Nouveau numéro automatique.
                 */
                nouveau.setNumero(
                        prochainNumero
                );

                prochainNumero++;

                /*
                 * Nouvelle référence automatique.
                 */
                nouveau.setReference(
                        echantillonRepository.getNextReference()
                );

                nouveau.setAnomalieDetectee(
                        request.getAnomalieDetectee() != null
                                ? request.getAnomalieDetectee()
                                : false
                );

                nouveau.setObservation(
                        request.getObservation()
                );

                test.addEchantillon(
                        nouveau
                );
            }
        }

        /*
         * ========================================================
         * SUPPRESSION DES ANCIENS ÉCHANTILLONS
         * ========================================================
         *
         * Si un ancien échantillon n'est plus envoyé par le
         * frontend, il est supprimé.
         */
        List<Echantillon> anciens =
                new ArrayList<>(
                        test.getEchantillons()
                );

        for (Echantillon echantillon :
                anciens) {

            Long echantillonId =
                    echantillon.getId();

            /*
             * Les nouveaux échantillons ont id = null.
             * Ils ne doivent donc pas être supprimés.
             */
            if (echantillonId != null
                    && !idsRecus.contains(echantillonId)) {

                test.removeEchantillon(
                        echantillon
                );
            }
        }
    }

    // ============================================================
    // ENTITY -> RESPONSE
    // ============================================================

    private TestResponse convertirEnResponse(
            Test test) {

        TestResponse response =
                new TestResponse();

        /*
         * ========================================================
         * TEST
         * ========================================================
         */
        response.setId(
                test.getId()
        );

        response.setReference(
                test.getReference()
        );

        /*
         * ========================================================
         * LIGNE PROGRAMME
         * ========================================================
         */
        LigneProgramme ligneProgramme =
                test.getLigneProgramme();

        if (ligneProgramme == null) {
            return response;
        }

        response.setLigneProgrammeId(
                ligneProgramme.getId()
        );

        response.setNumeroControle(
                ligneProgramme.getNumeroControle()
        );

        response.setTacheOperation(
                ligneProgramme.getTacheOperation()
        );

        response.setFaiblesseAConfirmer(
                ligneProgramme.getFaiblesseAConfirmer()
        );

        response.setRisque(
                ligneProgramme.getRisque()
        );

        response.setDomaineCycle(
                ligneProgramme.getDomaineCycle()
        );

        response.setTypeControle(
                ligneProgramme.getTypeControle()
        );

        response.setResponsable(
                ligneProgramme.getResponsable()
        );

        response.setFrequence(
                ligneProgramme.getFrequence()
        );

        response.setEchantillonDescription(
                ligneProgramme.getEchantillonDescription()
        );

        response.setTechniqueEchantillonnage(
                ligneProgramme.getTechniqueEchantillonnage()
        );

        response.setProcedureTest(
                ligneProgramme.getProcedureTest()
        );

        /*
         * ========================================================
         * OBJECTIF
         * ========================================================
         */
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

            /*
             * ====================================================
             * TDR
             * ====================================================
             */
            if (objectif.getTdr() != null) {

                response.setLieu(
                        objectif.getTdr().getLieu()
                );

                response.setPeriodeObservation(
                        objectif.getTdr()
                                .getPeriodeObservation()
                );

                /*
                 * =================================================
                 * MISSION
                 * =================================================
                 */
                if (objectif.getTdr().getMission() != null) {

                    response.setMissionNumero(
                            objectif.getTdr()
                                    .getMission()
                                    .getNumero()
                    );

                    response.setMissionIntitule(
                            objectif.getTdr()
                                    .getMission()
                                    .getIntitule()
                    );

                    response.setMissionObjet(
                            objectif.getTdr()
                                    .getMission()
                                    .getObjet()
                    );

                    /*
                     * ==============================================
                     * STRUCTURE
                     * ==============================================
                     */
                    if (objectif.getTdr()
                            .getMission()
                            .getStructure() != null) {

                        response.setStructureNom(
                                objectif.getTdr()
                                        .getMission()
                                        .getStructure()
                                        .getNom()
                        );
                    }
                }
            }
        }

        /*
         * ========================================================
         * DONNÉES PROPRES AU TEST
         * ========================================================
         */
        response.setDateTest(
                test.getDateTest()
        );

        response.setProcedureRealisee(
                test.getProcedureRealisee()
        );

        response.setResumeAnomalies(
                test.getResumeAnomalies()
        );

        response.setResultatTest(
                test.getResultatTest()
        );

        response.setRisqueMaitrise(
                test.getRisqueMaitrise()
        );

        response.setRecommandations(
                test.getRecommandations()
        );

        response.setCommentairesChefMission(
                test.getCommentairesChefMission()
        );

        response.setCommentairesSuperviseur(
                test.getCommentairesSuperviseur()
        );

        /*
         * ========================================================
         * AUDITEUR
         * ========================================================
         */
        remplirPersonneResponse(
                test.getAuditeur(),
                "auditeur",
                response
        );

        response.setDateAudit(
                test.getDateAudit()
        );

        /*
         * ========================================================
         * CHEF DE MISSION
         * ========================================================
         */
        remplirPersonneResponse(
                test.getChefMission(),
                "chefMission",
                response
        );

        response.setDateRevueChefMission(
                test.getDateRevueChefMission()
        );

        /*
         * ========================================================
         * SUPERVISEUR
         * ========================================================
         */
        remplirPersonneResponse(
                test.getSuperviseur(),
                "superviseur",
                response
        );

        response.setDateRevueSuperviseur(
                test.getDateRevueSuperviseur()
        );

        /*
         * ========================================================
         * ÉCHANTILLONS
         * ========================================================
         */
        List<EchantillonResponse>
                echantillonResponses =
                new ArrayList<>();

        List<Echantillon> echantillons =
                test.getEchantillons();

        if (echantillons != null) {

            for (Echantillon echantillon :
                    echantillons) {

                EchantillonResponse
                        echantillonResponse =
                        new EchantillonResponse();

                echantillonResponse.setId(
                        echantillon.getId()
                );

                echantillonResponse.setNumero(
                        echantillon.getNumero()
                );

                echantillonResponse.setReference(
                        echantillon.getReference()
                );

                echantillonResponse.setAnomalieDetectee(
                        echantillon.getAnomalieDetectee()
                );

                echantillonResponse.setObservation(
                        echantillon.getObservation()
                );

                echantillonResponses.add(
                        echantillonResponse
                );
            }
        }

        response.setEchantillons(
                echantillonResponses
        );

        return response;
    }

    // ============================================================
    // PERSONNE -> RESPONSE
    // ============================================================

    private void remplirPersonneResponse(
            MissionPersonne missionPersonne,
            String type,
            TestResponse response) {

        if (missionPersonne == null
                || missionPersonne.getPersonne() == null) {

            return;
        }

        /*
         * AUDITEUR
         */
        if ("auditeur".equals(type)) {

            response.setAuditeurMissionPersonneId(
                    missionPersonne.getId()
            );

            response.setAuditeurNom(
                    missionPersonne
                            .getPersonne()
                            .getNom()
            );

            response.setAuditeurPrenom(
                    missionPersonne
                            .getPersonne()
                            .getPrenom()
            );
        }

        /*
         * CHEF DE MISSION
         */
        else if ("chefMission".equals(type)) {

            response.setChefMissionMissionPersonneId(
                    missionPersonne.getId()
            );

            response.setChefMissionNom(
                    missionPersonne
                            .getPersonne()
                            .getNom()
            );

            response.setChefMissionPrenom(
                    missionPersonne
                            .getPersonne()
                            .getPrenom()
            );
        }

        /*
         * SUPERVISEUR
         */
        else if ("superviseur".equals(type)) {

            response.setSuperviseurMissionPersonneId(
                    missionPersonne.getId()
            );

            response.setSuperviseurNom(
                    missionPersonne
                            .getPersonne()
                            .getNom()
            );

            response.setSuperviseurPrenom(
                    missionPersonne
                            .getPersonne()
                            .getPrenom()
            );
        }
    }
}