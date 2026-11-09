package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.DocumentGenere;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentGenereJpa
        extends JpaRepository<DocumentGenere, Long> {

    List<DocumentGenere> findAllByOrderByDateGenerationDesc();

    List<DocumentGenere> findByDocumentTypeIdOrderByDateGenerationDesc(
            Long documentTypeId);

    List<DocumentGenere> findByMissionIdOrderByDateGenerationDesc(
            Long missionId);

    List<DocumentGenere> findByDateGenerationAfter(
            LocalDateTime date);

    List<DocumentGenere> findAllByOrderByDateGenerationDesc(
            Pageable pageable);

    long countByDocumentTypeId(Long documentTypeId);

    @Query("SELECT d.documentType.id, COUNT(d) "
            + "FROM DocumentGenere d "
            + "GROUP BY d.documentType.id")
    List<Object[]> countGroupedByDocumentType();

    @Modifying
    @Query("UPDATE DocumentGenere d SET d.mission = NULL "
            + "WHERE d.mission.id = :missionId")
    void detachMission(@Param("missionId") Long missionId);

    @Modifying
    @Query("UPDATE DocumentGenere d SET d.documentType = NULL, "
            + "d.documentTypeCode = :code, d.documentTypeNom = :nom "
            + "WHERE d.documentType.id = :documentTypeId")
    void detachDocumentType(
            @Param("documentTypeId") Long documentTypeId,
            @Param("code") String code,
            @Param("nom") String nom);
}
