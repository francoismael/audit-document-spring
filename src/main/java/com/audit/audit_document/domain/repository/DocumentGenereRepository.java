package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.DocumentGenere;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DocumentGenereRepository {

    DocumentGenere save(DocumentGenere documentGenere);

    Optional<DocumentGenere> findById(Long id);

    List<DocumentGenere> findAll();

    List<DocumentGenere> findByDocumentTypeId(Long documentTypeId);

    List<DocumentGenere> findByMissionId(Long missionId);

    List<DocumentGenere> findByDateGenerationAfter(LocalDateTime date);

    List<DocumentGenere> findRecent(int limit);

    long count();

    long countByDocumentTypeId(Long documentTypeId);

    Map<Long, Long> countGroupedByDocumentType();

    void detachMission(Long missionId);

    // The documents keep the code and the name of their type, which is deleted.
    void detachDocumentType(Long documentTypeId, String code, String nom);

    void deleteById(Long id);
}
