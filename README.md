# HealthTrack : Application de Suivi de Santé

## À propos du projet

**HealthTrack** est une application de bureau développée en Java qui permet aux utilisateurs de suivre leurs données de santé, de définir des objectifs, de gérer des rappels pour les médicaments ou les rendez-vous, et de suivre leur humeur quotidienne. Ce projet a été réalisé dans le cadre de notre deuxième année en DUT Génie Informatique.

### Contexte

Ce projet a été développé par des étudiants de l'**École Supérieure Polytechnique (ESP) de Dakar** pour l'unité d'enseignement de `Projet Java` en deuxième année de **DUT Génie Informatique**.

### Auteurs

*   **Boubacar LY**
*   **Ndeye Awa SENE**
*   **Mouhamadou Abib DRAME**

---

## Fonctionnalités

*   **Gestion des utilisateurs** : Création de compte et authentification.
*   **Suivi des données de santé** : Enregistrez des informations comme le poids, la taille, la tension artérielle, etc.
*   **Gestion des objectifs** : Définissez et suivez des objectifs de santé personnalisés (ex: perte de poids, nombre de pas).
*   **Journal de l'humeur** : Tenez un journal quotidien de votre humeur et de vos notes personnelles.
*   **Rappels** : Configurez des rappels pour la prise de médicaments, les rendez-vous médicaux ou d'autres activités.
*   **Statistiques** : Visualisez des statistiques et des graphiques sur vos progrès et vos données de santé.

---

## Technologies utilisées

*   **Langage** : Java (JDK 17)
*   **Gestion de projet** : Apache Maven
*   **Base de données** : MySQL
*   **Tests** : JUnit 5

---

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les logiciels suivants sur votre machine :

*   [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou une version supérieure
*   [Apache Maven](https://maven.apache.org/download.cgi)
*   [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## Installation et Lancement

Suivez ces étapes pour configurer et lancer le projet localement.

1.  **Clonez le dépôt**

    ```bash
    git clone <URL_DU_DEPOT_GIT>
    cd projet_java/HealthTrack
    ```

2.  **Configurez la base de données**

    *   Démarrez votre serveur MySQL.
    *   Créez une nouvelle base de données. Vous pouvez utiliser le script SQL fourni `java_project.sql` qui se trouve dans `HealthTrack/src/main/java/com/HealthTrack/HealthTrack/`.
    *   Assurez-vous que les informations de connexion à la base de données dans le fichier `src/main/java/com/HealthTrack/HealthTrack/ConnexionDB.java` correspondent à votre configuration locale (URL, nom d'utilisateur, mot de passe).

3.  **Compilez le projet avec Maven**

    Ouvrez un terminal dans le répertoire `HealthTrack` (où se trouve le `pom.xml`) et exécutez la commande suivante :

    ```bash
    mvn clean install
    ```

4.  **Exécutez l'application**

    Une fois la compilation terminée, vous pouvez lancer l'application avec la commande suivante :

    ```bash
    mvn exec:java -Dexec.mainClass="com.HealthTrack.HealthTrack.App"
    ```

---

## Licence

Ce projet est distribué sous la licence MIT. Voir le fichier `LICENSE` pour plus de détails.