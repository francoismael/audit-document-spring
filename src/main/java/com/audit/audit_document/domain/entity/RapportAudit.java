package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "rapport_audit",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_rapport_audit_mission",
            columnNames = "mission_id"
        )
    }
)
public class RapportAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "mission_id",
        nullable = false,
        unique = true
    )
    private Mission mission;

    @Column(
        name = "motifs",
        columnDefinition = "TEXT"
    )
    private String motifs;

    @Column(name = "nature")
    private String nature;

    @Column(
        name = "version_rapport",
        nullable = false
    )
    private String versionRapport = "definitif";

    @Column(name = "date_emission")
    private LocalDate dateEmission;

    @Column(name = "proprietaire_document")
    private String proprietaireDocument;

    @Column(
        name = "destinataires",
        columnDefinition = "TEXT"
    )
    private String destinataires;

    @Column(name = "classification")
    private String classification;

    public RapportAudit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public String getMotifs() {
        return motifs;
    }

    public void setMotifs(String motifs) {
        this.motifs = motifs;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getVersionRapport() {
        return versionRapport;
    }

    public void setVersionRapport(String versionRapport) {
        this.versionRapport = versionRapport;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getProprietaireDocument() {
        return proprietaireDocument;
    }

    public void setProprietaireDocument(String proprietaireDocument) {
        this.proprietaireDocument = proprietaireDocument;
    }

    public String getDestinataires() {
        return destinataires;
    }

    public void setDestinataires(String destinataires) {
        this.destinataires = destinataires;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}