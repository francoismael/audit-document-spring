package com.audit.audit_document.domain.entity;

import javax.persistence.*;

/*
 * Custom place of one section in the rendering of a business document.
 * Only the documents whose order was changed in the settings have rows:
 * the default order is the one of the official model, kept by the front.
 */
@Entity
@Table(
    name = "ordre_section_document",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_ordre_section_document",
            columnNames = {"document_code", "section_code"}
        )
    }
)
public class OrdreSectionDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "document_code",
        nullable = false,
        length = 50
    )
    private String documentCode;

    @Column(
        name = "section_code",
        nullable = false,
        length = 50
    )
    private String sectionCode;

    @Column(nullable = false)
    private Integer position;

    public OrdreSectionDocument() {
    }

    public OrdreSectionDocument(String documentCode, String sectionCode, Integer position) {
        this.documentCode = documentCode;
        this.sectionCode = sectionCode;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
