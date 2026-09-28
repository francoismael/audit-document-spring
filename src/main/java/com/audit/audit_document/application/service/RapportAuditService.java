package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateRapportAuditRequest;
import com.audit.audit_document.application.dto.RapportAuditResponse;
import com.audit.audit_document.application.usecases.CreateRapportAuditUseCase;
import com.audit.audit_document.application.usecases.GetRapportAuditByMissionUseCase;
import com.audit.audit_document.application.usecases.UpdateRapportAuditUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.RapportAudit;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.RapportAuditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RapportAuditService
        implements CreateRapportAuditUseCase,
                   GetRapportAuditByMissionUseCase,
                   UpdateRapportAuditUseCase {

    private final RapportAuditRepository rapportAuditRepository;
    private final MissionRepository missionRepository;

    public RapportAuditService(
            RapportAuditRepository rapportAuditRepository,
            MissionRepository missionRepository) {

        this.rapportAuditRepository = rapportAuditRepository;
        this.missionRepository = missionRepository;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Override
    public RapportAuditResponse create(
            CreateRapportAuditRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Les données du rapport sont obligatoires."
            );
        }

        if (request.getMissionId() == null) {
            throw new IllegalArgumentException(
                    "La mission est obligatoire."
            );
        }

        Mission mission = missionRepository
                .findById(request.getMissionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Mission introuvable avec l'id : "
                                        + request.getMissionId()
                        )
                );

        if (rapportAuditRepository
                .findByMissionId(mission.getId())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Un rapport d'audit existe déjà pour cette mission."
            );
        }

        RapportAudit rapport = new RapportAudit();

        rapport.setMission(mission);

        rapport.setMotifs(
                request.getMotifs()
        );

        rapport.setNature(
                request.getNature()
        );

        rapport.setVersionRapport(
                request.getVersionRapport() != null
                        ? request.getVersionRapport()
                        : "definitif"
        );

        rapport.setDateEmission(
                request.getDateEmission()
        );

        rapport.setProprietaireDocument(
                request.getProprietaireDocument() != null
                        ? request.getProprietaireDocument()
                        : "Direction de l’Audit Interne"
        );

        rapport.setDestinataires(
                request.getDestinataires()
        );

        rapport.setClassification(
                request.getClassification()
        );

        RapportAudit saved =
                rapportAuditRepository.save(rapport);

        return convertirEnResponse(saved);
    }

    // ============================================================
    // GET PAR MISSION
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public RapportAuditResponse getByMissionId(
            Long missionId) {

        if (missionId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la mission est obligatoire."
            );
        }

        RapportAudit rapport =
                rapportAuditRepository
                        .findByMissionId(missionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Rapport d'audit introuvable "
                                                + "pour la mission : "
                                                + missionId
                                )
                        );

        return convertirEnResponse(rapport);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public RapportAuditResponse update(
            Long id,
            CreateRapportAuditRequest request) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du rapport est obligatoire."
            );
        }

        if (request == null) {
            throw new IllegalArgumentException(
                    "Les données du rapport sont obligatoires."
            );
        }

        RapportAudit rapport =
                rapportAuditRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Rapport d'audit introuvable "
                                                + "avec l'id : "
                                                + id
                                )
                        );

        Mission mission = rapport.getMission();

        if (request.getMissionId() != null
                && !request.getMissionId().equals(mission.getId())) {

            throw new IllegalArgumentException(
                    "La mission d'un rapport d'audit "
                            + "ne peut pas être modifiée."
            );
        }

        
        rapport.setMotifs(
                request.getMotifs()
        );

        rapport.setNature(
                request.getNature()
        );

        
        if (request.getVersionRapport() != null) {

            rapport.setVersionRapport(
                    request.getVersionRapport()
            );
        }

        rapport.setDateEmission(
                request.getDateEmission()
        );

        rapport.setProprietaireDocument(
                request.getProprietaireDocument()
        );

        rapport.setDestinataires(
                request.getDestinataires()
        );

        rapport.setClassification(
                request.getClassification()
        );

        RapportAudit updated =
                rapportAuditRepository.save(rapport);

        return convertirEnResponse(updated);
    }



    private RapportAuditResponse convertirEnResponse(
            RapportAudit rapport) {

        Mission mission = rapport.getMission();

        RapportAuditResponse response =
                new RapportAuditResponse();

        response.setRapportId(
                rapport.getId()
        );

        response.setMissionId(
                mission.getId()
        );

        response.setMissionNumero(
                mission.getNumero()
        );

        response.setMissionIntitule(
                mission.getIntitule()
        );

        response.setMissionObjet(
                mission.getObjet()
        );

        response.setMissionDateSignature(
                mission.getDateSignature()
        );

        if (mission.getStructure() != null) {

            response.setStructureId(
                    mission.getStructure().getId()
            );

            response.setStructureNom(
                    mission.getStructure().getNom()
            );
        }

        response.setTitre(
                "RAPPORT D’AUDIT SUR "
                        + mission.getObjet()
        );

        response.setMotifs(
                rapport.getMotifs()
        );

        response.setNature(
                rapport.getNature()
        );

        response.setReferenceOm(
                mission.getNumero()
        );

        response.setDateOm(
                mission.getDateSignature()
        );

        response.setVersionRapport(
                rapport.getVersionRapport()
        );

        response.setDateEmission(
                rapport.getDateEmission()
        );

        response.setProprietaireDocument(
                rapport.getProprietaireDocument()
        );

        response.setDestinataires(
                rapport.getDestinataires()
        );

        response.setClassification(
                rapport.getClassification()
        );

        return response;
    }
}