package com.audit.audit_document.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "constat")
public class Constat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "reference", nullable = false, length = 100)
    private String reference;

    @Column(name = "descriptions", nullable = false, columnDefinition = "TEXT")
    private String descriptions;

    @Column(name = "niveau_risque", length = 50)
    private String niveauRisque;

    @Column(name = "direction_service_concerne", length = 255)
    private String directionServiceConcerne;

    @OneToMany(
        mappedBy = "constat",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Cause> causes = new ArrayList<>();

    @OneToMany(
        mappedBy = "constat",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Risque> risques = new ArrayList<>();

    @OneToMany(
        mappedBy = "constat",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Consequence> consequences = new ArrayList<>();

    @OneToMany(
        mappedBy = "constat",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Recommandation> recommandations = new ArrayList<>();

    public Constat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getNiveauRisque() {
        return niveauRisque;
    }

    public void setNiveauRisque(String niveauRisque) {
        this.niveauRisque = niveauRisque;
    }

    public String getDirectionServiceConcerne() {
        return directionServiceConcerne;
    }

    public void setDirectionServiceConcerne(String directionServiceConcerne) {
        this.directionServiceConcerne = directionServiceConcerne;
    }

    public List<Cause> getCauses() {
        return causes;
    }

    public void setCauses(List<Cause> causes) {
        this.causes = causes;
    }

    public List<Risque> getRisques() {
        return risques;
    }

    public void setRisques(List<Risque> risques) {
        this.risques = risques;
    }

    public List<Consequence> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<Consequence> consequences) {
        this.consequences = consequences;
    }

    public List<Recommandation> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(List<Recommandation> recommandations) {
        this.recommandations = recommandations;
    }

    public void addCause(Cause cause) {
        causes.add(cause);
        cause.setConstat(this);
    }

    public void removeCause(Cause cause) {
        causes.remove(cause);
        cause.setConstat(null);
    }

    public void addRisque(Risque risque) {
        risques.add(risque);
        risque.setConstat(this);
    }

    public void removeRisque(Risque risque) {
        risques.remove(risque);
        risque.setConstat(null);
    }

    public void addConsequence(Consequence consequence) {
        consequences.add(consequence);
        consequence.setConstat(this);
    }

    public void removeConsequence(Consequence consequence) {
        consequences.remove(consequence);
        consequence.setConstat(null);
    }

    public void addRecommandation(Recommandation recommandation) {
        recommandations.add(recommandation);
        recommandation.setConstat(this);
    }

    public void removeRecommandation(Recommandation recommandation) {
        recommandations.remove(recommandation);
        recommandation.setConstat(null);
    }
}