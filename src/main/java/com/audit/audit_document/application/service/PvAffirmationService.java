package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreatePvAffirmationRequest;
import com.audit.audit_document.application.dto.PvAffirmationLigneRequest;
import com.audit.audit_document.application.dto.PvAffirmationLigneResponse;
import com.audit.audit_document.application.dto.PvAffirmationParticipantRequest;
import com.audit.audit_document.application.dto.PvAffirmationParticipantResponse;
import com.audit.audit_document.application.dto.PvAffirmationResponse;

import com.audit.audit_document.application.usecases.CreatePvAffirmationUseCase;
import com.audit.audit_document.application.usecases.DeletePvAffirmationUseCase;
import com.audit.audit_document.application.usecases.GetPvAffirmationByIdUseCase;
import com.audit.audit_document.application.usecases.GetPvAffirmationByMissionUseCase;
import com.audit.audit_document.application.usecases.UpdatePvAffirmationUseCase;

import com.audit.audit_document.domain.entity.Constat;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.entity.PvAffirmation;
import com.audit.audit_document.domain.entity.PvAffirmationLigne;
import com.audit.audit_document.domain.entity.PvAffirmationParticipant;

import com.audit.audit_document.domain.repository.ConstatRepository;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.PvAffirmationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PvAffirmationService
        implements CreatePvAffirmationUseCase,
                   GetPvAffirmationByIdUseCase,
                   GetPvAffirmationByMissionUseCase,
                   UpdatePvAffirmationUseCase,
                   DeletePvAffirmationUseCase {

    private final PvAffirmationRepository pvRepository;
    private final MissionRepository missionRepository;
    private final MissionPersonneRepository missionPersonneRepository;
    private final ConstatRepository constatRepository;

    public PvAffirmationService(
            PvAffirmationRepository pvRepository,
            MissionRepository missionRepository,
            MissionPersonneRepository missionPersonneRepository,
            ConstatRepository constatRepository) {

        this.pvRepository = pvRepository;
        this.missionRepository = missionRepository;
        this.missionPersonneRepository =
                missionPersonneRepository;
        this.constatRepository = constatRepository;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Override
    public PvAffirmationResponse create(
            CreatePvAffirmationRequest request) {

        validateRequest(request);

        Mission mission =
                trouverMission(request.getMissionId());

        if (pvRepository
                .findByMissionId(mission.getId())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Un procès-verbal d'affirmation "
                            + "existe déjà pour cette mission."
            );
        }

        PvAffirmation pv =
                new PvAffirmation();

        pv.setMission(mission);
        pv.setDateEntretien(
                request.getDateEntretien()
        );
        pv.setLieu(
                request.getLieu()
        );
        pv.setObservationsComplementaires(
                request.getObservationsComplementaires()
        );

        ajouterParticipants(
                pv,
                mission,
                request.getParticipants()
        );

        ajouterLignes(
                pv,
                mission,
                request.getLignes()
        );

        PvAffirmation saved =
                pvRepository.save(pv);

        return toResponse(saved);
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public PvAffirmationResponse getById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du PV est obligatoire."
            );
        }

        PvAffirmation pv =
                pvRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Procès-verbal d'affirmation "
                                                + "introuvable avec l'id : "
                                                + id
                                )
                        );

        return toResponse(pv);
    }

    // ============================================================
    // GET BY MISSION
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public PvAffirmationResponse getByMissionId(
            Long missionId) {

        if (missionId == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la mission "
                            + "est obligatoire."
            );
        }

        PvAffirmation pv =
                pvRepository
                        .findByMissionId(missionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Procès-verbal d'affirmation "
                                                + "introuvable pour la mission : "
                                                + missionId
                                )
                        );

        return toResponse(pv);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public PvAffirmationResponse update(
            Long id,
            CreatePvAffirmationRequest request) {

        validateRequest(request);

        PvAffirmation pv =
                pvRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Procès-verbal d'affirmation "
                                                + "introuvable avec l'id : "
                                                + id
                                )
                        );

        Mission mission =
                pv.getMission();

        if (request.getMissionId() != null
                && !request.getMissionId()
                .equals(mission.getId())) {

            throw new IllegalArgumentException(
                    "La mission d'un procès-verbal "
                            + "d'affirmation ne peut pas être modifiée."
            );
        }

        pv.setDateEntretien(
                request.getDateEntretien()
        );

        pv.setLieu(
                request.getLieu()
        );

        pv.setObservationsComplementaires(
                request.getObservationsComplementaires()
        );

        mettreAJourParticipants(
                pv,
                mission,
                request.getParticipants()
        );

        mettreAJourLignes(
                pv,
                mission,
                request.getLignes()
        );

        PvAffirmation updated =
                pvRepository.save(pv);

        return toResponse(updated);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void delete(Long id) {

        PvAffirmation pv =
                pvRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Procès-verbal d'affirmation "
                                                + "introuvable avec l'id : "
                                                + id
                                )
                        );

        pvRepository.deleteById(
                pv.getId()
        );
    }

    // ============================================================
    // VALIDATION
    // ============================================================

    private void validateRequest(
            CreatePvAffirmationRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Les données du procès-verbal "
                            + "sont obligatoires."
            );
        }

        if (request.getMissionId() == null) {
            throw new IllegalArgumentException(
                    "La mission est obligatoire."
            );
        }

        if (request.getDateEntretien() == null) {
            throw new IllegalArgumentException(
                    "La date d'entretien est obligatoire."
            );
        }

        if (request.getParticipants() == null
                || request.getParticipants().isEmpty()) {

            throw new IllegalArgumentException(
                    "Au moins un participant est obligatoire."
            );
        }

        if (request.getLignes() == null
                || request.getLignes().isEmpty()) {

            throw new IllegalArgumentException(
                    "Au moins une ligne de constat "
                            + "est obligatoire."
            );
        }
    }

    // ============================================================
    // MISSION
    // ============================================================

    private Mission trouverMission(Long id) {

        return missionRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Mission introuvable avec l'id : "
                                        + id
                        )
                );
    }

    // ============================================================
    // PARTICIPANTS CREATE
    // ============================================================

    private void ajouterParticipants(
            PvAffirmation pv,
            Mission mission,
            List<PvAffirmationParticipantRequest>
                    requests) {

        Set<Long> missionPersonneIds =
                new HashSet<Long>();

        for (PvAffirmationParticipantRequest request
                : requests) {

            if (request == null) {
                continue;
            }

            if (request.getMissionPersonneId() == null) {
                throw new IllegalArgumentException(
                        "La personne participante est obligatoire."
                );
            }

            String type =
                    normaliserTypeParticipant(
                            request.getTypeParticipant()
                    );

            if (!missionPersonneIds.add(
                    request.getMissionPersonneId()
            )) {

                throw new IllegalArgumentException(
                        "Une même personne ne peut pas "
                                + "être ajoutée deux fois."
                );
            }

            MissionPersonne missionPersonne =
                    trouverMissionPersonne(
                            request.getMissionPersonneId()
                    );

            verifierMissionPersonne(
                    missionPersonne,
                    mission
            );

            PvAffirmationParticipant participant =
                    new PvAffirmationParticipant();

            participant.setMissionPersonne(
                    missionPersonne
            );

            participant.setTypeParticipant(
                    type
            );

            pv.addParticipant(
                    participant
            );
        }
    }

    // ============================================================
    // PARTICIPANTS UPDATE
    // ============================================================

    private void mettreAJourParticipants(
            PvAffirmation pv,
            Mission mission,
            List<PvAffirmationParticipantRequest>
                    requests) {

        pv.getParticipants().clear();

        ajouterParticipants(
                pv,
                mission,
                requests
        );
    }

    // ============================================================
    // LIGNES CREATE
    // ============================================================

    private void ajouterLignes(
            PvAffirmation pv,
            Mission mission,
            List<PvAffirmationLigneRequest>
                    requests) {

        Set<Long> constatIds =
                new HashSet<Long>();

        for (PvAffirmationLigneRequest request
                : requests) {

            if (request == null) {
                continue;
            }

            if (request.getConstatId() == null) {
                throw new IllegalArgumentException(
                        "Le constat est obligatoire."
                );
            }

            if (request.getQuestion() == null
                    || request.getQuestion()
                    .trim()
                    .isEmpty()) {

                throw new IllegalArgumentException(
                        "La question est obligatoire."
                );
            }

            if (!constatIds.add(
                    request.getConstatId()
            )) {

                throw new IllegalArgumentException(
                        "Un même constat ne peut pas "
                                + "être ajouté deux fois "
                                + "dans le même PV."
                );
            }

            Constat constat =
                    trouverConstat(
                            request.getConstatId()
                    );

            verifierConstatMission(
                    constat,
                    mission
            );

            PvAffirmationLigne ligne =
                    new PvAffirmationLigne();

            ligne.setConstat(
                    constat
            );

            ligne.setQuestion(
                    request.getQuestion()
            );

            ligne.setReponseEntite(
                    request.getReponseEntite()
            );

            ligne.setPiecesJustificatives(
                    request.getPiecesJustificatives()
            );

            ligne.setCommentairesAuditeurs(
                    request.getCommentairesAuditeurs()
            );

            pv.addLigne(ligne);
        }
    }

    // ============================================================
    // LIGNES UPDATE
    // ============================================================

    private void mettreAJourLignes(
            PvAffirmation pv,
            Mission mission,
            List<PvAffirmationLigneRequest>
                    requests) {

        pv.getLignes().clear();

        ajouterLignes(
                pv,
                mission,
                requests
        );
    }

    // ============================================================
    // MISSION PERSONNE
    // ============================================================

    private MissionPersonne trouverMissionPersonne(
            Long id) {

        return missionPersonneRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "MissionPersonne introuvable "
                                        + "avec l'id : "
                                        + id
                        )
                );
    }

    private void verifierMissionPersonne(
            MissionPersonne missionPersonne,
            Mission mission) {

        if (missionPersonne.getMission() == null
                || missionPersonne.getMission().getId() == null
                || !missionPersonne.getMission().getId()
                .equals(mission.getId())) {

            throw new IllegalArgumentException(
                    "La personne sélectionnée "
                            + "n'appartient pas à cette mission."
            );
        }
    }

    // ============================================================
    // CONSTAT
    // ============================================================

    private Constat trouverConstat(Long id) {

        return constatRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Constat introuvable avec l'id : "
                                        + id
                        )
                );
    }

    private void verifierConstatMission(
            Constat constat,
            Mission mission) {

        if (constat.getTest() == null
                || constat.getTest()
                .getLigneProgramme() == null
                || constat.getTest()
                .getLigneProgramme()
                .getProgramme() == null
                || constat.getTest()
                .getLigneProgramme()
                .getProgramme()
                .getMission() == null) {

            throw new IllegalArgumentException(
                    "Impossible de déterminer "
                            + "la mission du constat."
            );
        }

        Long constatMissionId =
                constat.getTest()
                        .getLigneProgramme()
                        .getProgramme()
                        .getMission()
                        .getId();

        if (!mission.getId().equals(
                constatMissionId
        )) {

            throw new IllegalArgumentException(
                    "Le constat sélectionné "
                            + "n'appartient pas à cette mission."
            );
        }
    }

    // ============================================================
    // TYPE PARTICIPANT
    // ============================================================

    private String normaliserTypeParticipant(
            String type) {

        if (type == null) {
            throw new IllegalArgumentException(
                    "Le type du participant est obligatoire."
            );
        }

        String valeur =
                type.trim().toUpperCase();

        if (!"AUDITEUR".equals(valeur)
                && !"ENTITE_AUDITEE".equals(valeur)) {

            throw new IllegalArgumentException(
                    "Le type de participant doit être "
                            + "AUDITEUR ou ENTITE_AUDITEE."
            );
        }

        return valeur;
    }

    // ============================================================
    // RESPONSE
    // ============================================================

    private PvAffirmationResponse toResponse(
            PvAffirmation pv) {

        PvAffirmationResponse response =
                new PvAffirmationResponse();

        Mission mission =
                pv.getMission();

        response.setId(
                pv.getId()
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

        if (mission.getStructure() != null) {

            response.setStructureNom(
                    mission.getStructure().getNom()
            );
        }

        response.setDateEntretien(
                pv.getDateEntretien()
        );

        response.setLieu(
                pv.getLieu()
        );

        response.setObservationsComplementaires(
                pv.getObservationsComplementaires()
        );

        response.setParticipants(
                pv.getParticipants()
                        .stream()
                        .map(
                            this::toParticipantResponse
                        )
                        .collect(
                            Collectors.toList()
                        )
        );

        response.setLignes(
                pv.getLignes()
                        .stream()
                        .map(
                            this::toLigneResponse
                        )
                        .collect(
                            Collectors.toList()
                        )
        );

        return response;
    }

    private PvAffirmationParticipantResponse
    toParticipantResponse(
            PvAffirmationParticipant participant) {

        PvAffirmationParticipantResponse response =
                new PvAffirmationParticipantResponse();

        MissionPersonne missionPersonne =
                participant.getMissionPersonne();

        response.setId(
                participant.getId()
        );

        response.setMissionPersonneId(
                missionPersonne.getId()
        );

        if (missionPersonne.getPersonne() != null) {

            response.setPersonneId(
                    missionPersonne.getPersonne().getId()
            );

            response.setNom(
                    missionPersonne
                            .getPersonne()
                            .getNom()
            );

            response.setPrenom(
                    missionPersonne
                            .getPersonne()
                            .getPrenom()
            );

            response.setFonction(
                    missionPersonne
                            .getPersonne()
                            .getFonction()
            );
        }

        response.setRoles(
                missionPersonne.getRoles()
        );

        response.setTypeParticipant(
                participant.getTypeParticipant()
        );

        return response;
    }

    private PvAffirmationLigneResponse
    toLigneResponse(
            PvAffirmationLigne ligne) {

        PvAffirmationLigneResponse response =
                new PvAffirmationLigneResponse();

        response.setId(
                ligne.getId()
        );

        response.setConstatId(
                ligne.getConstat().getId()
        );

        response.setReferenceConstat(
                ligne.getConstat().getReference()
        );

        response.setQuestion(
                ligne.getQuestion()
        );

        response.setReponseEntite(
                ligne.getReponseEntite()
        );

        response.setPiecesJustificatives(
                ligne.getPiecesJustificatives()
        );

        response.setCommentairesAuditeurs(
                ligne.getCommentairesAuditeurs()
        );

        return response;
    }
}