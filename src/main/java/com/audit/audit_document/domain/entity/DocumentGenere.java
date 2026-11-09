package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_genere")
public class DocumentGenere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Null once the type is deleted: the document stays in the history.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    // Code and name of the type, kept when the type is deleted.
    @Column(name = "document_type_code", length = 100)
    private String documentTypeCode;

    @Column(name = "document_type_nom", length = 255)
    private String documentTypeNom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(
            name = "nom_fichier",
            nullable = false,
            length = 255
    )
    private String nomFichier;

    @Column(
            name = "chemin_fichier",
            nullable = false,
            length = 500
    )
    private String cheminFichier;

    @Column(nullable = false)
    private Long taille;

    @Column(columnDefinition = "TEXT")
    private String valeurs;

    @Column(
            name = "template_version"
    )
    private Integer templateVersion;

    @Column(
            name = "date_generation",
            nullable = false
    )
    private LocalDateTime dateGeneration;

    // Account that generated the document; it stays in the history when the account is deleted.
    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    public DocumentGenere() {
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

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getDocumentTypeNom() {
        return documentTypeNom;
    }

    public void setDocumentTypeNom(String documentTypeNom) {
        this.documentTypeNom = documentTypeNom;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getValeurs() {
        return valeurs;
    }

    public void setValeurs(String valeurs) {
        this.valeurs = valeurs;
    }

    public Integer getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(Integer templateVersion) {
        this.templateVersion = templateVersion;
    }

    public LocalDateTime getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
