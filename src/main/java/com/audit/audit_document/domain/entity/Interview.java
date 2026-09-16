package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "interview",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_interview_reference",
            columnNames = "reference"
        )
    }
)
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "personne_interviewee_id", nullable = false)
    private Personne personneInterviewee;

    @Column(nullable = false, length = 50)
    private String reference;

    @Column(name = "date_interview", nullable = false)
    private LocalDate dateInterview;

    @Column(length = 255)
    private String fonction;

    @Column(length = 100)
    private String anciennete;

    @ManyToOne
    @JoinColumn(name = "redige_par_personne_id")
    private Personne redigeParPersonne;

    @ManyToOne
    @JoinColumn(name = "supervise_par_personne_id")
    private Personne superviseParPersonne;

    @ManyToOne
    @JoinColumn(name = "valide_par_personne_id")
    private Personne valideParPersonne;

    @OneToMany(
        mappedBy = "interview",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<InterviewQuestion> questions = new ArrayList<>();

    public Interview() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Personne getPersonneInterviewee() {
        return personneInterviewee;
    }

    public void setPersonneInterviewee(Personne personneInterviewee) {
        this.personneInterviewee = personneInterviewee;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateInterview() {
        return dateInterview;
    }

    public void setDateInterview(LocalDate dateInterview) {
        this.dateInterview = dateInterview;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(String anciennete) {
        this.anciennete = anciennete;
    }

    public Personne getRedigeParPersonne() {
        return redigeParPersonne;
    }

    public void setRedigeParPersonne(Personne redigeParPersonne) {
        this.redigeParPersonne = redigeParPersonne;
    }

    public Personne getSuperviseParPersonne() {
        return superviseParPersonne;
    }

    public void setSuperviseParPersonne(Personne superviseParPersonne) {
        this.superviseParPersonne = superviseParPersonne;
    }

    public Personne getValideParPersonne() {
        return valideParPersonne;
    }

    public void setValideParPersonne(Personne valideParPersonne) {
        this.valideParPersonne = valideParPersonne;
    }

    public List<InterviewQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<InterviewQuestion> questions) {
        this.questions = questions;
    }

    public void addQuestion(InterviewQuestion question) {
        questions.add(question);
        question.setInterview(this);
    }

    public void removeQuestion(InterviewQuestion question) {
        questions.remove(question);
        question.setInterview(null);
    }
}