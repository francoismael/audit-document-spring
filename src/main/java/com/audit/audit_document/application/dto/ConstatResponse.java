package com.audit.audit_document.application.dto;

import java.util.ArrayList;
import java.util.List;

public class ConstatResponse {

    private Long id;

    private Long testId;

    private String testReference;

    /*
     * Informations héritées de la ligne du programme
     */
    private String numeroControle;

    private String domaineCycle;

    /*
     * Informations héritées de l'objectif
     */
    private Long objectifId;

    private String objectifNumero;

    private String objectifDescription;

    /*
     * Informations propres au constat
     */
    private String reference;

    private String descriptions;

    private String niveauRisque;

    private String directionServiceConcerne;

    /*
     * Éléments du constat
     */
    private List<CauseResponse> causes = new ArrayList<>();

    private List<RisqueResponse> risques = new ArrayList<>();

    private List<ConsequenceResponse> consequences =
            new ArrayList<>();

    private List<RecommandationResponse> recommandations =
            new ArrayList<>();

    public ConstatResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestReference() {
        return testReference;
    }

    public void setTestReference(String testReference) {
        this.testReference = testReference;
    }

    public String getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(String numeroControle) {
        this.numeroControle = numeroControle;
    }

    public String getDomaineCycle() {
        return domaineCycle;
    }

    public void setDomaineCycle(String domaineCycle) {
        this.domaineCycle = domaineCycle;
    }

    public Long getObjectifId() {
        return objectifId;
    }

    public void setObjectifId(Long objectifId) {
        this.objectifId = objectifId;
    }

    public String getObjectifNumero() {
        return objectifNumero;
    }

    public void setObjectifNumero(String objectifNumero) {
        this.objectifNumero = objectifNumero;
    }

    public String getObjectifDescription() {
        return objectifDescription;
    }

    public void setObjectifDescription(String objectifDescription) {
        this.objectifDescription = objectifDescription;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getNiveauRisque() {
        return niveauRisque;
    }

    public void setNiveauRisque(String niveauRisque) {
        this.niveauRisque = niveauRisque;
    }

    public String getDirectionServiceConcerne() {
        return directionServiceConcerne;
    }

    public void setDirectionServiceConcerne(
            String directionServiceConcerne) {
        this.directionServiceConcerne =
                directionServiceConcerne;
    }

    public List<CauseResponse> getCauses() {
        return causes;
    }

    public void setCauses(List<CauseResponse> causes) {
        this.causes = causes;
    }

    public List<RisqueResponse> getRisques() {
        return risques;
    }

    public void setRisques(List<RisqueResponse> risques) {
        this.risques = risques;
    }

    public List<ConsequenceResponse> getConsequences() {
        return consequences;
    }

    public void setConsequences(
            List<ConsequenceResponse> consequences) {
        this.consequences = consequences;
    }

    public List<RecommandationResponse> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(
            List<RecommandationResponse> recommandations) {
        this.recommandations = recommandations;
    }
}