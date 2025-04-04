package com.HealthTrack.HealthTrack; 

import java.util.Scanner;

public class App {

    public static void saisirHumeurJournal(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=========================================");
        System.out.println("      🧠 SUIVI HUMEUR & JOURNAL       ");
        System.out.println("=========================================");

        System.out.println("Comment te sens-tu aujourd'hui ?");
        System.out.println("[1] Très mal 😞");
        System.out.println("[2] Mal 😟");
        System.out.println("[3] Moyen 😐");
        System.out.println("[4] Bien 🙂");
        System.out.println("[5] Très bien 😃");
        
        System.out.print("\nTon choix : ");
        int humeur = sc.nextInt();
        sc.nextLine(); // Consommer le retour à la ligne
        
        System.out.println("\n📝 Écris une note pour aujourd'hui (facultatif) : ");
        String journal = sc.nextLine();

        GestionnaireHumeurJournal gestionnaireHumeur = new GestionnaireHumeurJournal();
        gestionnaireHumeur.enregistrerHumeur(u.getGmail(), humeur, journal);

        System.out.println("\n✅ Ton humeur et journal ont été enregistrés avec succès !");
    }

    public static void SaisirDonneesSante(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n📋 Enregistrement des données santé !");
        System.out.print("Nombre de pas : ");
        int pas = sc.nextInt();
        System.out.print("Litres d'eau bus : ");
        int eau = sc.nextInt();
        System.out.print("Poids (kg) : ");
        double poids = sc.nextDouble();
        System.out.print("Heures de sommeil : ");
        double sommeil = sc.nextDouble();
        
        GestionnaireDonneesSante gestionnaireSante = new GestionnaireDonneesSante();
        gestionnaireSante.enregistrerDonneesSante(u.getGmail(), pas, eau, poids, sommeil);
    }
    
    public static void voirStatistiques(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n=========================================");
        System.out.println("       📊 STATISTIQUES SANTÉ");
        System.out.println("=========================================");

        // Récupérer les dernières données santé de l'utilisateur
        GestionnaireStatistiques gstStats = new GestionnaireStatistiques();
        DonneesSante dernieresDonnees = gstStats.recupererDernieresDonnees(u.getGmail());

        // Récupérer les objectifs définis par l'utilisateur
        GestionnaireObjectifs gestionnaireObj = new GestionnaireObjectifs();
        Objectifs objectifs = gestionnaireObj.recupererObjectifs(u.getGmail());

        // Si l'utilisateur a défini des objectifs, on les utilise ; sinon, on prend des valeurs par défaut
        int objectifPas;
        int objectifEau;
        double objectifPoids;
        double objectifSommeil;
        if (objectifs != null) {
            objectifPas = objectifs.getObjectifPas();
            objectifEau = objectifs.getObjectifEau();
            objectifPoids = objectifs.getObjectifPoids();
            objectifSommeil = objectifs.getObjectifSommeil();
        } else {
            objectifPas = 10000;
            objectifEau = 8;
            objectifPoids = 70.0;
            objectifSommeil = 8.0;
        }

        // Affichage des données santé et calcul des pourcentages en fonction des objectifs
        if (dernieresDonnees != null) {
            int pas = dernieresDonnees.getPas();
            int eau = dernieresDonnees.getEau();
            double poids = dernieresDonnees.getPoids();
            double sommeil = dernieresDonnees.getSommeil();

            double pctEau = (double) eau / objectifEau * 100.0;
            double pctPas = (double) pas / objectifPas * 100.0;
            double pctSommeil = (sommeil / objectifSommeil) * 100.0;

            System.out.printf("• Hydratation : %d/%d verres aujourd'hui  [%.0f%%]\n", 
                              eau, objectifEau, pctEau);
            System.out.printf("• Pas effectués : %d / %d  [%.0f%%]\n", 
                              pas, objectifPas, pctPas);
            System.out.printf("• Poids actuel : %.1f kg (objectif : %.1f kg)\n", 
                              poids, objectifPoids);
            System.out.printf("• Sommeil : %.1f heures (objectif : %.1f heures)\n", 
                              sommeil, objectifSommeil);

            double scoreBienEtre = (pctEau + pctPas + pctSommeil) / 3.0;
            System.out.printf("• Score Bien-être global : %.0f%%\n", scoreBienEtre);
        } else {
            System.out.println("Aucune donnée de santé trouvée pour l'instant. Veuillez saisir vos données santé (menu 1).");
        }

        // Affichage des objectifs de l'utilisateur
        System.out.println("-----------------------------------------");
        if (objectifs != null) {
            System.out.println("Vos objectifs actuels :");
            System.out.printf("   - Pas : %d\n", objectifPas);
            System.out.printf("   - Eau (verres) : %d\n", objectifEau);
            System.out.printf("   - Poids : %.1f kg\n", objectifPoids);
            System.out.printf("   - Sommeil : %.1f heures\n", objectifSommeil);
        } else {
            System.out.println("Aucun objectif défini pour le moment.");
        }
        System.out.println("-----------------------------------------");
        System.out.println("[1] ➤ Retour au menu principal");
        System.out.println("[2] ➤ Voir le rapport hebdomadaire");
        System.out.println("-----------------------------------------");
        System.out.print("Votre choix : ");
        String choix = sc.nextLine();

        switch (choix) {
            case "1":
                return;
            case "2":
                System.out.println("\n🔎 Rapport hebdomadaire (non implémenté).");
                System.out.println("Revenons au menu principal...");
                break;
            default:
                System.out.println("❌ Choix invalide, retour au menu principal.");
                break;
        }
    }

