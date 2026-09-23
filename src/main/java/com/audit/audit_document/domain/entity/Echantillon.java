package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(
        name = "echantillon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_echantillon_numero",
                        columnNames = {"test_id", "numero"}
                )
        }
)
public class Echantillon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Several samples can belong to one test.
     */
    @ManyToOne
    @JoinColumn(
            name = "test_id",
            nullable = false
    )
    private Test test;

    /*
     * Automatically managed inside one test:
     * 1, 2, 3, 4...
     */
    @Column(nullable = false)
    private Integer numero;

    @Column(length = 255)
    private String reference;

    @Column(
            name = "anomalie_detectee",
            nullable = false
    )
    private Boolean anomalieDetectee = false;

    @Column(
            columnDefinition = "TEXT"
    )
    private String observation;

    public Echantillon() {
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getAnomalieDetectee() {
        return anomalieDetectee;
    }

    public void setAnomalieDetectee(Boolean anomalieDetectee) {
        this.anomalieDetectee = anomalieDetectee;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}