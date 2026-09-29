package com.audit.audit_document.application.dto;

public class PvAffirmationLigneRequest {

    private Long constatId;

    private String question;

    private String reponseEntite;

    private String piecesJustificatives;

    private String commentairesAuditeurs;

    public PvAffirmationLigneRequest() {
    }

    public Long getConstatId() {
        return constatId;
    }

    public void setConstatId(Long constatId) {
        this.constatId = constatId;
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