    public static void gererObjectifs(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=========================================");
        System.out.println("         🎯 GÉRER MES OBJECTIFS");
        System.out.println("=========================================");

        GestionnaireObjectifs gestionnaireObj = new GestionnaireObjectifs();
        Objectifs objectifs = gestionnaireObj.recupererObjectifs(u.getGmail());

        if (objectifs != null) {
            System.out.println("Vos objectifs actuels :");
            System.out.printf("• Pas : %d\n", objectifs.getObjectifPas());
            System.out.printf("• Eau (verres) : %d\n", objectifs.getObjectifEau());
            System.out.printf("• Poids : %.1f kg\n", objectifs.getObjectifPoids());
            System.out.printf("• Sommeil : %.1f heures\n", objectifs.getObjectifSommeil());
        } else {
            System.out.println("Aucun objectif défini pour le moment.");
        }

        System.out.println("-----------------------------------------");
        System.out.println("Souhaitez-vous définir/modifier vos objectifs ?");
        System.out.println("[1] Oui");
        System.out.println("[2] Non (retour au menu principal)");
        System.out.print("Votre choix : ");
        String choix = sc.nextLine();

        if (choix.equals("1")) {
            System.out.print("Nouvel objectif - Nombre de pas : ");
            int nouvelObjectifPas = sc.nextInt();
            System.out.print("Nouvel objectif - Nombre de verres d'eau : ");
            int nouvelObjectifEau = sc.nextInt();
            System.out.print("Nouvel objectif - Poids (kg) : ");
            double nouvelObjectifPoids = sc.nextDouble();
            System.out.print("Nouvel objectif - Heures de sommeil : ");
            double nouvelObjectifSommeil = sc.nextDouble();
            sc.nextLine(); // Consommer le retour à la ligne

            gestionnaireObj.enregistrerOuModifierObjectifs(u.getGmail(), nouvelObjectifPas, nouvelObjectifEau, nouvelObjectifPoids, nouvelObjectifSommeil);
        } else {
            System.out.println("Retour au menu principal...");
        }
    }

    public static void afficherAccueil(Utilisateur utilisateur) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===================================================");
        System.out.println("      👍 HEALTH TRACK CLI - Bienvenue ! ");
        System.out.println("===================================================\n");

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
            switch (choix) {
                case "1": 
                    SaisirDonneesSante(utilisateur); 
                    break;
                case "2": 
                    saisirHumeurJournal(utilisateur); 
                    break;
                case "3":
                    voirStatistiques(utilisateur);
                    break;
                case "4":
                    gererObjectifs(utilisateur);
                    break;
                case "0": 
                    System.out.println("Au revoir");
                    scanner.close(); 
                    return;
                default:
                    System.out.println("❌ Option invalide, réessayez.");
            }
        }
    }

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
                    System.out.print("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Gmail : ");
                    String gmail = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String motDePasse = scanner.nextLine();

                    gestionnaire.inscrireUtilisateur(prenom, nom, gmail, motDePasse);
                    break;
                case "2":
                    System.out.print("Gmail : ");
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
        afficherAccueil(utilisateurConnecte);
    }
}
