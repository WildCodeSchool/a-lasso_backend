# A l'Asso - Backend

Backend API pour l'application **A l'Asso**, une plateforme de mise en relation entre bénévoles et associations.

FrontEnd Prod : https://a-l-asso.fr/ | Stagging : https://staging.a-l-asso.fr/

BackEnd Prod : https://api.a-l-asso.fr/ | Stagging : https://staging-api.a-l-asso.fr/
Repo GitHub FrontEnd : https://github.com/WildCodeSchool/a-lasso_frontend

![front](https://i.ibb.co/4RkstDXh/image.png)

## Stack Technique

| Technologie | Version | Description |
|-------------|---------|-------------|
| Java | 21 | Langage principal |
| Spring Boot | 3.4.0 | Framework applicatif |
| Spring Security | - | Authentification & autorisation (JWT) |
| Spring Data JPA | - | ORM avec Hibernate |
| MySQL | - | Base de données relationnelle |
| Lombok | 1.18.30 | Réduction du boilerplate |
| Swagger/OpenAPI | 2.7.0 | Documentation API |
| Bucket4j | 8.10.1 | Rate limiting |
| Caffeine | 3.1.8 | Cache en mémoire |
| TestContainers | 1.19.1 | Tests d'intégration avec containers |

## Prérequis

- **Java 21** (JDK)
- **Maven 3.9+**
- **MySQL** (ou Docker pour une instance locale)
- **Node.js** (pour Prettier/Husky)

## Installation & Démarrage

### 1. Cloner le repository

```bash
git clone <repo-url>
cd a-lasso_backend
```

### 2. Installer les dépendances Node (Prettier/Husky)

```bash
npm install
```

### 3. Configurer les variables d'environnement

Créer un fichier `.env` à la racine du projet en copiant le template :

```bash
cp .env.sample .env
```

Puis compléter les valeurs :

```env
# Développement
DB_HOST_DEV=localhost
DB_PORT_DEV=3306
DB_NAME_DEV=alasso_dev
DB_USER_DEV=root
DB_PASSWORD_DEV=your_password
CLIENT_URL_DEV=http://localhost:3000
API_URL_DEV=http://localhost:8080

# JWT
JWT_SECRET_KEY=your_secret_key_here
JWT_EXP_TIME=86400000
```

### 4. Lancer l'application

```bash
# Mode développement (par défaut)
mvn spring-boot:run

# Ou avec un profil spécifique
mvn spring-boot:run -Pdevelopment
mvn spring-boot:run -Pstaging
mvn spring-boot:run -Pproduction
```

L'API sera accessible sur `http://localhost:8080`

### 5. Lancer avec Docker

```bash
docker build -t alasso-backend .
docker run -p 8080:8080 --env-file .env alasso-backend
```

## Endpoints API

### Authentification (`/auth`)
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/auth/register/voluntary` | Inscription bénévole |
| POST | `/auth/register/association` | Inscription association |
| POST | `/auth/login` | Connexion |
| PATCH | `/auth/change-password` | Changer le mot de passe |
| PATCH | `/auth/change-email` | Changer l'email |
| DELETE | `/auth/delete-account` | Supprimer le compte |

### Activités (`/activities`)
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/activities/future` | Activités à venir |
| GET | `/activities/past` | Activités passées |
| GET | `/activities/{id}` | Détail d'une activité |
| GET | `/activities/association/{id}` | Activités d'une association |
| POST | `/activities` | Créer/modifier une activité |
| PATCH | `/activities/{id}/updateFavorite` | Ajouter/retirer des favoris |
| PATCH | `/activities/{id}/updateRegistered` | S'inscrire/se désinscrire |
| DELETE | `/activities/delete/{id}` | Supprimer une activité |

### Autres endpoints
- `/associations` - Gestion des associations
- `/voluntary` - Gestion des bénévoles
- `/themes` - Thèmes d'activités
- `/messages` - Messagerie
- `/reports` - Signalements
- `/password-reset` - Réinitialisation de mot de passe

## Documentation Swagger

En mode **développement**, la documentation interactive est disponible sur :

```
http://localhost:8080/swagger-ui/index.html
```

> ⚠️ Swagger est désactivé en staging et production.

## Environnements

| Environnement | Profil Maven | Swagger | DDL Auto |
|---------------|--------------|---------|----------|
| Development | `-Pdevelopment` | Activé | `create-drop` |
| Staging | `-Pstaging` | Désactivé | - |
| Production | `-Pproduction` | Désactivé | - |

Changer d'environnement dans `application.yml` :

```yaml
spring:
  profiles:
    active:
      - development  # ou staging, production
```

## Structure du Projet

```
src/main/java/com/back_alasso/
├── config/           # Configuration Spring
├── core/             # Classes de base (ApiResponse, BaseEntity)
├── exception/        # Gestion des exceptions globales
├── features/         # Modules fonctionnels
│   ├── Activity/     # Gestion des activités
│   ├── Association/  # Gestion des associations
│   ├── Authentication/ # Auth (login, register, JWT)
│   ├── Voluntary/    # Gestion des bénévoles
│   ├── Message/      # Messagerie
│   ├── Theme/        # Thèmes d'activités
│   ├── Report/       # Signalements
│   └── ...
├── security/         # Configuration sécurité (JWT, filters)
└── shared/           # DTOs partagés
```

## Branch Protection Rules

- Lock pushes on `production`, `staging` & `development`
- PR mandatory towards `production`, `staging` & `development`
- PR, any branch → `development` : All contributors must add their review
- PR, `development` → `staging` : Only `development` can PR to `staging`
- PR, `staging` → `production` : Only `staging` can PR to `production`, owner must approve

## Qualité de Code

### Prettier (Formatage)
```bash
npm run prettier
```
- Config : `.prettierrc.json`
- S'exécute automatiquement avant chaque commit

### Checkstyle (Règles de code)
```bash
mvn checkstyle:check
```
- Config : `checkstyle.xml`
- Applique les conventions Java standards

### Husky (Git Hooks)
Avant chaque commit :
- Vérifie que les fichiers de config n'ont pas été modifiés
- Exécute Prettier automatiquement
- Valide le nom de branche

## Tests

```bash
# Tous les tests
mvn test

# Tests unitaires uniquement
mvn test -Punit-tests

# Tests d'intégration uniquement
mvn test -Pintegration-tests

# Tests E2E uniquement
mvn test -Pe2e-tests
```

## CI/CD

Pipeline GitHub Actions (`.github/workflows/`) :

| Branche | Tests exécutés | Déploiement |
|---------|----------------|-------------|
| feature/* | Unit + Integration | Non |
| staging | Unit + Integration + E2E | DockerHub → VPS staging |
| production | Unit + Integration + E2E | DockerHub → VPS production |

### Flow de déploiement
1. Push sur `staging` ou `production`
2. CI : exécution des tests
3. CD : build Docker et push sur DockerHub
4. Webhook : le VPS pull la nouvelle image et redémarre le container

## Infrastructure de Production

L'application est hébergée sur un **VPS OVH** avec une architecture full Docker orchestrée par **Traefik**.

### Architecture

![archi](https://i.ibb.co/0V1hpTk3/image-4.png)

### Stack Infrastructure

| Composant | Description |
|-----------|-------------|
| **VPS OVH** | Serveur dédié virtuel hébergeant l'ensemble des services |
| **Docker** | Conteneurisation de tous les services |
| **Traefik** | Reverse proxy avec génération automatique des certificats SSL |
| **DockerHub** | Registry pour les images Docker |
| **MySQL** | Base de données conteneurisée |

### Domaines et Routage

Traefik route automatiquement les requêtes vers les bons containers :

| Domaine | Service | Container |
|---------|---------|-----------|
| `a-l-asso.fr` | Frontend Production | frontend-prod |
| `staging.a-l-asso.fr` | Frontend Staging | frontend-staging |
| `api.a-l-asso.fr` | Backend Production | backend-prod |
| `staging-api.a-l-asso.fr` | Backend Staging | backend-staging |

### Certificats SSL

Les certificats SSL sont générés automatiquement par Traefik via **Let's Encrypt** (ACME challenge HTTP-01).

## Variables d'Environnement

| Variable | Description |
|----------|-------------|
| `DB_HOST_*` | Hôte de la base de données |
| `DB_PORT_*` | Port MySQL (défaut: 3306) |
| `DB_NAME_*` | Nom de la base de données |
| `DB_USER_*` | Utilisateur MySQL |
| `DB_PASSWORD_*` | Mot de passe MySQL |
| `CLIENT_URL_*` | URL du frontend (CORS) |
| `API_URL_*` | URL de l'API |
| `JWT_SECRET_KEY` | Clé secrète pour les tokens JWT |
| `JWT_EXP_TIME` | Durée de validité des tokens (ms) |

> `*` = `DEV`, `STAGING`, ou `PRODUCTION` selon l'environnement


## Schémas 

### MPD

![MDP](	https://i.ibb.co/Kcs8jH3H/image-1.png)

### Security Flow Login
![Login Flow](https://i.ibb.co/Q7Bz9rcR/image-2.png)

### Security Flow Request with JWT

![JWT Floww](https://i.ibb.co/rfKByVKC/image-3.png)


