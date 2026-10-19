package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DocumentGenereResponse;
import com.audit.audit_document.application.dto.MoisStatistiqueResponse;
import com.audit.audit_document.application.dto.StatistiquesResponse;
import com.audit.audit_document.application.dto.TypeDocumentStatistiqueResponse;
import com.audit.audit_document.application.usecases.GetStatistiquesUseCase;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatistiquesService implements GetStatistiquesUseCase {

    private static final int MONTHS_IN_HISTORY = 12;

    private static final int RECENT_DOCUMENTS = 5;

    private final MissionRepository missionRepository;

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentGenereRepository documentGenereRepository;

    private final DocumentGenereMapper documentGenereMapper;

    public StatistiquesService(
            MissionRepository missionRepository,
            DocumentTypeRepository documentTypeRepository,
            DocumentGenereRepository documentGenereRepository,
            DocumentGenereMapper documentGenereMapper) {

        this.missionRepository = missionRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.documentGenereRepository = documentGenereRepository;
        this.documentGenereMapper = documentGenereMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public StatistiquesResponse getStatistics() {

        StatistiquesResponse response = new StatistiquesResponse();

        fillMissionStatistics(response);
        fillDocumentTypeStatistics(response);

        response.setNombreDocumentsGeneres(
                documentGenereRepository.count());

        response.setDocumentsParMois(countDocumentsByMonth());
        response.setDerniersDocuments(findRecentDocuments());

        return response;
    }

    private void fillMissionStatistics(StatistiquesResponse response) {

        List<Mission> missions = missionRepository.findAll();
        LocalDate today = LocalDate.now();

        long ongoing = 0;
        long upcoming = 0;
        long finished = 0;
        long withoutDates = 0;

        for (Mission mission : missions) {

            LocalDate start = mission.getDateDebut();
            LocalDate end = mission.getDateFin();

            if (end != null && end.isBefore(today)) {
                finished++;

            } else if (start != null && start.isAfter(today)) {
                upcoming++;

            } else if (start != null) {
                ongoing++;

            } else {
                withoutDates++;
            }
        }

        response.setNombreMissions(missions.size());
        response.setMissionsEnCours(ongoing);
        response.setMissionsAVenir(upcoming);
        response.setMissionsTerminees(finished);
        response.setMissionsSansDates(withoutDates);
    }

    private void fillDocumentTypeStatistics(StatistiquesResponse response) {

        List<DocumentType> types = documentTypeRepository.findAll();
        Map<Long, Long> countsByType =
                documentGenereRepository.countGroupedByDocumentType();

        long activeTypes = 0;
        List<TypeDocumentStatistiqueResponse> documentsByType = new ArrayList<>();

        for (DocumentType type : types) {

            if (Boolean.TRUE.equals(type.getActif())) {
                activeTypes++;
            }

            documentsByType.add(
                    new TypeDocumentStatistiqueResponse(
                            type.getId(),
                            type.getCode(),
                            type.getNom(),
                            countsByType.getOrDefault(type.getId(), 0L)));
        }

        documentsByType.sort((first, second) ->
                Long.compare(second.getNombre(), first.getNombre()));

        response.setNombreTypesDocuments(types.size());
        response.setNombreTypesActifs(activeTypes);
        response.setDocumentsParType(documentsByType);
    }

    private List<MoisStatistiqueResponse> countDocumentsByMonth() {

        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(MONTHS_IN_HISTORY - 1L);

        Map<YearMonth, Long> countsByMonth = new LinkedHashMap<>();

        for (int i = 0; i < MONTHS_IN_HISTORY; i++) {
            countsByMonth.put(firstMonth.plusMonths(i), 0L);
        }

        List<DocumentGenere> documents =
                documentGenereRepository.findByDateGenerationAfter(
                        firstMonth.atDay(1).atStartOfDay());

        for (DocumentGenere document : documents) {

            YearMonth month = YearMonth.from(document.getDateGeneration());
            countsByMonth.computeIfPresent(month, (key, count) -> count + 1);
        }

        List<MoisStatistiqueResponse> result = new ArrayList<>();

        for (Map.Entry<YearMonth, Long> entry : countsByMonth.entrySet()) {

            result.add(
                    new MoisStatistiqueResponse(
                            entry.getKey().toString(),
                            entry.getValue()));
        }

        return result;
    }

    private List<DocumentGenereResponse> findRecentDocuments() {

        List<DocumentGenereResponse> result = new ArrayList<>();

        for (DocumentGenere document
                : documentGenereRepository.findRecent(RECENT_DOCUMENTS)) {

            result.add(documentGenereMapper.toResponse(document));
        }

        return result;
    }
}
