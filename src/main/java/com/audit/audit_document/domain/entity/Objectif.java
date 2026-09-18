package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "objectif",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_objectif_numero",
            columnNames = {"tdr_id", "numero"}
        )
    }
)
public class Objectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "tdr_id",
        nullable = false
    )
    private Tdr tdr;

    @Column(
        nullable = false,
        length = 20
    )
    private String numero;

    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String descriptions;

    public Objectif() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tdr getTdr() {
        return tdr;
    }

    public void setTdr(Tdr tdr) {
        this.tdr = tdr;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}