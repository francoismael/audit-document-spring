package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ObjectifRequest;
import com.audit.audit_document.application.dto.ReunionPersonneRequest;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.MissionRole;
import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Checks shared by the business documents of a mission (TDR, declarations, meetings),
 * so that the user gets a clear message instead of a database error.
 */
@Component
public class MissionDocumentRules {

    private static final String ENTITE_AUDITEE = "ENTITE_AUDITEE";

    private static final String EQUIPE_DAI = "EQUIPE_DAI";

    private final MissionPersonneRepository missionPersonneRepository;

    private final DeclarationIndependanceRepository declarationRepository;

    public MissionDocumentRules(
            MissionPersonneRepository missionPersonneRepository,
            DeclarationIndependanceRepository declarationRepository) {

        this.missionPersonneRepository = missionPersonneRepository;
        this.declarationRepository = declarationRepository;
    }

    public void requireMission(Long missionId) {

        if (missionId == null) {
            throw new IllegalArgumentException(
                    "La mission est obligatoire.");
        }
    }

    public void checkObjectifs(List<ObjectifRequest> objectifs) {

        if (objectifs == null) {
            return;
        }

        for (ObjectifRequest objectif : objectifs) {

            if (objectif == null
                    || objectif.getDescriptions() == null
                    || objectif.getDescriptions().trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "La description de chaque objectif est obligatoire.");
            }

            objectif.setDescriptions(objectif.getDescriptions().trim());
        }
    }

    /*
     * A declaration is signed by a member of the audit team (not by a point focal),
     * once per mission.
     */
    public void checkDeclaration(
            Long missionId,
            Long personneId,
            LocalDate dateDeclaration,
            Long excludedDeclarationId) {

        requireMission(missionId);

        if (personneId == null) {
            throw new IllegalArgumentException(
                    "La personne qui déclare est obligatoire.");
        }

        if (dateDeclaration == null) {
            throw new IllegalArgumentException(
                    "La date de la déclaration est obligatoire.");
        }

        boolean auditor = missionPersonneRepository
                .findByMissionId(missionId)
                .stream()
                .anyMatch(member ->
                        member.getPersonne().getId().equals(personneId)
                                && !MissionRole.POINT_FOCAL.name().equals(member.getRoles()));

        if (!auditor) {
            throw new IllegalArgumentException(
                    "La personne doit faire partie de l'équipe d'audit de la mission.");
        }

        boolean alreadyDeclared = declarationRepository
                .findAll()
                .stream()
                .anyMatch(declaration ->
                        declaration.getMission().getId().equals(missionId)
                                && declaration.getPersonne().getId().equals(personneId)
                                && !declaration.getId().equals(excludedDeclarationId));

        if (alreadyDeclared) {
            throw new ConflictException(
                    "Cette personne a déjà une déclaration d'indépendance pour cette mission.");
        }
    }

    /*
     * Meeting participants come from the mission team:
     * the audited entity is represented by its points focaux, the DAI by the other members.
     */
    public void checkReunionParticipants(
            Long missionId,
            List<ReunionPersonneRequest> participants) {

        if (participants == null) {
            return;
        }

        List<MissionPersonne> team =
                missionPersonneRepository.findByMissionId(missionId);

        Set<Long> seen = new HashSet<>();

        for (ReunionPersonneRequest participant : participants) {

            if (participant == null || participant.getPersonneId() == null) {
                throw new IllegalArgumentException(
                        "Chaque participant doit avoir une personne.");
            }

            String type = participant.getTypeParticipant();

            if (!ENTITE_AUDITEE.equals(type) && !EQUIPE_DAI.equals(type)) {
                throw new IllegalArgumentException(
                        "Le type de participant doit être ENTITE_AUDITEE ou EQUIPE_DAI.");
            }

            if (!seen.add(participant.getPersonneId())) {
                throw new IllegalArgumentException(
                        "Une même personne ne peut pas être ajoutée deux fois aux participants.");
            }

            boolean pointFocal = ENTITE_AUDITEE.equals(type);

            boolean allowed = team
                    .stream()
                    .anyMatch(member ->
                            member.getPersonne().getId().equals(participant.getPersonneId())
                                    && MissionRole.POINT_FOCAL.name().equals(member.getRoles()) == pointFocal);

            if (!allowed) {
                throw new IllegalArgumentException(
                        pointFocal
                                ? "Un représentant de l'entité auditée doit être un point focal de la mission."
                                : "Un membre de l'équipe DAI doit faire partie de l'équipe d'audit de la mission.");
            }
        }
    }
}
