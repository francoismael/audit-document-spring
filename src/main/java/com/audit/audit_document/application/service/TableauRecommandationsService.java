package com.audit.audit_document.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.RecommandationRegroupeeResponse;
import com.audit.audit_document.application.dto.TableauRecommandationsResponse;
import com.audit.audit_document.application.usecases.GetTableauRecommandationsUseCase;
import com.audit.audit_document.domain.entity.Constat;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.Recommandation;
import com.audit.audit_document.domain.entity.Reponse;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.ConstatRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.audit.audit_document.domain.repository.ReponseRepository;

@Service
@Transactional
public class TableauRecommandationsService
        implements GetTableauRecommandationsUseCase {

    private final ConstatRepository constatRepository;
    private final MissionRepository missionRepository;
    private final ReponseRepository reponseRepository;

    public TableauRecommandationsService(
            ConstatRepository constatRepository,
            MissionRepository missionRepository,
            ReponseRepository reponseRepository) {

        this.constatRepository = constatRepository;
        this.missionRepository = missionRepository;
        this.reponseRepository = reponseRepository;
    }

    @Override
    public TableauRecommandationsResponse getByMissionId(
            Long missionId) {

        if (missionId == null) {

            throw new IllegalArgumentException(
                    "La mission est obligatoire."
            );
        }

        Mission mission =
                missionRepository
                        .findById(missionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Mission introuvable avec l'id : "
                                                + missionId
                                )
                        );

        TableauRecommandationsResponse response =
                new TableauRecommandationsResponse();

        response.setMissionId(
                mission.getId()
        );

        response.setMissionIntitule(
                mission.getIntitule()
        );

        // =====================================================
        // STRUCTURE AUDITÉE
        // =====================================================

        Structure structure =
                mission.getStructure();

        if (structure != null) {

            response.setStructureId(
                    structure.getId()
            );

            response.setStructureNom(
                    structure.getNom()
            );
        }

        // =====================================================
        // CONSTATS DE LA MISSION
        // =====================================================

        List<Constat> constats =
                constatRepository.findAll()
                        .stream()
                        .filter(constat ->
                                appartientAMission(
                                        constat,
                                        missionId
                                )
                        )
                        .collect(Collectors.toList());

        List<RecommandationRegroupeeResponse> lignes =
                new ArrayList<>();

        // =====================================================
        // PARCOURIR LES CONSTATS
        // =====================================================

        for (Constat constat : constats) {

            if (constat.getRecommandations() == null) {
                continue;
            }

            // =================================================
            // GARDER UNIQUEMENT LA RECOMMANDATION RETENUE
            // =================================================

            for (Recommandation recommandation :
                    constat.getRecommandations()) {

                if (!Boolean.TRUE.equals(
                        recommandation.getRetenue())) {

                    continue;
                }

                RecommandationRegroupeeResponse ligne =
                        construireLigne(
                                constat,
                                recommandation
                        );

                lignes.add(ligne);
            }
        }

        response.setLignes(lignes);

        return response;
    }



    private boolean appartientAMission(
            Constat constat,
            Long missionId) {

        if (constat == null
                || constat.getTest() == null) {

            return false;
        }

        if (constat.getTest().getLigneProgramme() == null) {

            return false;
        }

        if (constat.getTest()
                .getLigneProgramme()
                .getObjectif() == null) {

            return false;
        }

        if (constat.getTest()
                .getLigneProgramme()
                .getObjectif()
                .getTdr() == null) {

            return false;
        }

        if (constat.getTest()
                .getLigneProgramme()
                .getObjectif()
                .getTdr()
                .getMission() == null) {

            return false;
        }

        Long constatMissionId =
                constat.getTest()
                        .getLigneProgramme()
                        .getObjectif()
                        .getTdr()
                        .getMission()
                        .getId();

        return missionId.equals(
                constatMissionId
        );
    }



    private RecommandationRegroupeeResponse construireLigne(
            Constat constat,
            Recommandation recommandation) {

        RecommandationRegroupeeResponse ligne =
                new RecommandationRegroupeeResponse();



        ligne.setConstatId(
                constat.getId()
        );

        ligne.setConstatReference(
                constat.getReference()
        );

        ligne.setAnomalie(
                constat.getDescriptions()
        );



        ligne.setRecommandationId(
                recommandation.getId()
        );

        ligne.setRecommandation(
                recommandation.getDescription()
        );



        ligne.setRecommandationMaintenue(
                recommandation.getMaintenue()
        );



        List<Reponse> reponses =
                reponseRepository
                        .findByRecommandationId(
                                recommandation.getId()
                        );

        Reponse derniereReponse =
                trouverDerniereReponse(
                        reponses
                );

        if (derniereReponse != null) {

            ligne.setReponseId(
                    derniereReponse.getId()
            );

            ligne.setReponse(
                    derniereReponse.getDescriptions()
            );

            ligne.setDateReponse(
                    derniereReponse.getDateReponse()
            );
        }

        return ligne;
    }


    private Reponse trouverDerniereReponse(
            List<Reponse> reponses) {

        if (reponses == null
                || reponses.isEmpty()) {

            return null;
        }

        return reponses.stream()
                .max(
                        Comparator
                                .comparing(
                                        Reponse::getDateReponse,
                                        Comparator.nullsFirst(
                                                Comparator.naturalOrder()
                                        )
                                )
                                .thenComparing(
                                        Reponse::getId,
                                        Comparator.nullsFirst(
                                                Comparator.naturalOrder()
                                        )
                                )
                )
                .orElse(null);
    }
}