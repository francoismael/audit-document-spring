package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ProgrammeTravailResponse;
import com.audit.audit_document.application.usecases.GetAllProgrammesTravailUseCase;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.ProgrammeTravail;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetAllProgrammesTravailService
        implements GetAllProgrammesTravailUseCase {

    private final ProgrammeTravailRepository programmeRepository;
    private final LigneProgrammeRepository ligneRepository;

    public GetAllProgrammesTravailService(
            ProgrammeTravailRepository programmeRepository,
            LigneProgrammeRepository ligneRepository) {

        this.programmeRepository = programmeRepository;
        this.ligneRepository = ligneRepository;
    }

    @Override
    public List<ProgrammeTravailResponse> getAll() {

        List<ProgrammeTravail> programmes =
                programmeRepository.findAll();

        List<ProgrammeTravailResponse> responses =
                new ArrayList<>();

        for (ProgrammeTravail programme : programmes) {

            List<LigneProgramme> lignes =
                    ligneRepository.findByProgrammeId(
                            programme.getId()
                    );

            responses.add(
                    mapToResponse(programme, lignes)
            );
        }

        return responses;
    }

    private ProgrammeTravailResponse mapToResponse(
            ProgrammeTravail programme,
            List<LigneProgramme> lignes) {

        ProgrammeTravailResponse response =
                new ProgrammeTravailResponse();

        response.setId(programme.getId());

        if (programme.getMission() != null) {

            response.setMissionId(
                    programme.getMission().getId()
            );

            response.setMissionNumero(
                    programme.getMission().getNumero()
            );

            response.setMissionIntitule(
                    programme.getMission().getIntitule()
            );

            response.setMissionObjet(
                    programme.getMission().getObjet()
            );

            if (programme.getMission().getStructure() != null) {

                response.setStructureId(
                        programme.getMission()
                                .getStructure()
                                .getId()
                );

                response.setStructureNom(
                        programme.getMission()
                                .getStructure()
                                .getNom()
                );
            }
        }

        List<com.audit.audit_document.application.dto.LigneProgrammeResponse>
                ligneResponses = new ArrayList<>();

        for (LigneProgramme ligne : lignes) {

            com.audit.audit_document.application.dto.LigneProgrammeResponse
                    ligneResponse =
                    new com.audit.audit_document.application.dto.LigneProgrammeResponse();

            ligneResponse.setId(ligne.getId());
            ligneResponse.setNumeroControle(
                    ligne.getNumeroControle()
            );

            if (ligne.getObjectif() != null) {

                ligneResponse.setObjectifId(
                        ligne.getObjectif().getId()
                );

                ligneResponse.setObjectifNumero(
                        ligne.getObjectif().getNumero()
                );

                ligneResponse.setObjectifDescription(
                        ligne.getObjectif().getDescriptions()
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