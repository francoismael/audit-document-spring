CREATE DATABASE audit_interne;

CREATE TABLE utilisateur (
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(100) NOT NULL UNIQUE,

    email VARCHAR(255) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    role VARCHAR(30) NOT NULL,

    actif BOOLEAN NOT NULL DEFAULT TRUE,

    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_utilisateur_role
        CHECK (role IN ('ADMIN', 'UTILISATEUR'))
);

CREATE TABLE structure (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    descriptions TEXT
);


CREATE TABLE personne (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(150) NOT NULL,
    matricule VARCHAR(100),
    fonction VARCHAR(255),
    services VARCHAR(255)
);



CREATE SEQUENCE mission_numero_seq
START WITH 1
INCREMENT BY 1;
CREATE TABLE mission (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(100) NOT NULL UNIQUE,
    intitule VARCHAR(500) NOT NULL,
    objet TEXT,
    date_debut DATE,
    date_fin DATE,
    date_signature DATE,
    structure_id BIGINT NOT NULL,

    CONSTRAINT fk_mission_structure
        FOREIGN KEY (structure_id)
        REFERENCES structure(id)
);


CREATE TABLE mission_personne (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,
    personne_id BIGINT NOT NULL,

    roles VARCHAR(100) NOT NULL,

    CONSTRAINT fk_mp_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_mp_personne
        FOREIGN KEY (personne_id)
        REFERENCES personne(id),

    CONSTRAINT uq_mission_personne_role
        UNIQUE (mission_id, personne_id, roles)
);

CREATE TABLE declaration_independance (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,

    personne_id BIGINT NOT NULL,

    date_declaration DATE NOT NULL,

    CONSTRAINT fk_declaration_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_declaration_personne
        FOREIGN KEY (personne_id)
        REFERENCES personne(id),

    CONSTRAINT uq_declaration
        UNIQUE (mission_id, personne_id)
);

CREATE SEQUENCE interview_reference_seq
START WITH 1
INCREMENT BY 1;

CREATE TABLE interview (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,

    personne_interviewee_id BIGINT NOT NULL,

    reference VARCHAR(50) NOT NULL,

    date_interview DATE NOT NULL,

    fonction VARCHAR(255),

    anciennete VARCHAR(100),

    redige_par_personne_id BIGINT,

    supervise_par_personne_id BIGINT,

    valide_par_personne_id BIGINT,

    CONSTRAINT fk_interview_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_interview_personne_interviewee
        FOREIGN KEY (personne_interviewee_id)
        REFERENCES personne(id),

    CONSTRAINT fk_interview_redige_par
        FOREIGN KEY (redige_par_personne_id)
        REFERENCES personne(id),

    CONSTRAINT fk_interview_supervise_par
        FOREIGN KEY (supervise_par_personne_id)
        REFERENCES personne(id),

    CONSTRAINT fk_interview_valide_par
        FOREIGN KEY (valide_par_personne_id)
        REFERENCES personne(id),

    CONSTRAINT uq_interview_reference
        UNIQUE (reference)
);


