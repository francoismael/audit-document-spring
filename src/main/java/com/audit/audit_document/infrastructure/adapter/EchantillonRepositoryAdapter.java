package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Echantillon;
import com.audit.audit_document.domain.repository.EchantillonRepository;
import com.audit.audit_document.infrastructure.jpa.EchantillonJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EchantillonRepositoryAdapter
        implements EchantillonRepository {

    private final EchantillonJpa echantillonJpa;

    public EchantillonRepositoryAdapter(
            EchantillonJpa echantillonJpa) {

        this.echantillonJpa = echantillonJpa;
    }

    @Override
    public Echantillon save(
            Echantillon echantillon) {

        return echantillonJpa.save(
                echantillon
        );
    }

    @Override
    public Optional<Echantillon> findById(
            Long id) {

        return echantillonJpa.findById(id);
    }

    @Override
    public List<Echantillon> findByTestId(
            Long testId) {

        return echantillonJpa.findByTestId(
                testId
        );
    }

    @Override
    public void deleteById(Long id) {

        echantillonJpa.deleteById(id);
    }

    @Override
    public void deleteByTestId(Long testId) {

        echantillonJpa.deleteByTestId(testId);
    }

    @Override
    public Integer getNextNumero(Long testId) {

        return echantillonJpa.getNextNumero(
                testId
        );
    }

    @Override
    public String getNextReference() {

        Long number =
                echantillonJpa.getNextReference();

        return String.format(
                "DOS-%03d",
                number
        );
    }
}