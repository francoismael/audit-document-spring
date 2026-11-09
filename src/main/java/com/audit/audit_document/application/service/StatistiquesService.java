package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DocumentGenereResponse;
import com.audit.audit_document.application.dto.MoisStatistiqueResponse;
import com.audit.audit_document.application.dto.StatistiquesResponse;
import com.audit.audit_document.application.dto.TypeDocumentStatistiqueResponse;
import com.audit.audit_document.application.usecases.GetStatistiquesUseCase;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.entity.MissionStatus;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiquesService implements GetStatistiquesUseCase {

    private static final int MONTHS_IN_HISTORY = 12;

    private static final int RECENT_DOCUMENTS = 5;

    private final MissionRepository missionRepository;

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentGenereRepository documentGenereRepository;

    private final DocumentGenereMapper documentGenereMapper;

    private final MissionAccess missionAccess;

    public StatistiquesService(
            MissionRepository missionRepository,
            DocumentTypeRepository documentTypeRepository,
            DocumentGenereRepository documentGenereRepository,
            DocumentGenereMapper documentGenereMapper,
            MissionAccess missionAccess) {

        this.missionRepository = missionRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.documentGenereRepository = documentGenereRepository;
        this.documentGenereMapper = documentGenereMapper;
        this.missionAccess = missionAccess;
    }

    @Override
    @Transactional(readOnly = true)
    // Figures of the missions and documents the connected account sees (all of them for an administrator).
    public StatistiquesResponse getStatistics() {

        StatistiquesResponse response = new StatistiquesResponse();

        List<DocumentGenere> documents = documentGenereRepository.findAll()
                .stream()
                .filter(missionAccess.visibleDocuments())
                .collect(Collectors.toList());

        fillMissionStatistics(response);
        fillDocumentTypeStatistics(response, documents);

        response.setNombreDocumentsGeneres(documents.size());

        response.setDocumentsParMois(countDocumentsByMonth(documents));
        response.setDerniersDocuments(findRecentDocuments(documents));

        return response;
    }

    private void fillMissionStatistics(StatistiquesResponse response) {

        List<Mission> missions =
                missionAccess.keepVisible(missionRepository.findAll(), Mission::getId);
        LocalDate today = LocalDate.now();

        long ongoing = 0;
        long upcoming = 0;
        long finished = 0;
        long withoutDates = 0;

        for (Mission mission : missions) {

            switch (MissionStatus.of(mission, today)) {

                case TERMINEE:
                    finished++;
                    break;

                case A_VENIR:
                    upcoming++;
                    break;

                case EN_COURS:
                    ongoing++;
                    break;

                default:
                    withoutDates++;
            }
        }

        response.setNombreMissions(missions.size());
        response.setMissionsEnCours(ongoing);
        response.setMissionsAVenir(upcoming);
        response.setMissionsTerminees(finished);
        response.setMissionsSansDates(withoutDates);
    }

    private void fillDocumentTypeStatistics(
            StatistiquesResponse response,
            List<DocumentGenere> documents) {

        List<DocumentType> types = documentTypeRepository.findAll();
        Map<Long, Long> countsByType = documents
                .stream()
                .filter(document -> document.getDocumentType() != null)
                .collect(Collectors.groupingBy(
                        document -> document.getDocumentType().getId(),
                        Collectors.counting()));

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

    private List<MoisStatistiqueResponse> countDocumentsByMonth(List<DocumentGenere> documents) {

        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(MONTHS_IN_HISTORY - 1L);

        Map<YearMonth, Long> countsByMonth = new LinkedHashMap<>();

        for (int i = 0; i < MONTHS_IN_HISTORY; i++) {
            countsByMonth.put(firstMonth.plusMonths(i), 0L);
        }

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

    private List<DocumentGenereResponse> findRecentDocuments(List<DocumentGenere> documents) {

        return documents
                .stream()
                .sorted(Comparator.comparing(DocumentGenere::getDateGeneration).reversed())
                .limit(RECENT_DOCUMENTS)
                .map(documentGenereMapper::toResponse)
                .collect(Collectors.toList());
    }
}
