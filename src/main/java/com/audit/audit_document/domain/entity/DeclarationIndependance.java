package com.audit.audit_document.domain.entity;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "declaration_independance",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uq_declaration",
                columnNames = {"mission_id", "personne_id"}
            )
        }
)

public class DeclarationIndependance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @Column(name = "date_declaration", nullable = false)
    private LocalDate dateDeclaration;



    public DeclarationIndependance(){

    }

    public Long getId(){
        return id;
    }

    public Mission getMission(){
        return mission;
    }

    public void setMission(Mission mission){
        this.mission = mission;
    }

    public Personne getPersonne(){
        return personne;
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
    }

    public LocalDate getDateDeclaration(){
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration){
        this.dateDeclaration = dateDeclaration;
    }



}