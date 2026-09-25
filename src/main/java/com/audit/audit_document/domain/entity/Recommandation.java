package com.audit.audit_document.domain.entity;

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
@Table(name = "recommandation")
public class Recommandation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "constat_id", nullable = false)
    private Constat constat;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "retenue", nullable = false)
    private Boolean retenue = false;

    @Column(name = "maintenue")
    private Boolean maintenue;

    public Recommandation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Constat getConstat() {
        return constat;
    }

    public void setConstat(Constat constat) {
        this.constat = constat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRetenue() {
        return retenue;
    }

    public void setRetenue(Boolean retenue) {
        this.retenue = retenue;
    }

    public Boolean getMaintenue() {
        return maintenue;
    }

    public void setMaintenue(Boolean maintenue) {
        this.maintenue = maintenue;
    }
}