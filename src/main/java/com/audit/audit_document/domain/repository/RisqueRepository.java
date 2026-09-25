package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Risque;

public interface RisqueRepository {

    Risque save(Risque risque);

    Optional<Risque> findById(Long id);

    List<Risque> findByConstatId(Long constatId);

    void deleteById(Long id);

    void deleteByConstatId(Long constatId);
}