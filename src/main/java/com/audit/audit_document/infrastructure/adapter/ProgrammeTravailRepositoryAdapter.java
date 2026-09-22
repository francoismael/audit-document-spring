package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.ProgrammeTravail;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import com.audit.audit_document.infrastructure.jpa.ProgrammeTravailJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProgrammeTravailRepositoryAdapter
        implements ProgrammeTravailRepository {

    private final ProgrammeTravailJpa programmeTravailJpa;

    public ProgrammeTravailRepositoryAdapter(
            ProgrammeTravailJpa programmeTravailJpa) {
        this.programmeTravailJpa = programmeTravailJpa;
    }

    @Override
    public ProgrammeTravail save(ProgrammeTravail programme) {
        return programmeTravailJpa.save(programme);
    }

    @Override
    public Optional<ProgrammeTravail> findById(Long id) {
        return programmeTravailJpa.findById(id);
    }

    @Override
    public Optional<ProgrammeTravail> findByMissionId(Long missionId) {
        return programmeTravailJpa.findByMissionId(missionId);
    }

    @Override
    public List<ProgrammeTravail> findAll() {
        return programmeTravailJpa.findAll();
    }

    @Override
    public void deleteById(Long id) {
        programmeTravailJpa.deleteById(id);
    }

    @Override
    public boolean existsByMissionId(Long missionId) {
        return programmeTravailJpa.existsByMissionId(missionId);
    }
}