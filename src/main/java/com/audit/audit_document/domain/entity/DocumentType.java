package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false, length = 255)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean actif = true;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @OneToMany(
            mappedBy = "documentType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DocumentTemplate> templates =
            new ArrayList<>();

    public DocumentType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<DocumentTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(
            List<DocumentTemplate> templates) {

        this.templates = templates;
    }

    public void addTemplate(
            DocumentTemplate template) {

        templates.add(template);
        template.setDocumentType(this);
    }

    public void removeTemplate(
            DocumentTemplate template) {

        templates.remove(template);
        template.setDocumentType(null);
    }
}