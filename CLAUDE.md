# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Projet

Backend Spring Boot de l'application de génération de documents d'audit interne du MEF (Direction de l'Audit Interne). Il expose une API REST sous `/api`, consommée par le front Angular `../audit-document-angular`.

- Spring Boot 2.7, **Java 8** (pas de `var`, de `record` ni de `List.of`), JPA/Hibernate (`javax.persistence`), PostgreSQL.
- Apache POI (`poi-ooxml`) pour lire et remplir les modèles Word (`.docx`).
- Conversion du document rempli en PDF : moteur choisi par `app.pdf.engine` dans `application.properties`. `word` (par défaut ici) : **Microsoft Word** piloté par PowerShell, rendu exactement identique à Word ; Windows et Word doivent être installés sur la machine du backend. `libreoffice` : LibreOffice en mode headless (`app.pdf.soffice-path`), fonctionne partout mais le rendu peut différer un peu de Word (polices, images, justification).
- Spring Security et JWT (`jjwt`) : toute l'API, sauf `/api/auth/**`, exige un token.
- Base de données : `audit_interne`.

Ce projet est la version avec utilisateurs et authentification de `audit-document-back`. Depuis le 04/11/2026, seul ce projet et le front `../audit-document-angular` évoluent : `audit-document-back` n'est plus modifié.
- Le schéma de la base est mis à jour par `spring.jpa.hibernate.ddl-auto=update`.

## Commandes (PowerShell)

- `.\mvnw spring-boot:run` : lance l'API sur http://localhost:8080.
- `.\mvnw compile` : vérifie la compilation (ajouter `-o` pour travailler hors ligne).

## Architecture

```
api/controller/          contrôleurs REST (+ exeption/GlobalExceptionHandler)
application/dto/         requêtes et réponses de l'API
application/usecases/    interfaces des cas d'usage
application/service/     implémentations des cas d'usage
application/port/        interfaces vers les outils externes (PdfConverter)
domain/entity/           entités JPA et enums métier (MissionRole)
domain/repository/       interfaces de repository du domaine
infrastructure/jpa/      interfaces Spring Data JPA
infrastructure/adapter/  implémentations des repositories du domaine
infrastructure/config/   configuration (CORS global)
infrastructure/pdf/      conversion DOCX → PDF : WordPdfConverter ou LibreOfficePdfConverter (PdfConversion : partie commune)
infrastructure/security/ Spring Security, filtre JWT, encodage des mots de passe
```

- Un contrôleur dépend uniquement des interfaces de `usecases/`. Un service dépend uniquement des interfaces de `domain/repository/`, jamais directement de `infrastructure/jpa/`.
- Le CORS est global (`CorsConfig`) : ne pas ajouter `@CrossOrigin` sur les contrôleurs.
- Les fichiers (modèles et documents générés) se lisent et s'écrivent uniquement via `DocumentStorage`, jamais avec le chemin absolu enregistré en base.
- Les modèles Word utilisent la syntaxe `{{nomDuChamp}}`. Le nom du champ doit correspondre à `DocumentField.nom`.
- La génération produit un **PDF** : le modèle `.docx` est rempli avec POI, puis converti par `PdfConverter` (Word ou LibreOffice). Les conversions sont faites une par une. Avec Word : environ 8 s au premier appel, puis 6 s.
- Les documents de l'historique générés avant le passage au PDF sont des `.docx` : `GeneratedDocument.contentTypeOf()` donne le bon type au téléchargement.
- Les rôles de mission sont fixes (`MissionRole`) : `DIRECTEUR_AUDIT_INTERNE`, `CHEF_MISSION`, `SUPERVISEUR`, `AUDITEUR`, `POINT_FOCAL`.

## Erreurs

`GlobalExceptionHandler` convertit les exceptions en JSON `{ status, message, timestamp }`. Lever l'exception qui correspond au sens de l'erreur, avec un message en français destiné à l'utilisateur :

| Exception | Code | Quand l'utiliser |
|---|---|---|
| `ResourceNotFoundException` | 404 | L'élément demandé n'existe pas (« … introuvable ») |
| `IllegalArgumentException` | 400 | Validation ou règle métier non respectée |
| `ConflictException` | 409 | Doublon (« … existe déjà ») |
| `ForbiddenException` | 403 | Mission ou document hors de l'équipe du compte connecté |
| `RuntimeException` avec une cause | 500 | Erreur technique (lecture ou écriture de fichier, POI…) |

Les deux exceptions métier se trouvent dans `domain/exception/`.

Erreurs propres à la sécurité :

| Cas | Code | Où |
|---|---|---|
| Identifiants incorrects (`BadCredentialsException`) ou compte désactivé (`DisabledException`) | 401 | `LoginService` → `GlobalExceptionHandler` |
| Token absent ou invalide | 401 | `SecurityConfig` (point d'entrée) |
| Rôle insuffisant | 403 | `SecurityConfig` (`accessDeniedHandler`) |

Les erreurs de sécurité ont le même format JSON que les autres.

## Sécurité

- Connexion par nom d'utilisateur et mot de passe (pas d'e-mail) : `POST /api/auth/login` `{ username, password }` renvoie `{ id, username, role, token }`. Le token est valable 1 heure et s'envoie dans l'en-tête `Authorization: Bearer <token>`.
- Inscription publique `POST /api/auth/register` `{ username, password }` : toujours un compte `UTILISATEUR` **inactif**, que l'administrateur active et relie à une personne. `GET /api/auth/me` (compte connecté et personne liée), `PUT /api/auth/password` `{ motDePasseActuel, nouveauMotDePasse }`.
- Mot de passe : au moins 8 caractères, dont une lettre et un chiffre (`UtilisateurRules`).
- Rôles utilisateur (`Role`) : `ADMIN` et `UTILISATEUR`. Ne pas les confondre avec les rôles de mission (`MissionRole`).
- Les règles par rôle sont dans `SecurityConfig` : comptes (`/api/utilisateurs`, recherche `q` / `role` / `actif`, `PATCH /{id}/mot-de-passe`), écriture des types, modèles et champs, ordre des sections, suppressions (missions, documents du dossier, historique) et modification ou suppression des personnes et structures réservées à `ADMIN`. Un administrateur ne peut ni se désactiver, ni se supprimer, ni se retirer le rôle.
- **Visibilité par mission** (`MissionAccess`) : un `UTILISATEUR` ne voit que les missions dont sa personne fait partie de l'équipe, leurs documents, et les documents générés par lui ; `ADMIN` voit tout. Toute nouvelle lecture ou écriture liée à une mission appelle `missionAccess.checkAccess(missionId)` (403) ou filtre avec `missionAccess.keepVisible(...)`. Le compte connecté s'obtient par le port `CurrentUserProvider`, jamais directement par `SecurityContextHolder` dans un service.
- `SecurityConfig` doit garder `.cors()`, sinon les requêtes de pré-vérification (`OPTIONS`) du navigateur sont rejetées.

## Langue du code

- **Les noms de fonctions et de méthodes sont toujours en anglais** et décrivent l'action réalisée, sous la forme verbe + complément : `findMissionPersonne()`, `addParticipants()`, `buildFileName()`. Ne jamais écrire `trouverMission()`, `ajouterLignes()`, etc.
- **Tous les commentaires sont en anglais.**
- Restent en français :
  - les messages d'erreur renvoyés à l'utilisateur (`throw new IllegalArgumentException("La mission est obligatoire.")`) ;
  - les noms des entités métier, des DTO, des tables et des champs du contrat d'API (`Constat`, `RapportAudit`, `motifs`, `dateEmission`…). Ils ne sont pas traduits, y compris quand ils apparaissent dans un nom de méthode : `addRisques()`, `findConstat()`.
