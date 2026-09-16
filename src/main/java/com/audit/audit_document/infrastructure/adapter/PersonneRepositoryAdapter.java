package com.audit.audit_document.infrastructure.repository;

import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Repository;
import com.audit.audit_document.infrastructure.jpa.PersonneJpa;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonneRepositoryAdapter implements PersonneRepository {

    private final PersonneJpa repository;

    public PersonneRepositoryAdapter(PersonneJpa repository) {
        this.repository = repository;
    }

    @Override
    public Personne save(Personne personne) {
        return repository.save(personne);
    }

    @Override
    public Optional<Personne> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Personne> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}