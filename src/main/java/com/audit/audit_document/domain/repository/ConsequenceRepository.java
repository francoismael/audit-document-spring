package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Consequence;

public interface ConsequenceRepository {

    Consequence save(Consequence consequence);

    Optional<Consequence> findById(Long id);

    List<Consequence> findByConstatId(Long constatId);

    void deleteById(Long id);

    void deleteByConstatId(Long constatId);
}