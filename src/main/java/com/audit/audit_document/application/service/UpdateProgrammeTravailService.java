package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateProgrammeTravailRequest;
import com.audit.audit_document.application.dto.LigneProgrammeRequest;
import com.audit.audit_document.application.dto.LigneProgrammeResponse;
import com.audit.audit_document.application.dto.ProgrammeTravailResponse;
import com.audit.audit_document.application.usecases.UpdateProgrammeTravailUseCase;
import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.entity.ProgrammeTravail;
import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.domain.repository.ObjectifRepository;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UpdateProgrammeTravailService
        implements UpdateProgrammeTravailUseCase {

    private final ProgrammeTravailRepository programmeRepository;
    private final LigneProgrammeRepository ligneRepository;
    private final ObjectifRepository objectifRepository;

    public UpdateProgrammeTravailService(
            ProgrammeTravailRepository programmeRepository,
            LigneProgrammeRepository ligneRepository,
            ObjectifRepository objectifRepository) {

        this.programmeRepository = programmeRepository;
        this.ligneRepository = ligneRepository;
        this.objectifRepository = objectifRepository;
    }

    @Override
    public ProgrammeTravailResponse update(
            Long id,
            CreateProgrammeTravailRequest request) {

        ProgrammeTravail programme =
                programmeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Programme de travail introuvable"
                                )
                        );

        Mission mission = programme.getMission();

        if (request.getMissionId() != null &&
                !request.getMissionId().equals(mission.getId())) {

            throw new RuntimeException(
                    "La mission d'un programme de travail ne peut pas être modifiée"
            );
        }

        List<LigneProgramme> existingLines =
                ligneRepository.findByProgrammeId(programme.getId());

        Set<Long> requestLineIds = new HashSet<>();

        if (request.getLignes() != null) {

            for (LigneProgrammeRequest ligneRequest :
                    request.getLignes()) {

                LigneProgramme ligne;

                if (ligneRequest.getId() != null) {

                    requestLineIds.add(ligneRequest.getId());

                    ligne = existingLines.stream()
                            .filter(existing ->
                                    existing.getId()
                                            .equals(ligneRequest.getId())
                            )
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "La ligne du programme avec l'id "
                                                    + ligneRequest.getId()
                                                    + " n'appartient pas à ce programme"
                                    )
                            );

                } else {

                    ligne = new LigneProgramme();

                    ligne.setProgramme(programme);

                    ligne.setNumeroControle(
                            ligneRepository.getNextNumeroControle()
                    );
                }

                Objectif objectif =
                        getObjectifValide(
                                ligneRequest.getObjectifId(),
                                mission
                        );

                ligne.setObjectif(objectif);

                mapRequestToEntity(
                        ligneRequest,
                        ligne
                );

                ligneRepository.save(ligne);
            }
        }

        /*
         * Supprime les anciennes lignes qui
         * ne sont plus présentes dans la requête.
         */
        for (LigneProgramme existing : existingLines) {

    if (!requestLineIds.contains(existing.getId())) {
        ligneRepository.deleteById(existing.getId());
    }
}

        return buildResponse(programme);
    }

    private Objectif getObjectifValide(
            Long objectifId,
            Mission mission) {

        if (objectifId == null) {
            throw new RuntimeException(
                    "L'objectif est obligatoire"
            );
        }

        Objectif objectif =
                objectifRepository.findById(objectifId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Objectif introuvable"
                                )
                        );

        Tdr tdr = objectif.getTdr();

        if (tdr == null ||
                tdr.getMission() == null ||
                !tdr.getMission().getId()
                        .equals(mission.getId())) {

            throw new RuntimeException(
                    "L'objectif n'appartient pas au TDR de cette mission"
            );
        }

        return objectif;
    }

    private void mapRequestToEntity(
            LigneProgrammeRequest request,
            LigneProgramme ligne) {

        ligne.setTacheOperation(
                request.getTacheOperation()
        );

        ligne.setFaiblesseAConfirmer(
                request.getFaiblesseAConfirmer()
        );

        ligne.setResponsable(
                request.getResponsable()
        );

        ligne.setFrequence(
                request.getFrequence()
        );

        ligne.setTypeControle(
                request.getTypeControle()
        );

        ligne.setDomaineCycle(
                request.getDomaineCycle()
        );

        ligne.setRisque(
                request.getRisque()
        );

        ligne.setProcedureTest(
                request.getProcedureTest()
        );

        ligne.setEchantillonDescription(
                request.getEchantillonDescription()
        );

        ligne.setTechniqueAudit(
                request.getTechniqueAudit()
        );

        ligne.setTechniqueEchantillonnage(
                request.getTechniqueEchantillonnage()
        );
    }

    private ProgrammeTravailResponse buildResponse(
            ProgrammeTravail programme) {

        List<LigneProgramme> lignes =
                ligneRepository.findByProgrammeId(
                        programme.getId()
                );

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

            LigneProgrammeResponse responseLigne =
                    new LigneProgrammeResponse();

            responseLigne.setId(ligne.getId());

            responseLigne.setNumeroControle(
                    ligne.getNumeroControle()
            );

            if (ligne.getObjectif() != null) {

                responseLigne.setObjectifId(
                        ligne.getObjectif().getId()
                );

                responseLigne.setObjectifNumero(
                        ligne.getObjectif().getNumero()
                );

                responseLigne.setObjectifDescription(
                        ligne.getObjectif().getDescriptions()
                );
            }

            responseLigne.setTacheOperation(
                    ligne.getTacheOperation()
            );

            responseLigne.setFaiblesseAConfirmer(
                    ligne.getFaiblesseAConfirmer()
            );

            responseLigne.setResponsable(
                    ligne.getResponsable()
            );

            responseLigne.setFrequence(
                    ligne.getFrequence()
            );

            responseLigne.setTypeControle(
                    ligne.getTypeControle()
            );

            responseLigne.setDomaineCycle(
                    ligne.getDomaineCycle()
            );

            responseLigne.setRisque(
                    ligne.getRisque()
            );

            responseLigne.setProcedureTest(
                    ligne.getProcedureTest()
            );

            responseLigne.setEchantillonDescription(
                    ligne.getEchantillonDescription()
            );

            responseLigne.setTechniqueAudit(
                    ligne.getTechniqueAudit()
            );

            responseLigne.setTechniqueEchantillonnage(
                    ligne.getTechniqueEchantillonnage()
            );

            ligneResponses.add(responseLigne);
        }

        response.setLignes(ligneResponses);

        return response;
    }
}