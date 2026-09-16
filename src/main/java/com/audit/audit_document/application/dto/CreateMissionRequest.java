package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.List;

public class CreateMissionRequest {

    private String intitule;
    private String objet;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateSignature;

    private Long structureId;

    private List<MissionPersonneRequest> personnes;

    public CreateMissionRequest() {
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(LocalDate dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public List<MissionPersonneRequest> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<MissionPersonneRequest> personnes) {
        this.personnes = personnes;
    }
}