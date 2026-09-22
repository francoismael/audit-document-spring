package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.LigneProgramme;
import com.audit.audit_document.domain.repository.LigneProgrammeRepository;
import com.audit.audit_document.infrastructure.jpa.LigneProgrammeJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LigneProgrammeRepositoryAdapter
        implements LigneProgrammeRepository {

    private final LigneProgrammeJpa ligneProgrammeJpa;

    public LigneProgrammeRepositoryAdapter(
            LigneProgrammeJpa ligneProgrammeJpa) {
        this.ligneProgrammeJpa = ligneProgrammeJpa;
    }

    @Override
    public LigneProgramme save(LigneProgramme ligne) {
        return ligneProgrammeJpa.save(ligne);
    }

    @Override
    public Optional<LigneProgramme> findById(Long id) {
        return ligneProgrammeJpa.findById(id);
    }

    @Override
    public List<LigneProgramme> findByProgrammeId(Long programmeId) {
        return ligneProgrammeJpa.findByProgrammeId(programmeId);
    }

    @Override
    public void deleteById(Long id) {
        ligneProgrammeJpa.deleteById(id);
    }

    @Override
    public void deleteByProgrammeId(Long programmeId) {
        ligneProgrammeJpa.deleteByProgrammeId(programmeId);
    }

    @Override
    public String getNextNumeroControle() {

        Long number = ligneProgrammeJpa.getNextNumeroControle();

        return String.format("CTRL%03d", number);
    }
}