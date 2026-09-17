package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Reunion;

import java.util.List;
import java.util.Optional;

public interface ReunionRepository {

    Reunion save(Reunion reunion);

    Optional<Reunion> findById(Long id);

    List<Reunion> findAll();

    void deleteById(Long id);
}