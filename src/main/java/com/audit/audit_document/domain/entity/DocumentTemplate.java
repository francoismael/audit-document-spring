package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_template")
public class DocumentTemplate {

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
            name = "nom_fichier_original",
            nullable = false,
            length = 255
    )
    private String nomFichierOriginal;

    @Column(
            name = "nom_fichier_stockage",
            nullable = false,
            unique = true,
            length = 255
    )
    private String nomFichierStockage;

    @Column(
            name = "chemin_fichier",
            nullable = false,
            length = 500
    )
    private String cheminFichier;

    @Column(
            name = "type_mime",
            length = 150
    )
    private String typeMime;

    @Column(nullable = false)
    private Long taille;

    @Column(nullable = false)
    private Integer version = 1;

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(
            name = "date_upload",
            nullable = false
    )
    private LocalDateTime dateUpload;

    public DocumentTemplate() {
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

    public void setDocumentType(
            DocumentType documentType) {

        this.documentType = documentType;
    }

    public String getNomFichierOriginal() {
        return nomFichierOriginal;
    }

    public void setNomFichierOriginal(
            String nomFichierOriginal) {

        this.nomFichierOriginal =
                nomFichierOriginal;
    }

    public String getNomFichierStockage() {
        return nomFichierStockage;
    }

    public void setNomFichierStockage(
            String nomFichierStockage) {

        this.nomFichierStockage =
                nomFichierStockage;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(
            String cheminFichier) {

        this.cheminFichier = cheminFichier;
    }

    public String getTypeMime() {
        return typeMime;
    }

    public void setTypeMime(String typeMime) {
        this.typeMime = typeMime;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(
            LocalDateTime dateUpload) {

        this.dateUpload = dateUpload;
    }
}