package com.audit.audit_document.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reponse")
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "recommandation_id",
            nullable = false
    )
    private Recommandation recommandation;

    @Column(
            name = "descriptions",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String descriptions;

    @Column(name = "date_reponse")
    private LocalDate dateReponse;

    public Reponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recommandation getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(
            Recommandation recommandation) {
        this.recommandation = recommandation;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(
            String descriptions) {
        this.descriptions = descriptions;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(
            LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }
}