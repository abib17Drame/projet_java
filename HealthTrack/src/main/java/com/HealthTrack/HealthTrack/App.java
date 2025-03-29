package com.HealthTrack.HealthTrack;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionnaireUtilisateurs gestionnaire = new GestionnaireUtilisateurs();
        Utilisateur utilisateurConnecte = null;

        while (utilisateurConnecte == null) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Inscription");
            System.out.println("2. Connexion");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.println("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Gmail : ");
                    String gmail = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String motDePasse = scanner.nextLine();

                    gestionnaire.inscrireUtilisateur(prenom ,nom, gmail, motDePasse);
                    break;
                case "2":
                    System.out.print("gmail : ");
                    String gmailConnexion = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String motDePasseConnexion = scanner.nextLine();
                    utilisateurConnecte = gestionnaire.connecterUtilisateur(gmailConnexion, motDePasseConnexion);

                    if (utilisateurConnecte == null) {
                        System.out.println("❌ Connexion échouée. Vérifiez vos identifiants !");
                    }
                    break;
                case "3":
                    System.out.println("👋 Au revoir !");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Option invalide, réessayez.");
            }
        }

        // Affichage de l'accueil avec des données réelles
        afficherAccueil(utilisateurConnecte, scanner);
    }

    public static void afficherAccueil(Utilisateur utilisateur, Scanner scanner) {
        System.out.println("\n===================================================");
        System.out.println("      👍 HEALTH TRACK CLI - Bienvenue ! ");
        System.out.println("===================================================\n");

        // Suppression de la gestion de la dernière connexion
        System.out.println("Bonjour " + utilisateur.getGmail() + " ! 🔥");

        System.out.println("---------------------------------------------------");
        System.out.println("Que souhaites-tu faire aujourd’hui ? ⬇️");
        System.out.println("---------------------------------------------------\n");

        while (true) {
            System.out.println("[1] ➤ Saisir mes données santé 🌿");
            System.out.println("[2] ➤ Suivi humeur & journal 🧠");
            System.out.println("[3] ➤ Voir mes statistiques 📊");
            System.out.println("[4] ➤ Gérer mes objectifs 🎯");
            System.out.println("[5] ➤ Notifications & rappels 🔔");
            System.out.println("[6] ➤ Paramètres ⚙️");
            System.out.println("[0] ➤ Quitter 🟧\n");
            System.out.print("Entre le numéro de ton choix : ");

            String choix = scanner.nextLine();
            if (choix.equals("0")) {
                System.out.println("👋 Déconnexion réussie !");
                break;
            } else {
                System.out.println("✅ Option " + choix + " sélectionnée !");
            }
        }
    }
}
