package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
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
import com.audit.audit_document.domain.entity.MissionRole;
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

    // ============================================================
    // CREATE
    // ============================================================

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

        /*
         * Get the programme line.
         */
        LigneProgramme ligneProgramme =
                ligneProgrammeRepository
                        .findById(
                                request.getLigneProgrammeId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "LigneProgramme introuvable avec l'id : "
                                                + request.getLigneProgrammeId()
                                )
                        );

        /*
         * Automatically determine the mission
         * that the programme line belongs to.
         */
        Long missionId =
                resolveMissionId(
                        ligneProgramme
                );

        Test test = new Test();

        /*
         * Automatic test reference.
         * Example: FT001, FT002, FT003...
         */
        test.setReference(
                testRepository.getNextReference()
        );

        test.setLigneProgramme(
                ligneProgramme
        );

        /*
         * Test-specific data.
         */
        fillTestData(
                test,
                request
        );

        /*
         * Quality control people.
         *
         * The backend now checks:
         * - the MissionPersonne exists
         * - it belongs to the mission
         * - correct role
         */
        test.setAuditeur(
                findMissionPersonne(
                        request.getAuditeurMissionPersonneId(),
                        missionId,
                        MissionRole.AUDITEUR
                )
        );

        test.setChefMission(
                findMissionPersonne(
                        request.getChefMissionMissionPersonneId(),
                        missionId,
                        MissionRole.CHEF_MISSION
                )
        );

        test.setSuperviseur(
                findMissionPersonne(
                        request.getSuperviseurMissionPersonneId(),
                        missionId,
                        MissionRole.SUPERVISEUR
                )
        );

        /*
         * First save:
         * needed to get the Test ID.
         */
        Test testSauvegarde =
                testRepository.save(test);

        /*
         * Create the samples.
         */
        addNewEchantillons(
                testSauvegarde,
                request.getEchantillons()
        );

        /*
         * Final save with the samples.
         */
        Test resultat =
                testRepository.save(testSauvegarde);

        return toResponse(resultat);
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public TestResponse getById(Long id) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Test introuvable avec l'id : "
                                                + id
                                )
                        );

        return toResponse(test);
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<TestResponse> getAll() {

        List<Test> tests =
                testRepository.findAll();

        List<TestResponse> responses =
                new ArrayList<>();

        for (Test test : tests) {

            responses.add(
                    toResponse(test)
            );
        }

        return responses;
    }

    // ============================================================
    // GET BY LIGNE PROGRAMME
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<TestResponse> getByLigneProgrammeId(
            Long ligneProgrammeId) {

        /*
         * Check that the line exists.
         */
        ligneProgrammeRepository
                .findById(ligneProgrammeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
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
                    toResponse(test)
            );
        }

        return responses;
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public TestResponse update(
            Long id,
            CreateTestRequest request) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
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

        /*
         * Get the new programme line.
         */
        LigneProgramme ligneProgramme =
                ligneProgrammeRepository
                        .findById(
                                request.getLigneProgrammeId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "LigneProgramme introuvable avec l'id : "
                                                + request.getLigneProgrammeId()
                                )
                        );

        /*
         * Automatically determine the mission
         * matching this line.
         */
        Long missionId =
                resolveMissionId(
                        ligneProgramme
                );

        /*
         * The programme line can be changed,
         * but the FTxxx reference stays unchanged.
         */
        test.setLigneProgramme(
                ligneProgramme
        );

        /*
         * Update the test data.
         */
        fillTestData(
                test,
                request
        );

        /*
         * Update the people.
         *
         * The backend checks:
         * - the mission
         * - the role
         */
        test.setAuditeur(
                findMissionPersonne(
                        request.getAuditeurMissionPersonneId(),
                        missionId,
                        MissionRole.AUDITEUR
                )
        );

        test.setChefMission(
                findMissionPersonne(
                        request.getChefMissionMissionPersonneId(),
                        missionId,
                        MissionRole.CHEF_MISSION
                )
        );

        test.setSuperviseur(
                findMissionPersonne(
                        request.getSuperviseurMissionPersonneId(),
                        missionId,
                        MissionRole.SUPERVISEUR
                )
        );

        /*
         * Update the samples.
         */
        updateEchantillons(
                test,
                request.getEchantillons()
        );

        Test resultat =
                testRepository.save(test);

        return toResponse(resultat);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void delete(Long id) {

        Test test =
                testRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Test introuvable avec l'id : "
                                                + id
                                )
                        );

        testRepository.deleteById(
                test.getId()
        );
    }

    // ============================================================
    // FILL TEST DATA
    // ============================================================

    private void fillTestData(
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

    // ============================================================
    // GET THE MISSION OF THE PROGRAMME LINE
    // ============================================================

    private Long resolveMissionId(
            LigneProgramme ligneProgramme) {

        if (ligneProgramme == null) {
            throw new IllegalArgumentException(
                    "La ligne du programme est obligatoire."
            );
        }

        if (ligneProgramme.getObjectif() == null) {
            throw new IllegalArgumentException(
                    "La ligne du programme ne possède aucun objectif."
            );
        }

        if (ligneProgramme.getObjectif().getTdr() == null) {
            throw new IllegalArgumentException(
                    "L'objectif ne possède aucun TDR."
            );
        }

        if (ligneProgramme.getObjectif()
                .getTdr()
                .getMission() == null) {

            throw new IllegalArgumentException(
                    "Le TDR ne possède aucune mission."
            );
        }

        if (ligneProgramme.getObjectif()
                .getTdr()
                .getMission()
                .getId() == null) {

            throw new IllegalArgumentException(
                    "La mission ne possède aucun ID."
            );
        }

        return ligneProgramme
                .getObjectif()
                .getTdr()
                .getMission()
                .getId();
    }

    // ============================================================
    // FIND A MISSION PERSONNE
    // ============================================================

    private MissionPersonne findMissionPersonne(
            Long id,
            Long missionId,
            MissionRole role) {

        /*
         * The field is optional.
         */
        if (id == null) {
            return null;
        }

        /*
         * Check that the MissionPersonne exists.
         */
        MissionPersonne missionPersonne =
                missionPersonneRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "MissionPersonne introuvable avec l'id : "
                                                + id
                                )
                        );

        /*
         * Check that the MissionPersonne belongs
         * to the Test's mission.
         */
        if (missionPersonne.getMission() == null
                || missionPersonne.getMission().getId() == null
                || !missionPersonne.getMission()
                        .getId()
                        .equals(missionId)) {

            throw new IllegalArgumentException(
                    "La MissionPersonne "
                            + id
                            + " n'appartient pas à la mission "
                            + missionId
            );
        }

        /*
         * Check the role.
         */
        if (missionPersonne.getRoles() == null
                || !missionPersonne.getRoles()
                        .equalsIgnoreCase(role.name())) {

            throw new IllegalArgumentException(
                    "La MissionPersonne "
                            + id
                            + " n'a pas le rôle "
                            + role
            );
        }

        return missionPersonne;
    }

    // ============================================================
    // ADD NEW SAMPLES
    // ============================================================

    private void addNewEchantillons(
            Test test,
            List<EchantillonRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return;
        }

        /*
         * Internal number:
         * 1, 2, 3...
         */
        Integer prochainNumero =
                echantillonRepository.getNextNumero(
                        test.getId()
                );

        for (EchantillonRequest request : requests) {

            Echantillon echantillon =
                    new Echantillon();

            echantillon.setTest(
                    test
            );

            /*
             * Automatic number.
             */
            echantillon.setNumero(
                    prochainNumero
            );

            prochainNumero++;

            /*
             * Automatic reference.
             * Example: DOS-001, DOS-002...
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

    // ============================================================
    // UPDATE SAMPLES
    // ============================================================

    private void updateEchantillons(
            Test test,
            List<EchantillonRequest> requests) {

        /*
         * If null:
         * delete all the previous samples.
         */
        if (requests == null) {

            List<Echantillon> anciens =
                    new ArrayList<>(
                            test.getEchantillons()
                    );

            for (Echantillon echantillon :
                    anciens) {

                test.removeEchantillon(
                        echantillon
                );
            }

            return;
        }

        /*
         * Index the previous samples by ID.
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
         * IDs received from the frontend.
         */
        Set<Long> idsRecus =
                new HashSet<>();

        /*
         * Next sample number.
         */
        Integer prochainNumero =
                echantillonRepository.getNextNumero(
                        test.getId()
                );

        /*
         * Iterate over the received samples.
         */
        for (EchantillonRequest request :
                requests) {

            /*
             * ====================================================
             * CASE 1: EXISTING SAMPLE
             * ====================================================
             */
            if (request.getId() != null) {

                Echantillon ancien =
                        anciensParId.get(
                                request.getId()
                        );

                /*
                 * Check that the sample belongs
                 * to the current Test.
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
                 * The reference and the number stay stable.
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
             * CASE 2: NEW SAMPLE
             * ====================================================
             */
            else {

                Echantillon nouveau =
                        new Echantillon();

                nouveau.setTest(
                        test
                );

                /*
                 * New automatic number.
                 */
                nouveau.setNumero(
                        prochainNumero
                );

                prochainNumero++;

                /*
                 * New automatic reference.
                 */
                nouveau.setReference(
                        echantillonRepository
                                .getNextReference()
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
         * DELETE PREVIOUS SAMPLES
         * ========================================================
         *
         * If a previous sample is no longer present
         * in the request, it is deleted.
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
             * New samples have id = null.
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

    private TestResponse toResponse(
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
         * PROGRAMME LINE
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
         * OBJECTIVE
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
         * TEST-SPECIFIC DATA
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
         * AUDITOR
         * ========================================================
         */
        fillPersonneResponse(
                test.getAuditeur(),
                "auditeur",
                response
        );

        response.setDateAudit(
                test.getDateAudit()
        );

        /*
         * ========================================================
         * MISSION LEADER
         * ========================================================
         */
        fillPersonneResponse(
                test.getChefMission(),
                "chefMission",
                response
        );

        response.setDateRevueChefMission(
                test.getDateRevueChefMission()
        );

        /*
         * ========================================================
         * SUPERVISOR
         * ========================================================
         */
        fillPersonneResponse(
                test.getSuperviseur(),
                "superviseur",
                response
        );

        response.setDateRevueSuperviseur(
                test.getDateRevueSuperviseur()
        );

        /*
         * ========================================================
         * SAMPLES
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

    private void fillPersonneResponse(
            MissionPersonne missionPersonne,
            String type,
            TestResponse response) {

        if (missionPersonne == null
                || missionPersonne.getPersonne() == null) {

            return;
        }

        /*
         * AUDITOR
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
         * MISSION LEADER
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
         * SUPERVISOR
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