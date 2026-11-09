package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Sexe;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Component;

/*
 * Checks shared by the creation and the update of a person of the directory.
 */
@Component
public class PersonneRules {

    private final PersonneRepository personneRepository;

    public PersonneRules(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    // The fields entered by the user; the matricule is never one of them.
    public void applyFields(Personne personne, String nom, String prenom, String fonction, String services, Sexe sexe) {

        personne.setNom(requireText(nom, "Le nom de la personne est obligatoire."));
        personne.setPrenom(requireText(prenom, "Le prénom de la personne est obligatoire."));
        personne.setFonction(trimToNull(fonction));
        personne.setServices(trimToNull(services));

        if (sexe == null) {
            throw new IllegalArgumentException("Le sexe de la personne est obligatoire.");
        }

        personne.setSexe(sexe);
    }

    // MAT-001, MAT-002... from a sequence, like the other references (CST-001, FT-001...);
    // matricules entered by hand in the database are skipped.
    public String nextMatricule() {

        String matricule;

        do {
            matricule = String.format("MAT-%03d", personneRepository.getNextMatriculeNumber());
        } while (personneRepository.existsByMatricule(matricule));

        return matricule;
    }

    private static String requireText(String value, String message) {

        String text = trimToNull(value);

        if (text == null) {
            throw new IllegalArgumentException(message);
        }

        return text;
    }

    private static String trimToNull(String value) {

        return value == null || value.trim().isEmpty() ? null : value.trim();
    }
}
