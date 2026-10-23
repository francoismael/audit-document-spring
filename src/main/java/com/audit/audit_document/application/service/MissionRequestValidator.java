package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionPersonneRequest;
import com.audit.audit_document.domain.entity.MissionRole;

import java.util.HashSet;
import java.util.Set;

/*
 * Checks a mission before it is created or updated,
 * so that the user gets a clear message instead of a database error.
 */
final class MissionRequestValidator {

    // Size of the intitule column.
    private static final int INTITULE_MAX_LENGTH = 500;

    private MissionRequestValidator() {
    }

    static void validate(CreateMissionRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "La requête est obligatoire.");
        }

        String intitule = request.getIntitule() == null
                ? ""
                : request.getIntitule().trim();

        if (intitule.isEmpty()) {
            throw new IllegalArgumentException(
                    "L'intitulé de la mission est obligatoire.");
        }

        if (intitule.length() > INTITULE_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "L'intitulé de la mission ne doit pas dépasser "
                            + INTITULE_MAX_LENGTH + " caractères.");
        }

        request.setIntitule(intitule);

        if (request.getStructureId() == null) {
            throw new IllegalArgumentException(
                    "La structure auditée est obligatoire.");
        }

        if (request.getDateDebut() != null
                && request.getDateFin() != null
                && request.getDateFin().isBefore(request.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin doit être postérieure ou égale à la date de début.");
        }

        if (request.getPersonnes() == null) {
            return;
        }

        Set<String> members = new HashSet<>();

        for (MissionPersonneRequest personne : request.getPersonnes()) {

            if (personne == null || personne.getPersonneId() == null) {
                throw new IllegalArgumentException(
                        "Chaque membre de l'équipe doit avoir une personne.");
            }

            MissionRole role = MissionRole.fromValue(personne.getRoles());

            if (!members.add(personne.getPersonneId() + ":" + role.name())) {
                throw new IllegalArgumentException(
                        "Une même personne ne peut pas avoir deux fois le même rôle dans la mission.");
            }
        }
    }
}
