package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.DocumentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentTemplateJpa
        extends JpaRepository<DocumentTemplate, Long> {

    List<DocumentTemplate> findByDocumentTypeId(
            Long documentTypeId);

    Optional<DocumentTemplate>
    findByDocumentTypeIdAndActifTrue(
            Long documentTypeId);

    @Modifying
    @Query(
        "UPDATE DocumentTemplate t " +
        "SET t.actif = false " +
        "WHERE t.documentType.id = :documentTypeId"
    )
    void deactivateByDocumentTypeId(
            @Param("documentTypeId")
            Long documentTypeId);
}