package com.audit.audit_document.domain.entity;

import java.util.Arrays;

public enum MissionRole {

    DIRECTEUR_AUDIT_INTERNE,
    CHEF_MISSION,
    SUPERVISEUR,
    AUDITEUR,
    POINT_FOCAL;

    public static MissionRole fromValue(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Le rôle de la personne dans la mission est obligatoire."
            );
        }

        try {
            return valueOf(value.trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException(
                    "Rôle invalide : " + value
                            + ". Valeurs acceptées : "
                            + Arrays.toString(values())
            );
        }
    }
}
