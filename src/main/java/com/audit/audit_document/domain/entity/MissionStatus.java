package com.audit.audit_document.domain.entity;

import java.time.LocalDate;

/*
 * State of a mission from its dates, compared with today.
 * Same rule in the statistics, the search of the missions and the front.
 */
public enum MissionStatus {

    A_VENIR,
    EN_COURS,
    TERMINEE,
    SANS_DATES;

    public static MissionStatus of(Mission mission, LocalDate today) {

        LocalDate start = mission.getDateDebut();
        LocalDate end = mission.getDateFin();

        if (end != null && end.isBefore(today)) {
            return TERMINEE;
        }

        if (start != null && start.isAfter(today)) {
            return A_VENIR;
        }

        return start != null ? EN_COURS : SANS_DATES;
    }
}
