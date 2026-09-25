package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Cause;

public interface CauseRepository {

    Cause save(Cause cause);

    Optional<Cause> findById(Long id);

    List<Cause> findByConstatId(Long constatId);

    void deleteById(Long id);

    void deleteByConstatId(Long constatId);
}