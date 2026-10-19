package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.infrastructure.jpa.DocumentGenereJpa;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DocumentGenereRepositoryAdapter
        implements DocumentGenereRepository {

    private final DocumentGenereJpa jpa;

    public DocumentGenereRepositoryAdapter(
            DocumentGenereJpa jpa) {

        this.jpa = jpa;
    }

    @Override
    public DocumentGenere save(DocumentGenere documentGenere) {

        return jpa.save(documentGenere);
    }

    @Override
    public Optional<DocumentGenere> findById(Long id) {

        return jpa.findById(id);
    }

    @Override
    public List<DocumentGenere> findAll() {

        return jpa.findAllByOrderByDateGenerationDesc();
    }

    @Override
    public List<DocumentGenere> findByDocumentTypeId(
            Long documentTypeId) {

        return jpa.findByDocumentTypeIdOrderByDateGenerationDesc(
                documentTypeId);
    }

    @Override
    public List<DocumentGenere> findByMissionId(Long missionId) {

        return jpa.findByMissionIdOrderByDateGenerationDesc(missionId);
    }

    @Override
    public List<DocumentGenere> findByDateGenerationAfter(
            LocalDateTime date) {

        return jpa.findByDateGenerationAfter(date);
    }

    @Override
    public List<DocumentGenere> findRecent(int limit) {

        return jpa.findAllByOrderByDateGenerationDesc(
                PageRequest.of(0, limit));
    }

    @Override
    public long count() {

        return jpa.count();
    }

    @Override
    public long countByDocumentTypeId(Long documentTypeId) {

        return jpa.countByDocumentTypeId(documentTypeId);
    }

    @Override
    public Map<Long, Long> countGroupedByDocumentType() {

        Map<Long, Long> counts = new HashMap<>();

        for (Object[] row : jpa.countGroupedByDocumentType()) {

            counts.put((Long) row[0], (Long) row[1]);
        }

        return counts;
    }

    @Override
    public void detachMission(Long missionId) {

        jpa.detachMission(missionId);
    }

    @Override
    public void deleteById(Long id) {

        jpa.deleteById(id);
    }
}
