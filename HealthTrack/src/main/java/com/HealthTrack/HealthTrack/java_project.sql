CREATE database java_project;
use java_project;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prenom` text NOT NULL,
  `nom` text NOT NULL,
  `mail` text NOT NULL,
  `mot_passe` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `donnees_sante` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int(11) NOT NULL,
  `pas` int(11) NOT NULL,
  `eau` int(11) NOT NULL,
  `poids` double NOT NULL,
  `sommeil` double NOT NULL,
  `date_enregistrement` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `donnees_sante_ibfk_1` (`id_utilisateur`),
  CONSTRAINT `donnees_sante_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `humeurs_journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int(11) DEFAULT NULL,
  `humeur` int(11) DEFAULT NULL,
  `journal` varchar(255) DEFAULT NULL,
  `date_enregistrement` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `humeurs_journal_ibfk_1` (`id_utilisateur`),
  CONSTRAINT `humeurs_journal_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `objectifs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int(11) NOT NULL,
  `objectif_pas` int(11) NOT NULL,
  `objectif_eau` int(11) NOT NULL,
  `objectif_poids` double NOT NULL,
  `objectif_sommeil` double NOT NULL,
  `date_enregistrement` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `objectifs_ibfk_1` (`id_utilisateur`),
  CONSTRAINT `objectifs_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `rappels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `heure` time NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `rappels_ibfk_1` (`id_utilisateur`),
  CONSTRAINT `rappels_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;