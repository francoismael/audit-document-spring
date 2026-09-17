package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.ReunionPersonne;

import java.util.List;

public interface ReunionPersonneRepository {

    ReunionPersonne save(ReunionPersonne reunionPersonne);

    List<ReunionPersonne> findByReunionId(Long reunionId);

    void deleteByReunionId(Long reunionId);

    void flush();
}