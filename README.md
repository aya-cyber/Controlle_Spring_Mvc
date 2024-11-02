# Système de Gestion des Étudiants et des Cours

Ce projet est une application web développée avec **Spring Boot** et **Thymeleaf**, permettant de gérer les étudiants et les cours associés. Il offre des fonctionnalités **CRUD** (Créer, Lire, Mettre à jour, Supprimer) pour les étudiants, les cours, et les inscriptions des étudiants aux cours.

## Fonctionnalités

- **Gestion des Étudiants** :
  - Ajouter un nouvel étudiant.
  - Consulter la liste des étudiants.
  - Modifier les informations d'un étudiant.
  - Supprimer un étudiant.

- **Gestion des Cours** :
  - Ajouter un nouveau cours.
  - Consulter la liste des cours.
  - Modifier les informations d'un cours.
  - Supprimer un cours.

- **Gestion des Inscriptions** :
  - Inscrire un étudiant à un cours.
  - Consulter les inscriptions des étudiants.
  - Annuler l'inscription d'un étudiant à un cours.

## Prérequis

- **Java 17** ou supérieur.
- **Maven 3.x**.
- Un IDE compatible avec **Spring Boot** (par exemple, IntelliJ IDEA ou Eclipse).
- **MySQL** pour la base de données.

## Structure du projet 

La structure du projet est organisée comme suit :

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── projet/
│   │           └── gestionDesEtudiantsEtCours/
│   │               ├── controllers/
│   │               ├── entities/
│   │               ├── services/
│   │               └── repositories/
│   └── resources/
│       ├── templates/
│       └── application.properties
```

- **src/main/java/com/projet/gestionDesEtudiantsEtCours** :

  - **controllers** : Contient les classes contrôleurs gérant les requêtes HTTP pour les opérations de gestion des étudiants et des cours.
  - **entities** : Contient les classes entités représentant les modèles de données (Étudiant, Cours, Inscription).
  - **services** : Contient les classes de service pour la logique métier, gérant les interactions entre les contrôleurs et les dépôts.
  - **repositories** : Contient les interfaces des dépôts pour l'accès aux données (repos Étudiant, Cours, Inscription).

- **src/main/resources/templates** : Contient les fichiers Thymeleaf pour les vues (pages HTML de l'application).
  
- **src/main/resources/application.properties** : Fichier de configuration de l'application pour les paramètres de connexion à la base de données MySQL et d'autres configurations.

## Technologies Utilisées

- **Spring Boot** : Framework pour la création de l'application web.
- **Thymeleaf** : Moteur de template pour générer des pages HTML côté serveur.
- **Spring Data JPA** : Pour l'accès aux données et l'interaction avec la base de données MySQL.
- **MySQL** : Système de gestion de base de données.
  
## Dépendances

Voici les principales dépendances utilisées dans le projet :

- **Spring Web** : Pour créer des applications web.
- **Spring Data JPA** : Pour la gestion des données.
- **Thymeleaf** : Pour le rendu des templates HTML.
- **JDBC Driver** : Pour la connexion à la base de données MySQL.
- **Spring DevTools** : Pour le rechargement automatique en développement.

## Base de Données

- **MySQL** : La base de données utilise MySQL pour stocker les informations des étudiants et des cours.
- **JPA** avec **Spring Data** : Pour la gestion des entités et des opérations CRUD.
- **Repositories extensibles** (CrudRepository) : Pour faciliter l'accès aux données.

## Améliorations Futures

- **Authentification et Autorisation** : Implémentation de rôles pour limiter l'accès selon le type d'utilisateur (administrateur, étudiant, professeur).
- **API REST** : Mise en place d'une API REST pour intégrer le système avec d'autres applications.
- **Tableau de Bord** : Ajouter un tableau de bord pour les statistiques (inscriptions par cours, étudiants inscrits, etc.).
- **Tests Unitaires et d'Intégration** : Pour garantir la fiabilité de l'application.

---

Ce guide devrait vous permettre de comprendre l'organisation et les fonctionnalités de votre projet Système de Gestion des Étudiants et des Cours, ainsi que les améliorations possibles pour le futur.



https://github.com/user-attachments/assets/70755ced-c77e-4a97-8dcf-38a3ae507a8b

