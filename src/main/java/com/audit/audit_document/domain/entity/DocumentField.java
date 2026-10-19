package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "document_field",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_document_field_nom",
            columnNames = {
                "document_type_id",
                "nom"
            }
        )
    }
)
public class DocumentField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "document_type_id",
        nullable = false
    )
    private DocumentType documentType;

    @Column(
        name = "nom",
        nullable = false,
        length = 100
    )
    private String nom;

    @Column(
        name = "libelle",
        nullable = false,
        length = 255
    )
    private String libelle;

    @Column(
        name = "type",
        nullable = false,
        length = 30
    )
    private String type;

    @Column(
        name = "obligatoire",
        nullable = false
    )
    private Boolean obligatoire = false;

    @Column(
        name = "ordre",
        nullable = false
    )
    private Integer ordre;

    public DocumentField() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}