package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.AvancementMissionResponse;
import com.audit.audit_document.application.usecases.GetAvancementMissionsUseCase;
import com.audit.audit_document.domain.entity.Constat;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.PvAffirmation;
import com.audit.audit_document.domain.entity.RapportAudit;
import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.entity.Test;
import com.audit.audit_document.domain.repository.ConstatRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PvAffirmationRepository;
import com.audit.audit_document.domain.repository.RapportAuditRepository;
import com.audit.audit_document.domain.repository.ReunionRepository;
import com.audit.audit_document.domain.repository.TdrRepository;
import com.audit.audit_document.domain.repository.TestRepository;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

@Service
public class GetAvancementMissionsService
        implements GetAvancementMissionsUseCase {

    private static final int DOCUMENTS_ATTENDUS = 7;

    private final MissionRepository missionRepository;
    private final TdrRepository tdrRepository;
    private final ProgrammeTravailRepository programmeTravailRepository;
    private final TestRepository testRepository;
    private final ConstatRepository constatRepository;
    private final RapportAuditRepository rapportAuditRepository;
    private final PvAffirmationRepository pvAffirmationRepository;
    private final ReunionRepository reunionRepository;

    public GetAvancementMissionsService(
            MissionRepository missionRepository,
            TdrRepository tdrRepository,
            ProgrammeTravailRepository programmeTravailRepository,
            TestRepository testRepository,
            ConstatRepository constatRepository,
            RapportAuditRepository rapportAuditRepository,
            PvAffirmationRepository pvAffirmationRepository,
            ReunionRepository reunionRepository) {

        this.missionRepository = missionRepository;
        this.tdrRepository = tdrRepository;
        this.programmeTravailRepository =
                programmeTravailRepository;
        this.testRepository = testRepository;
        this.constatRepository = constatRepository;
        this.rapportAuditRepository =
                rapportAuditRepository;
        this.pvAffirmationRepository =
                pvAffirmationRepository;
        this.reunionRepository = reunionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvancementMissionResponse> execute() {

        List<Mission> missions =
                missionRepository.findAll();

        /*
         * On prépare une liste d'identifiants de missions
         * pour chaque type de document.
         */

        Set<Long> missionsAvecTdr =
                getMissionsAvecTdr();

        Set<Long> missionsAvecProgramme =
                getMissionsAvecProgramme();

        Set<Long> missionsAvecTest =
                getMissionsAvecTest();

        Set<Long> missionsAvecConstat =
                getMissionsAvecConstat();

        Set<Long> missionsAvecRapport =
                getMissionsAvecRapport();

        Set<Long> missionsAvecPvAffirmation =
                getMissionsAvecPvAffirmation();

        Set<Long> missionsAvecPvCloture =
                getMissionsAvecPvCloture();

        List<AvancementMissionResponse> responses =
                new ArrayList<>();

        for (Mission mission : missions) {

            Long missionId = mission.getId();

            boolean tdr =
                    missionsAvecTdr.contains(missionId);

            boolean programme =
                    missionsAvecProgramme.contains(missionId);

            boolean tests =
                    missionsAvecTest.contains(missionId);

            boolean constats =
                    missionsAvecConstat.contains(missionId);

            boolean rapport =
                    missionsAvecRapport.contains(missionId);

            boolean pvAffirmation =
                    missionsAvecPvAffirmation
                            .contains(missionId);

            boolean pvCloture =
                    missionsAvecPvCloture
                            .contains(missionId);

            int documentsCompletes = 0;

            if (tdr) {
                documentsCompletes++;
            }

            if (programme) {
                documentsCompletes++;
            }

            if (tests) {
                documentsCompletes++;
            }

            if (constats) {
                documentsCompletes++;
            }

            if (rapport) {
                documentsCompletes++;
            }

            if (pvAffirmation) {
                documentsCompletes++;
            }

            if (pvCloture) {
                documentsCompletes++;
            }

            double pourcentage =
                    (documentsCompletes * 100.0)
                            / DOCUMENTS_ATTENDUS;

            /*
             * Arrondi à deux décimales.
             */
            pourcentage =
                    Math.round(pourcentage * 100.0)
                            / 100.0;

            AvancementMissionResponse response =
                    new AvancementMissionResponse();

            response.setMissionId(
                    mission.getId());

            response.setMissionNumero(
                    mission.getNumero());

            response.setMissionIntitule(
                    mission.getIntitule());

            response.setDocumentsCompletes(
                    documentsCompletes);

            response.setDocumentsAttendus(
                    DOCUMENTS_ATTENDUS);

            response.setPourcentage(
                    pourcentage);

            response.setTdr(tdr);

            response.setProgramme(programme);

            response.setTests(tests);

            response.setConstats(constats);

            response.setRapport(rapport);

            response.setPvAffirmation(
                    pvAffirmation);

            response.setPvCloture(
                    pvCloture);

            responses.add(response);
        }

        return responses;
    }

    private Set<Long> getMissionsAvecTdr() {

        List<Tdr> tdrs =
                tdrRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (Tdr tdr : tdrs) {

            if (tdr.getMission() != null
                    && tdr.getMission().getId() != null) {

                missionIds.add(
                        tdr.getMission().getId());
            }
        }

        return missionIds;
    }

    private Set<Long> getMissionsAvecProgramme() {

        List<com.audit.audit_document.domain.entity.ProgrammeTravail>
                programmes =
                programmeTravailRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (com.audit.audit_document.domain.entity.ProgrammeTravail programme
                : programmes) {

            if (programme.getMission() != null
                    && programme.getMission().getId() != null) {

                missionIds.add(
                        programme.getMission().getId());
            }
        }

        return missionIds;
    }

    private Set<Long> getMissionsAvecTest() {

        List<Test> tests =
                testRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (Test test : tests) {

            if (test.getLigneProgramme() == null) {
                continue;
            }

            LigneProgramme ligneProgramme =
                    test.getLigneProgramme();

            if (ligneProgramme.getProgramme() == null) {
                continue;
            }

            if (ligneProgramme.getProgramme().getMission()
                    == null) {
                continue;
            }

            if (ligneProgramme.getProgramme()
                    .getMission().getId() == null) {
                continue;
            }

            missionIds.add(
                    ligneProgramme.getProgramme()
                            .getMission().getId());
        }

        return missionIds;
    }

    private Set<Long> getMissionsAvecConstat() {

        List<Constat> constats =
                constatRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (Constat constat : constats) {

            if (constat.getTest() == null) {
                continue;
            }

            Test test = constat.getTest();

            if (test.getLigneProgramme() == null) {
                continue;
            }

            LigneProgramme ligneProgramme =
                    test.getLigneProgramme();

            if (ligneProgramme.getProgramme() == null) {
                continue;
            }

            if (ligneProgramme.getProgramme().getMission()
                    == null) {
                continue;
            }

            if (ligneProgramme.getProgramme()
                    .getMission().getId() == null) {
                continue;
            }

            missionIds.add(
                    ligneProgramme.getProgramme()
                            .getMission().getId());
        }

        return missionIds;
    }

    private Set<Long> getMissionsAvecRapport() {

        List<RapportAudit> rapports =
                rapportAuditRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (RapportAudit rapport : rapports) {

            if (rapport.getMission() != null
                    && rapport.getMission().getId() != null) {

                missionIds.add(
                        rapport.getMission().getId());
            }
        }

        return missionIds;
    }

    private Set<Long> getMissionsAvecPvAffirmation() {

    Set<Long> missionIds =
            new HashSet<>();

    List<Mission> missions =
            missionRepository.findAll();

    for (Mission mission : missions) {

        if (mission.getId() == null) {
            continue;
        }

        if (pvAffirmationRepository
                .findByMissionId(mission.getId())
                .isPresent()) {

            missionIds.add(
                    mission.getId());
        }
    }

    return missionIds;
}

    private Set<Long> getMissionsAvecPvCloture() {

        List<Reunion> reunions =
                reunionRepository.findAll();

        Set<Long> missionIds =
                new HashSet<>();

        for (Reunion reunion : reunions) {

            if (!"CLOTURE".equals(
                    reunion.getType())) {
                continue;
            }

            if (reunion.getMission() == null) {
                continue;
            }

            if (reunion.getMission().getId() == null) {
                continue;
            }

            missionIds.add(
                    reunion.getMission().getId());
        }

        return missionIds;
    }
}