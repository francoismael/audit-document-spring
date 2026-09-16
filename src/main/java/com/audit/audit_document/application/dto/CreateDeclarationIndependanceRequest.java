package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class CreateDeclarationIndependanceRequest {
    private Long missionId;

    private Long personneId;

    private LocalDate dateDeclaration;


    public CreateDeclarationIndependanceRequest(){

    }

    public Long getMissionId(){
        return missionId;
    }

    public void setMissionId(Long missionId){
        this.missionId = missionId;
    }

    public Long getPersonneId(){
        return personneId;
    }

    public void setPersonneId(Long personneId){
        this.personneId = personneId;
    }

    public LocalDate getDateDeclaration(){
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration){
        this.dateDeclaration = dateDeclaration;
    }

}
