package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.OrdreSectionsRequest;
import com.audit.audit_document.application.dto.OrdreSectionsResponse;
import com.audit.audit_document.application.usecases.GetOrdreSectionsUseCase;
import com.audit.audit_document.application.usecases.ResetOrdreSectionsUseCase;
import com.audit.audit_document.application.usecases.SaveOrdreSectionsUseCase;
import com.audit.audit_document.domain.entity.OrdreSectionDocument;
import com.audit.audit_document.domain.repository.OrdreSectionDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/*
 * Order of the sections in the rendering of the 12 business documents.
 * The sections themselves (and their default order) are defined by the front:
 * the backend only checks the format of the codes and stores the custom order.
 */
@Service
public class OrdreSectionService
        implements GetOrdreSectionsUseCase,
                   SaveOrdreSectionsUseCase,
                   ResetOrdreSectionsUseCase {

    // Codes of the 12 business documents, the same as their page in the mission workspace.
    static final Set<String> BUSINESS_DOCUMENTS = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(
            "ordre-de-mission",
            "tdr",
            "declarations",
            "pv-ouverture",
            "interviews",
            "programme",
            "tests",
            "constats",
            "pv-affirmation",
            "rapport",
            "pv-cloture",
            "recommandations")));

    private static final Pattern SECTION_CODE = Pattern.compile("[a-z][a-z0-9-]{0,49}");

    private static final int MAX_SECTIONS = 50;

    private final OrdreSectionDocumentRepository repository;

    public OrdreSectionService(OrdreSectionDocumentRepository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdreSectionsResponse> getAll() {

        Map<String, List<String>> sectionsByDocument = new LinkedHashMap<>();

        for (OrdreSectionDocument section : repository.findAll()) {

            sectionsByDocument
                    .computeIfAbsent(section.getDocumentCode(), code -> new ArrayList<>())
                    .add(section.getSectionCode());
        }

        List<OrdreSectionsResponse> responses = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : sectionsByDocument.entrySet()) {
            responses.add(new OrdreSectionsResponse(entry.getKey(), entry.getValue()));
        }

        return responses;
    }

    @Override
    @Transactional
    public OrdreSectionsResponse save(String documentCode, OrdreSectionsRequest request) {

        checkDocument(documentCode);

        List<String> sections = request != null ? request.getSections() : null;
        checkSections(sections);

        repository.deleteByDocumentCode(documentCode);
        // The old rows must be gone before the new ones hit the unique constraint.
        repository.flush();

        List<OrdreSectionDocument> rows = new ArrayList<>();

        for (int index = 0; index < sections.size(); index++) {
            rows.add(new OrdreSectionDocument(documentCode, sections.get(index), index + 1));
        }

        repository.saveAll(rows);

        return new OrdreSectionsResponse(documentCode, new ArrayList<>(sections));
    }

    @Override
    @Transactional
    public void reset(String documentCode) {

        checkDocument(documentCode);

        repository.deleteByDocumentCode(documentCode);
    }

    private void checkDocument(String documentCode) {

        if (!BUSINESS_DOCUMENTS.contains(documentCode)) {

            throw new IllegalArgumentException(
                    "Document métier inconnu : " + documentCode
                            + ". Valeurs possibles : " + String.join(", ", BUSINESS_DOCUMENTS) + ".");
        }
    }

    private void checkSections(List<String> sections) {

        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("L'ordre doit contenir au moins une section.");
        }

        if (sections.size() > MAX_SECTIONS) {
            throw new IllegalArgumentException("L'ordre ne peut pas contenir plus de " + MAX_SECTIONS + " sections.");
        }

        Set<String> seen = new HashSet<>();

        for (String section : sections) {

            if (section == null || !SECTION_CODE.matcher(section).matches()) {

                throw new IllegalArgumentException(
                        "Code de section invalide : " + section
                                + ". Il doit commencer par une lettre minuscule et ne contenir que des lettres minuscules, des chiffres et des tirets.");
            }

            if (!seen.add(section)) {
                throw new IllegalArgumentException("La section « " + section + " » apparaît plusieurs fois.");
            }
        }
    }
}
