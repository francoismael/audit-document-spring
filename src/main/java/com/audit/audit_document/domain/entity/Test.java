package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * A test is created from one programme line.
     */
    @ManyToOne
    @JoinColumn(
            name = "ligne_programme_id",
            nullable = false
    )
    private LigneProgramme ligneProgramme;

    /*
     * Automatically generated reference:
     * FT001, FT002, FT003...
     */
    @Column(
            nullable = false,
            length = 100
    )
    private String reference;

    @Column(name = "date_test")
    private LocalDate dateTest;

    @Column(
            name = "procedure_realisee",
            columnDefinition = "TEXT"
    )
    private String procedureRealisee;

    @Column(
            name = "resume_anomalies",
            columnDefinition = "TEXT"
    )
    private String resumeAnomalies;

    @Column(
            name = "resultat_test",
            length = 50
    )
    private String resultatTest;

    @Column(name = "risque_maitrise")
    private Boolean risqueMaitrise;

    @Column(
            columnDefinition = "TEXT"
    )
    private String recommandations;

    @Column(
            name = "commentaires_chef_mission",
            columnDefinition = "TEXT"
    )
    private String commentairesChefMission;

    @Column(
            name = "commentaires_superviseur",
            columnDefinition = "TEXT"
    )
    private String commentairesSuperviseur;

    @ManyToOne
    @JoinColumn(
            name = "auditeur_mission_personne_id"
    )
    private MissionPersonne auditeur;

    @Column(name = "date_audit")
    private LocalDate dateAudit;

    @ManyToOne
    @JoinColumn(
            name = "chef_mission_mission_personne_id"
    )
    private MissionPersonne chefMission;

    @Column(name = "date_revue_chef_mission")
    private LocalDate dateRevueChefMission;

    @ManyToOne
    @JoinColumn(
            name = "superviseur_mission_personne_id"
    )
    private MissionPersonne superviseur;

    @Column(name = "date_revue_superviseur")
    private LocalDate dateRevueSuperviseur;

    /*
     * One test can contain several samples.
     */
    @OneToMany(
            mappedBy = "test",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Echantillon> echantillons = new ArrayList<>();

    public Test() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LigneProgramme getLigneProgramme() {
        return ligneProgramme;
    }

    public void setLigneProgramme(LigneProgramme ligneProgramme) {
        this.ligneProgramme = ligneProgramme;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateTest() {
        return dateTest;
    }

    public void setDateTest(LocalDate dateTest) {
        this.dateTest = dateTest;
    }

    public String getProcedureRealisee() {
        return procedureRealisee;
    }

    public void setProcedureRealisee(String procedureRealisee) {
        this.procedureRealisee = procedureRealisee;
    }

    public String getResumeAnomalies() {
        return resumeAnomalies;
    }

    public void setResumeAnomalies(String resumeAnomalies) {
        this.resumeAnomalies = resumeAnomalies;
    }

    public String getResultatTest() {
        return resultatTest;
    }

    public void setResultatTest(String resultatTest) {
        this.resultatTest = resultatTest;
    }

    public Boolean getRisqueMaitrise() {
        return risqueMaitrise;
    }

    public void setRisqueMaitrise(Boolean risqueMaitrise) {
        this.risqueMaitrise = risqueMaitrise;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getCommentairesChefMission() {
        return commentairesChefMission;
    }

    public void setCommentairesChefMission(String commentairesChefMission) {
        this.commentairesChefMission = commentairesChefMission;
    }

    public String getCommentairesSuperviseur() {
        return commentairesSuperviseur;
    }

    public void setCommentairesSuperviseur(String commentairesSuperviseur) {
        this.commentairesSuperviseur = commentairesSuperviseur;
    }

    public MissionPersonne getAuditeur() {
        return auditeur;
    }

    public void setAuditeur(MissionPersonne auditeur) {
        this.auditeur = auditeur;
    }

    public LocalDate getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(LocalDate dateAudit) {
        this.dateAudit = dateAudit;
    }

    public MissionPersonne getChefMission() {
        return chefMission;
    }

    public void setChefMission(MissionPersonne chefMission) {
        this.chefMission = chefMission;
    }

    public LocalDate getDateRevueChefMission() {
        return dateRevueChefMission;
    }

    public void setDateRevueChefMission(LocalDate dateRevueChefMission) {
        this.dateRevueChefMission = dateRevueChefMission;
    }

    public MissionPersonne getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(MissionPersonne superviseur) {
        this.superviseur = superviseur;
    }

    public LocalDate getDateRevueSuperviseur() {
        return dateRevueSuperviseur;
    }

    public void setDateRevueSuperviseur(LocalDate dateRevueSuperviseur) {
        this.dateRevueSuperviseur = dateRevueSuperviseur;
    }

    public List<Echantillon> getEchantillons() {
        return echantillons;
    }

    public void setEchantillons(List<Echantillon> echantillons) {
        this.echantillons = echantillons;
    }

    public void addEchantillon(Echantillon echantillon) {
        echantillons.add(echantillon);
        echantillon.setTest(this);
    }

    public void removeEchantillon(Echantillon echantillon) {
        echantillons.remove(echantillon);
        echantillon.setTest(null);
    }
}