CREATE TABLE interview_question (
    id BIGSERIAL PRIMARY KEY,

    interview_id BIGINT NOT NULL,

    numero INTEGER NOT NULL,

    question TEXT NOT NULL,

    reponse TEXT,

    CONSTRAINT fk_interview_question_interview
        FOREIGN KEY (interview_id)
        REFERENCES interview(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_interview_question_numero
        UNIQUE (interview_id, numero)
);

CREATE TABLE tdr (
    id SERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL UNIQUE,

    objet TEXT,

    contexte TEXT,

    competence TEXT,

    perimetre TEXT,

    periode_observation TEXT,

    lieu VARCHAR(255),

    methodologie_travail TEXT,

    resultats_attendus TEXT,

    livrables TEXT,

    CONSTRAINT fk_tdr_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE
);

/* Objectif defini dans TDR */
CREATE TABLE objectif (
    id BIGSERIAL PRIMARY KEY,

    tdr_id BIGINT NOT NULL,

    numero VARCHAR(20) NOT NULL,

    descriptions TEXT NOT NULL,

    CONSTRAINT fk_objectif_tdr
        FOREIGN KEY (tdr_id)
        REFERENCES tdr(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_objectif_numero
        UNIQUE (tdr_id, numero)
);

/*
   Une mission possède un seul programme de travail
 */

CREATE TABLE programme_travail (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_programme_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE
);


/*
    SEQUENCE POUR LES NUMEROS DE CONTROLE */

CREATE SEQUENCE ligne_programme_controle_seq
START WITH 1
INCREMENT BY 1;


/*  LIGNES DU PROGRAMME DE TRAVAIL
*/

CREATE TABLE ligne_programme (
    id BIGSERIAL PRIMARY KEY,

    programme_id BIGINT NOT NULL,

    objectif_id BIGINT NOT NULL,

    numero_controle VARCHAR(50) NOT NULL,

    tache_operation TEXT,

    faiblesse_a_confirmer TEXT,

    responsable VARCHAR(255),

    frequence VARCHAR(100),

    type_controle VARCHAR(100),

    domaine_cycle VARCHAR(255),

    risque TEXT,

    procedure_test TEXT,

    echantillon_description TEXT,

    technique_audit VARCHAR(255),

    technique_echantillonnage VARCHAR(255),

    CONSTRAINT fk_ligne_programme
        FOREIGN KEY (programme_id)
        REFERENCES programme_travail(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ligne_objectif
        FOREIGN KEY (objectif_id)
        REFERENCES objectif(id)
);




/* séquence de fiche de test */
CREATE SEQUENCE test_reference_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE echantillon_reference_seq
START WITH 1
INCREMENT BY 1;


CREATE TABLE test (
    id BIGSERIAL PRIMARY KEY,

    ligne_programme_id BIGINT NOT NULL,

    reference VARCHAR(100) NOT NULL,

    date_test DATE,

    procedure_realisee TEXT,

    resume_anomalies TEXT,

    resultat_test VARCHAR(50),

    risque_maitrise BOOLEAN,

    recommandations TEXT,

    commentaires_chef_mission TEXT,

    commentaires_superviseur TEXT,

    auditeur_mission_personne_id BIGINT,

    date_audit DATE,

    chef_mission_mission_personne_id BIGINT,

    date_revue_chef_mission DATE,

    superviseur_mission_personne_id BIGINT,

    date_revue_superviseur DATE,

    CONSTRAINT fk_test_ligne_programme
        FOREIGN KEY (ligne_programme_id)
        REFERENCES ligne_programme(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_test_auditeur
        FOREIGN KEY (auditeur_mission_personne_id)
        REFERENCES mission_personne(id),

    CONSTRAINT fk_test_chef_mission
        FOREIGN KEY (chef_mission_mission_personne_id)
        REFERENCES mission_personne(id),

    CONSTRAINT fk_test_superviseur
        FOREIGN KEY (superviseur_mission_personne_id)
        REFERENCES mission_personne(id),

    CONSTRAINT chk_resultat_test
        CHECK (
            resultat_test IS NULL
            OR resultat_test IN (
                'EFFECTIF',
                'INEFFECTIF',
                'PAS_ECHANTILLON',
                'NON_IMPLETE'
            )
        )
);


CREATE TABLE echantillon (
    id BIGSERIAL PRIMARY KEY,

    test_id BIGINT NOT NULL,

    numero INTEGER NOT NULL,

    reference VARCHAR(255),

    anomalie_detectee BOOLEAN NOT NULL DEFAULT FALSE,

    observation TEXT,

    CONSTRAINT fk_echantillon_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_echantillon_numero
        UNIQUE (test_id, numero)
);

CREATE TABLE constat (
    id BIGSERIAL PRIMARY KEY,

    test_id BIGINT NOT NULL,

    reference VARCHAR(100) NOT NULL,

    descriptions TEXT NOT NULL,

    niveau_risque VARCHAR(50),

    direction_service_concerne VARCHAR(255),

    CONSTRAINT fk_constat_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)
        ON DELETE CASCADE
);

CREATE TABLE cause (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    descriptions TEXT NOT NULL,

    CONSTRAINT fk_cause_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
);

CREATE TABLE risque (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    descriptions TEXT NOT NULL,

    niveau VARCHAR(50),

    CONSTRAINT fk_risque_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
);

CREATE TABLE consequence (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    descriptions TEXT NOT NULL,

    CONSTRAINT fk_consequence_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
);

CREATE SEQUENCE constat_reference_seq
START WITH 1
INCREMENT BY 1;

CREATE TABLE recommandation (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    description TEXT NOT NULL,

    CONSTRAINT fk_recommandation_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
);

ALTER TABLE recommandation
ADD COLUMN retenue BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE recommandation
ADD COLUMN maintenue BOOLEAN;

CREATE UNIQUE INDEX uq_recommandation_retenue_par_constat
ON recommandation (constat_id)
WHERE retenue = TRUE;

CREATE TABLE reponse (
    id BIGSERIAL PRIMARY KEY,
    recommandation_id BIGINT NOT NULL,
    descriptions TEXT NOT NULL,
    date_reponse DATE,

    CONSTRAINT fk_reponse_recommandation
        FOREIGN KEY (recommandation_id)
        REFERENCES recommandation(id)
        ON DELETE CASCADE
);

INSERT INTO structure (nom, descriptions)
VALUES
('Direction Générale des Finances', 'Structure chargée de la gestion financière'),
('Direction des Ressources Humaines', 'Structure chargée de la gestion des ressources humaines');

INSERT INTO personne (nom, prenom, matricule, fonction, services)
VALUES
('RAKOTO', 'Jean', 'MAT001', 'Chef de mission', 'Direction de l''Audit Interne'),
('RABE', 'Marie', 'MAT002', 'Auditeur interne', 'Direction de l''Audit Interne'),
('RANDRIA', 'Paul', 'MAT003', 'Superviseur', 'Direction de l''Audit Interne'),
('RAZAFINDRAKOTO', 'Luc', 'MAT004', 'Auditeur interne', 'Direction de l''Audit Interne'),
('ANDRIAMBOLOLONA', 'Sophie', 'MAT005', 'Directeur de l''Audit Interne', 'Direction de l''Audit Interne'),
('RAKOTOARISOA', 'Nadia', 'MAT006', 'Chef de mission', 'Direction de l''Audit Interne'),
('RABEMANANA', 'Hery', 'MAT007', 'Superviseur', 'Direction de l''Audit Interne');


