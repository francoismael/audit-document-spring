package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.LigneProgrammeResponse;
import com.audit.audit_document.application.dto.ProgrammeTravailResponse;
import com.audit.audit_document.application.usecases.GetProgrammeTravailByIdUseCase;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.ProgrammeTravail;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetProgrammeTravailByIdService
        implements GetProgrammeTravailByIdUseCase {

    private final ProgrammeTravailRepository programmeRepository;
    private final LigneProgrammeRepository ligneRepository;

    public GetProgrammeTravailByIdService(
            ProgrammeTravailRepository programmeRepository,
            LigneProgrammeRepository ligneRepository) {

        this.programmeRepository = programmeRepository;
        this.ligneRepository = ligneRepository;
    }

    @Override
    public ProgrammeTravailResponse getById(Long id) {

        ProgrammeTravail programme =
                programmeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Programme de travail introuvable"
                                )
                        );

        List<LigneProgramme> lignes =
                ligneRepository.findByProgrammeId(id);

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