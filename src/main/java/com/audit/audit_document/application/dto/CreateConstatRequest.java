package com.audit.audit_document.application.dto;

import java.util.ArrayList;
import java.util.List;

public class CreateConstatRequest {

    private Long testId;

    private String descriptions;

    private String niveauRisque;

    private String directionServiceConcerne;

    private List<CauseRequest> causes = new ArrayList<>();

    private List<RisqueRequest> risques = new ArrayList<>();

    private List<ConsequenceRequest> consequences = new ArrayList<>();

    private List<RecommandationRequest> recommandations = new ArrayList<>();

    public CreateConstatRequest() {
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
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

    public void setDirectionServiceConcerne(String directionServiceConcerne) {
        this.directionServiceConcerne = directionServiceConcerne;
    }

    public List<CauseRequest> getCauses() {
        return causes;
    }

    public void setCauses(List<CauseRequest> causes) {
        this.causes = causes;
    }

    public List<RisqueRequest> getRisques() {
        return risques;
    }

    public void setRisques(List<RisqueRequest> risques) {
        this.risques = risques;
    }

    public List<ConsequenceRequest> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<ConsequenceRequest> consequences) {
        this.consequences = consequences;
    }

    public List<RecommandationRequest> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(List<RecommandationRequest> recommandations) {
        this.recommandations = recommandations;
    }
}