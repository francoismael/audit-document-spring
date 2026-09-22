package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateProgrammeTravailRequest;
import com.audit.audit_document.application.dto.LigneProgrammeRequest;
import com.audit.audit_document.application.dto.LigneProgrammeResponse;
import com.audit.audit_document.application.dto.ProgrammeTravailResponse;
import com.audit.audit_document.application.usecases.CreateProgrammeTravailUseCase;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.ProgrammeTravail;
import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.ObjectifRepository;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CreateProgrammeTravailService
        implements CreateProgrammeTravailUseCase {

    private final ProgrammeTravailRepository programmeRepository;
    private final LigneProgrammeRepository ligneRepository;
    private final MissionRepository missionRepository;
    private final ObjectifRepository objectifRepository;

    public CreateProgrammeTravailService(
            ProgrammeTravailRepository programmeRepository,
            LigneProgrammeRepository ligneRepository,
            MissionRepository missionRepository,
            ObjectifRepository objectifRepository) {

        this.programmeRepository = programmeRepository;
        this.ligneRepository = ligneRepository;
        this.missionRepository = missionRepository;
        this.objectifRepository = objectifRepository;
    }

    @Override
    public ProgrammeTravailResponse create(
            CreateProgrammeTravailRequest request) {

        Mission mission = missionRepository.findById(request.getMissionId())
                .orElseThrow(() ->
                        new RuntimeException("Mission introuvable"));

        if (programmeRepository.existsByMissionId(mission.getId())) {
            throw new RuntimeException(
                    "Un programme de travail existe déjà pour cette mission"
            );
        }

        ProgrammeTravail programme = new ProgrammeTravail();
        programme.setMission(mission);

        programme = programmeRepository.save(programme);

        if (request.getLignes() != null) {

            for (LigneProgrammeRequest ligneRequest : request.getLignes()) {

                Objectif objectif = getObjectifValide(
                        ligneRequest.getObjectifId(),
                        mission
                );

                LigneProgramme ligne = new LigneProgramme();

                ligne.setProgramme(programme);
                ligne.setObjectif(objectif);

                ligne.setNumeroControle(
                        ligneRepository.getNextNumeroControle()
                );

                mapRequestToEntity(ligneRequest, ligne);

                ligneRepository.save(ligne);
            }
        }

        return buildResponse(programme);
    }

    private Objectif getObjectifValide(
            Long objectifId,
            Mission mission) {

        Objectif objectif = objectifRepository.findById(objectifId)
                .orElseThrow(() ->
                        new RuntimeException("Objectif introuvable"));

        Tdr tdr = objectif.getTdr();

        if (tdr == null ||
                tdr.getMission() == null ||
                !tdr.getMission().getId().equals(mission.getId())) {

            throw new RuntimeException(
                    "L'objectif n'appartient pas au TDR de cette mission"
            );
        }

        return objectif;
    }

    private void mapRequestToEntity(
            LigneProgrammeRequest request,
            LigneProgramme ligne) {

        ligne.setTacheOperation(request.getTacheOperation());
        ligne.setFaiblesseAConfirmer(request.getFaiblesseAConfirmer());
        ligne.setResponsable(request.getResponsable());
        ligne.setFrequence(request.getFrequence());
        ligne.setTypeControle(request.getTypeControle());
        ligne.setDomaineCycle(request.getDomaineCycle());
        ligne.setRisque(request.getRisque());
        ligne.setProcedureTest(request.getProcedureTest());
        ligne.setEchantillonDescription(
                request.getEchantillonDescription()
        );
        ligne.setTechniqueAudit(request.getTechniqueAudit());
        ligne.setTechniqueEchantillonnage(
                request.getTechniqueEchantillonnage()
        );
    }

    private ProgrammeTravailResponse buildResponse(
            ProgrammeTravail programme) {

        List<LigneProgramme> lignes =
                ligneRepository.findByProgrammeId(programme.getId());

        return mapToResponse(programme, lignes);
    }

    private ProgrammeTravailResponse mapToResponse(
            ProgrammeTravail programme,
            List<LigneProgramme> lignes) {

        ProgrammeTravailResponse response =
                new ProgrammeTravailResponse();

        response.setId(programme.getId());

        Mission mission = programme.getMission();

        response.setMissionId(mission.getId());
        response.setMissionNumero(mission.getNumero());
        response.setMissionIntitule(mission.getIntitule());
        response.setMissionObjet(mission.getObjet());

        if (mission.getStructure() != null) {
            response.setStructureId(
                    mission.getStructure().getId()
            );

            response.setStructureNom(
                    mission.getStructure().getNom()
            );
        }

        List<LigneProgrammeResponse> ligneResponses =
                new ArrayList<>();

        for (LigneProgramme ligne : lignes) {

            LigneProgrammeResponse ligneResponse =
                    new LigneProgrammeResponse();

            ligneResponse.setId(ligne.getId());
            ligneResponse.setNumeroControle(
                    ligne.getNumeroControle()
            );

            Objectif objectif = ligne.getObjectif();

            if (objectif != null) {
                ligneResponse.setObjectifId(
                        objectif.getId()
                );

                ligneResponse.setObjectifNumero(
                        objectif.getNumero()
                );

                ligneResponse.setObjectifDescription(
                        objectif.getDescriptions()
                );
            }

            ligneResponse.setTacheOperation(
                    ligne.getTacheOperation()
            );

            ligneResponse.setFaiblesseAConfirmer(
                    ligne.getFaiblesseAConfirmer()
            );

            ligneResponse.setResponsable(
                    ligne.getResponsable()
            );

            ligneResponse.setFrequence(
                    ligne.getFrequence()
            );

            ligneResponse.setTypeControle(
                    ligne.getTypeControle()
            );

            ligneResponse.setDomaineCycle(
                    ligne.getDomaineCycle()
            );

            ligneResponse.setRisque(
                    ligne.getRisque()
            );

            ligneResponse.setProcedureTest(
                    ligne.getProcedureTest()
            );

            ligneResponse.setEchantillonDescription(
                    ligne.getEchantillonDescription()
            );

            ligneResponse.setTechniqueAudit(
                    ligne.getTechniqueAudit()
            );

            ligneResponse.setTechniqueEchantillonnage(
                    ligne.getTechniqueEchantillonnage()
            );

            ligneResponses.add(ligneResponse);
        }

        response.setLignes(ligneResponses);

        return response;
    }
}