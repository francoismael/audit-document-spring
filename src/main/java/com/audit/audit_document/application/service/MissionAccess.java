package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.MissionPersonneRequest;
import com.audit.audit_document.application.port.CurrentUserProvider;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.MissionRole;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.exception.ForbiddenException;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Which missions the connected account may see: all of them for an administrator,
 * only those whose team contains its person for a user (none if the account is not linked to a person).
 */
@Component
public class MissionAccess {

    private static final String FORBIDDEN_MISSION =
            "Accès refusé : vous ne faites pas partie de l'équipe de cette mission";

    private static final String NOT_LINKED =
            "Votre compte n'est pas encore relié à une personne : contactez l'administrateur.";

    private final CurrentUserProvider currentUserProvider;

    private final MissionPersonneRepository missionPersonneRepository;

    public MissionAccess(
            CurrentUserProvider currentUserProvider,
            MissionPersonneRepository missionPersonneRepository) {

        this.currentUserProvider = currentUserProvider;
        this.missionPersonneRepository = missionPersonneRepository;
    }

    public Utilisateur currentUser() {
        return currentUserProvider.getCurrentUser();
    }

    // Read once, then used to filter a whole list.
    public Predicate<Long> visibleMissions() {

        Utilisateur utilisateur = currentUser();

        if (utilisateur.isAdmin()) {
            return missionId -> true;
        }

        Set<Long> missionIds = utilisateur.getPersonne() == null
                ? Collections.<Long>emptySet()
                : new HashSet<>(missionPersonneRepository
                        .findMissionIdsByPersonneId(utilisateur.getPersonne().getId()));

        return missionId -> missionId != null && missionIds.contains(missionId);
    }

    // A user sees the documents he generated and those of his missions; an administrator sees them all.
    public Predicate<DocumentGenere> visibleDocuments() {

        Long utilisateurId = currentUser().getId();
        Predicate<Long> visibleMissions = visibleMissions();

        return document ->
                utilisateurId.equals(document.getUtilisateurId())
                        || visibleMissions.test(
                                document.getMission() != null ? document.getMission().getId() : null);
    }

    // Keeps the items that belong to a visible mission.
    public <T> List<T> keepVisible(List<T> items, Function<T, Long> missionIdOf) {

        Predicate<Long> visibleMissions = visibleMissions();

        return items.stream()
                .filter(item -> visibleMissions.test(missionIdOf.apply(item)))
                .collect(Collectors.toList());
    }

    public void checkAccess(Long missionId) {

        if (!visibleMissions().test(missionId)) {
            throw new ForbiddenException(FORBIDDEN_MISSION);
        }
    }

    // A user who creates a mission joins its team as an auditor, otherwise he could not see it.
    public List<MissionPersonneRequest> addCurrentUserToTeam(List<MissionPersonneRequest> members) {

        List<MissionPersonneRequest> team =
                members != null ? new ArrayList<>(members) : new ArrayList<>();

        Utilisateur utilisateur = currentUser();

        if (utilisateur.isAdmin()) {
            return team;
        }

        Long personneId = linkedPersonneId(utilisateur);

        if (!containsPersonne(team, personneId)) {

            MissionPersonneRequest member = new MissionPersonneRequest();
            member.setPersonneId(personneId);
            member.setRoles(MissionRole.AUDITEUR.name());
            team.add(member);
        }

        return team;
    }

    // A user may not take himself out of the team: he would lose the mission.
    public void checkCurrentUserStaysInTeam(List<MissionPersonneRequest> members) {

        Utilisateur utilisateur = currentUser();

        if (!utilisateur.isAdmin()
                && !containsPersonne(members, linkedPersonneId(utilisateur))) {

            throw new IllegalArgumentException(
                    "Vous ne pouvez pas vous retirer de l'équipe de la mission.");
        }
    }

    private Long linkedPersonneId(Utilisateur utilisateur) {

        if (utilisateur.getPersonne() == null) {
            throw new IllegalArgumentException(NOT_LINKED);
        }

        return utilisateur.getPersonne().getId();
    }

    private static boolean containsPersonne(List<MissionPersonneRequest> members, Long personneId) {

        return members != null
                && members.stream().anyMatch(member -> personneId.equals(member.getPersonneId()));
    }
}
