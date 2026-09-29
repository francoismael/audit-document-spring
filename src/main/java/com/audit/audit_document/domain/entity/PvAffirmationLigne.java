package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "pv_affirmation_ligne",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_pv_affirmation_constat",
            columnNames = {
                "pv_affirmation_id",
                "constat_id"
            }
        )
    }
)
public class PvAffirmationLigne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "pv_affirmation_id",
        nullable = false
    )
    private PvAffirmation pvAffirmation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "constat_id",
        nullable = false
    )
    private Constat constat;

    @Column(
        name = "question",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String question;

    @Column(
        name = "reponse_entite",
        columnDefinition = "TEXT"
    )
    private String reponseEntite;

    @Column(
        name = "pieces_justificatives",
        columnDefinition = "TEXT"
    )
    private String piecesJustificatives;

    @Column(
        name = "commentaires_auditeurs",
        columnDefinition = "TEXT"
    )
    private String commentairesAuditeurs;

    public PvAffirmationLigne() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PvAffirmation getPvAffirmation() {
        return pvAffirmation;
    }

    public void setPvAffirmation(
            PvAffirmation pvAffirmation) {

        this.pvAffirmation = pvAffirmation;
    }

    public Constat getConstat() {
        return constat;
    }

    public void setConstat(Constat constat) {
        this.constat = constat;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponseEntite() {
        return reponseEntite;
    }

    public void setReponseEntite(
            String reponseEntite) {

        this.reponseEntite = reponseEntite;
    }

    public String getPiecesJustificatives() {
        return piecesJustificatives;
    }

    public void setPiecesJustificatives(
            String piecesJustificatives) {

        this.piecesJustificatives =
                piecesJustificatives;
    }

    public String getCommentairesAuditeurs() {
        return commentairesAuditeurs;
    }

    public void setCommentairesAuditeurs(
            String commentairesAuditeurs) {

        this.commentairesAuditeurs =
                commentairesAuditeurs;
    }
}