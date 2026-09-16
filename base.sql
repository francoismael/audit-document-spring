/* table qui représente la direction ou service ex: directions des ressource, ... */
create table structure (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    descriptions TEXT
);


CREATE TABLE mission (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(100) NOT NULL UNIQUE,
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


CREATE TABLE personne (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(150) NOT NULL,
    matricule VARCHAR(100),
    fonction VARCHAR(255),
    services VARCHAR(255)
);


/* pour savoir quelle personne est dans quelle mission avec sont role */
CREATE TABLE mission_personne (
    id SERIAL PRIMARY KEY,
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


/* cancerne une personne dans la cadre de la mission */
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



/* une mission peut avoir plusieurs fiches d'interview */
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





/* le TDR  appartient a une mission */
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


/* une mission possede une programme de travail */
CREATE TABLE programme_travail (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_programme_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE
);


/* tableau */
CREATE TABLE ligne_programme (
    id BIGSERIAL PRIMARY KEY,

    programme_id BIGINT NOT NULL,

    objectif_id BIGINT,

    numero_controle VARCHAR(50),

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


/* lancé une teste a partir d'une ligne de programme */
CREATE TABLE test (
    id BIGSERIAL PRIMARY KEY,

    ligne_programme_id BIGINT NOT NULL,

    reference VARCHAR(100),

    date_test DATE,

    procedure_realisee TEXT,

    resultat TEXT,

    conclusion TEXT,

    observation TEXT,

    CONSTRAINT fk_test_ligne_programme
        FOREIGN KEY (ligne_programme_id)
        REFERENCES ligne_programme(id)
        ON DELETE CASCADE
);



/* les élément des testes, peut contenir plusieurs élément */
CREATE TABLE echantillon (
    id BIGSERIAL PRIMARY KEY,

    test_id BIGINT NOT NULL,

    numero VARCHAR(50),

    reference VARCHAR(255),

    descriptions TEXT,

    anomalie_detectee BOOLEAN DEFAULT FALSE,

    observation TEXT,

    CONSTRAINT fk_echantillon_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)
        ON DELETE CASCADE
);


/* un test peut produire 1 a n constat */
CREATE TABLE constat (
    id BIGSERIAL PRIMARY KEY,

    test_id BIGINT NOT NULL,

    reference VARCHAR(100) NOT NULL,

    descriptions TEXT NOT NULL,

    niveau_risque VARCHAR(100),

    direction_service_concerne VARCHAR(255),

    CONSTRAINT fk_constat_test
        FOREIGN KEY (test_id)
        REFERENCES test(id)
        ON DELETE CASCADE
);


/* un constat peut avoir 1 a n cause  */
CREATE TABLE cause (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    descriptions TEXT NOT NULL,

    CONSTRAINT fk_cause_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
);


un constat peut avoir 1 a n risque
CREATE TABLE risque (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    descriptions TEXT NOT NULL,

    niveau VARCHAR(100),

    CONSTRAINT fk_risque_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
); 


/* une recommandation et liée a une constat */
CREATE TABLE recommandation (
    id BIGSERIAL PRIMARY KEY,

    constat_id BIGINT NOT NULL,

    description TEXT NOT NULL,

    statut VARCHAR(100),

    CONSTRAINT fk_recommandation_constat
        FOREIGN KEY (constat_id)
        REFERENCES constat(id)
        ON DELETE CASCADE
); 


/* correspond au reponse de l'entité audité  */
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


/* pour gerer le réunio de cloture et ouverture */
CREATE TABLE reunion (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,

    type VARCHAR(30) NOT NULL,

    date_reunion DATE NOT NULL,

    heure_debut TIME,

    heure_fin TIME,

    lieu VARCHAR(255),

    observations TEXT,

    CONSTRAINT fk_reunion_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_reunion_type
        CHECK (type IN ('OUVERTURE', 'CLOTURE'))
); 


/* représente les participant */
CREATE TABLE reunion_personne (
    id BIGSERIAL PRIMARY KEY,

    reunion_id BIGINT NOT NULL,

    personne_id BIGINT NOT NULL,

    role VARCHAR(150),

    type_participant VARCHAR(100),

    CONSTRAINT fk_rp_reunion
        FOREIGN KEY (reunion_id)
        REFERENCES reunion(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_rp_personne
        FOREIGN KEY (personne_id)
        REFERENCES personne(id)
);







CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO role (nom) VALUES ('ADMIN'), ('UTILISATEUR');

CREATE TABLE utilisateur (
    id BIGSERIAL PRIMARY KEY,

    nom VARCHAR(100) NOT NULL,

    prenom VARCHAR(150) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,

    mot_de_passe VARCHAR(255) NOT NULL,

    role_id BIGINT NOT NULL,

    actif BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_utilisateur_role
        FOREIGN KEY (role_id)
        REFERENCES role(id)
);