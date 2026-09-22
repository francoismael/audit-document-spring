package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "ligne_programme")
public class LigneProgramme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "programme_id",
        nullable = false
    )
    private ProgrammeTravail programme;

    @ManyToOne
    @JoinColumn(
        name = "objectif_id",
        nullable = false
    )
    private Objectif objectif;

    @Column(
        name = "numero_controle",
        nullable = false,
        length = 50
    )
    private String numeroControle;

    @Column(
        name = "tache_operation",
        columnDefinition = "TEXT"
    )
    private String tacheOperation;

    @Column(
        name = "faiblesse_a_confirmer",
        columnDefinition = "TEXT"
    )
    private String faiblesseAConfirmer;

    @Column(length = 255)
    private String responsable;

    @Column(length = 100)
    private String frequence;

    @Column(
        name = "type_controle",
        length = 100
    )
    private String typeControle;

    @Column(
        name = "domaine_cycle",
        length = 255
    )
    private String domaineCycle;

    @Column(columnDefinition = "TEXT")
    private String risque;

    @Column(
        name = "procedure_test",
        columnDefinition = "TEXT"
    )
    private String procedureTest;

    @Column(
        name = "echantillon_description",
        columnDefinition = "TEXT"
    )
    private String echantillonDescription;

    @Column(
        name = "technique_audit",
        length = 255
    )
    private String techniqueAudit;

    @Column(
        name = "technique_echantillonnage",
        length = 255
    )
    private String techniqueEchantillonnage;

    public LigneProgramme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProgrammeTravail getProgramme() {
        return programme;
    }

    public void setProgramme(ProgrammeTravail programme) {
        this.programme = programme;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    public String getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(String numeroControle) {
        this.numeroControle = numeroControle;
    }

    public String getTacheOperation() {
        return tacheOperation;
    }

    public void setTacheOperation(String tacheOperation) {
        this.tacheOperation = tacheOperation;
    }

    public String getFaiblesseAConfirmer() {
        return faiblesseAConfirmer;
    }

    public void setFaiblesseAConfirmer(String faiblesseAConfirmer) {
        this.faiblesseAConfirmer = faiblesseAConfirmer;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getTypeControle() {
        return typeControle;
    }

    public void setTypeControle(String typeControle) {
        this.typeControle = typeControle;
    }

    public String getDomaineCycle() {
        return domaineCycle;
    }

    public void setDomaineCycle(String domaineCycle) {
        this.domaineCycle = domaineCycle;
    }

    public String getRisque() {
        return risque;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getProcedureTest() {
        return procedureTest;
    }

    public void setProcedureTest(String procedureTest) {
        this.procedureTest = procedureTest;
    }

    public String getEchantillonDescription() {
        return echantillonDescription;
    }

    public void setEchantillonDescription(String echantillonDescription) {
        this.echantillonDescription = echantillonDescription;
    }

    public String getTechniqueAudit() {
        return techniqueAudit;
    }

    public void setTechniqueAudit(String techniqueAudit) {
        this.techniqueAudit = techniqueAudit;
    }

    public String getTechniqueEchantillonnage() {
        return techniqueEchantillonnage;
    }

    public void setTechniqueEchantillonnage(String techniqueEchantillonnage) {
        this.techniqueEchantillonnage = techniqueEchantillonnage;
    }
}