package com.HealthTrack.HealthTrack;

import java.sql.Time;
import java.util.List;
import java.util.Scanner;
import java.io.Console;

public class App {

    // Méthode utilitaire pour valider l'email avec regex
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

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
        sc.nextLine();
        
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

        GestionnaireStatistiques gstStats = new GestionnaireStatistiques();
        DonneesSante dernieresDonnees = gstStats.recupererDernieresDonnees(u.getGmail());

        GestionnaireObjectifs gestionnaireObj = new GestionnaireObjectifs();
        Objectifs objectifs = gestionnaireObj.recupererObjectifs(u.getGmail());

        int objectifPas, objectifEau;
        double objectifPoids, objectifSommeil;
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

        if (dernieresDonnees != null) {
            int pas = dernieresDonnees.getPas();
            int eau = dernieresDonnees.getEau();
            double poids = dernieresDonnees.getPoids();
            double sommeil = dernieresDonnees.getSommeil();

            double pctEau = (double)eau / objectifEau * 100.0;
            double pctPas = (double)pas / objectifPas * 100.0;
            double pctSommeil = (sommeil / objectifSommeil) * 100.0;

            System.out.printf("• Hydratation : %d/%d verres aujourd'hui  [%.0f%%]\n", eau, objectifEau, pctEau);
            System.out.printf("• Pas effectués : %d / %d  [%.0f%%]\n", pas, objectifPas, pctPas);
            System.out.printf("• Poids actuel : %.1f kg (objectif : %.1f kg)\n", poids, objectifPoids);
            System.out.printf("• Sommeil : %.1f heures (objectif : %.1f heures)\n", sommeil, objectifSommeil);

            double scoreBienEtre = (pctEau + pctPas + pctSommeil) / 3.0;
            System.out.printf("• Score Bien-être global : %.0f%%\n", scoreBienEtre);
        } else {
            System.out.println("Aucune donnée de santé trouvée pour l'instant. Veuillez saisir vos données santé (menu 1).");
        }

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
                voirRapportHebdomadaire(u);
                break;
            default:
                System.out.println("❌ Choix invalide, retour au menu principal.");
                break;
        }
    }

    public static void voirRapportHebdomadaire(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=========================================");
        System.out.println("       📅 RAPPORT HEBDOMADAIRE");
        System.out.println("=========================================");

        GestionnaireStatistiques gstStats = new GestionnaireStatistiques();
        List<DonneesSante> semaine = gstStats.recupererDonneesSemaine(u.getGmail());

        if (semaine.isEmpty()) {
            System.out.println("Aucune donnée de santé pour la semaine dernière.");
        } else {
            int totalPas = 0, totalEau = 0;
            double totalPoids = 0.0, totalSommeil = 0.0;
            for (DonneesSante ds : semaine) {
                totalPas += ds.getPas();
                totalEau += ds.getEau();
                totalPoids += ds.getPoids();
                totalSommeil += ds.getSommeil();
            }
            int count = semaine.size();
            System.out.printf("Moyenne de pas       : %.0f\n", (double)totalPas / count);
            System.out.printf("Moyenne de verres eau : %.0f\n", (double)totalEau / count);
            System.out.printf("Moyenne de poids      : %.1f kg\n", totalPoids / count);
            System.out.printf("Moyenne de sommeil    : %.1f heures\n", totalSommeil / count);
        }
        System.out.println("\nAppuyez sur Entrée pour revenir au menu principal.");
        sc.nextLine();
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
            sc.nextLine();
            gestionnaireObj.enregistrerOuModifierObjectifs(u.getGmail(), nouvelObjectifPas, nouvelObjectifEau, nouvelObjectifPoids, nouvelObjectifSommeil);
        } else {
            System.out.println("Retour au menu principal...");
        }
    }

    public static void gererNotificationsEtRappels(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        GestionnaireRappels gestionnaireRappels = new GestionnaireRappels();

        System.out.println("\n=========================================");
        System.out.println("     🔔 NOTIFICATIONS & RAPPELS");
        System.out.println("=========================================");

        List<Rappel> rappels = gestionnaireRappels.listerRappels(u.getGmail());
        System.out.println("Rappels actifs :");
        if (rappels.isEmpty()) {
            System.out.println("   Aucun rappel pour le moment.");
        } else {
            for (int i = 0; i < rappels.size(); i++) {
                Rappel r = rappels.get(i);
                System.out.printf("%d) %s à %s\n", i + 1, r.getDescription(), r.getHeure().toString().substring(0, 5));
            }
        }

        System.out.println("\nQue veux-tu faire ?");
        System.out.println("[1] ➤ Ajouter un nouveau rappel ➕");
        System.out.println("[2] ➤ Supprimer un rappel ❌");
        System.out.println("[3] ➤ Modifier un rappel ✏️");
        System.out.println("[0] ➤ Retour au menu principal");
        System.out.print("Ton choix : ");
        String choix = sc.nextLine();

        switch (choix) {
            case "1":
                System.out.print("Description du rappel : ");
                String desc = sc.nextLine();
                System.out.print("Heure du rappel (format HH:MM) : ");
                String heureStr = sc.nextLine();
                try {
                    Time heureTime = Time.valueOf(heureStr + ":00");
                    gestionnaireRappels.ajouterRappel(u.getGmail(), desc, heureTime);
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Format d'heure invalide !");
                }
                break;
            case "2":
                if (rappels.isEmpty()) {
                    System.out.println("Aucun rappel à supprimer.");
                    break;
                }
                System.out.print("Numéro du rappel à supprimer : ");
                int numSupp = sc.nextInt();
                sc.nextLine();
                if (numSupp < 1 || numSupp > rappels.size()) {
                    System.out.println("❌ Numéro invalide !");
                } else {
                    int idRappelASupprimer = rappels.get(numSupp - 1).getId();
                    gestionnaireRappels.supprimerRappel(idRappelASupprimer);
                }
                break;
            case "3":
                if (rappels.isEmpty()) {
                    System.out.println("Aucun rappel à modifier.");
                    break;
                }
                System.out.print("Numéro du rappel à modifier : ");
                int numMod = sc.nextInt();
                sc.nextLine();
                if (numMod < 1 || numMod > rappels.size()) {
                    System.out.println("❌ Numéro invalide !");
                } else {
                    Rappel rappelAModifier = rappels.get(numMod - 1);
                    System.out.println("Ancienne description : " + rappelAModifier.getDescription());
                    System.out.print("Nouvelle description : ");
                    String nouvelleDesc = sc.nextLine();
                    System.out.println("Ancienne heure : " + rappelAModifier.getHeure());
                    System.out.print("Nouvelle heure (format HH:MM) : ");
                    String newHeureStr = sc.nextLine();
                    try {
                        Time newHeureTime = Time.valueOf(newHeureStr + ":00");
                        gestionnaireRappels.modifierRappel(rappelAModifier.getId(), nouvelleDesc, newHeureTime);
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Format d'heure invalide !");
                    }
                }
                break;
            case "0":
                return;
            default:
                System.out.println("❌ Choix invalide.");
                break;
        }
    }

    /*
      Menu 6 : Paramètres
     Options : Modifier ses informations personnelles (champ par champ), supprimer le compte, se déconnecter.
     Retourne l'objet Utilisateur mis à jour ou null en cas de déconnexion/suppression.
     */
    public static Utilisateur gererParametres(Utilisateur u) {
        Scanner sc = new Scanner(System.in);
        GestionnaireUtilisateurs gestionnaireUtilisateurs = new GestionnaireUtilisateurs();
        boolean continuer = true;
        boolean compteModifie = false;

        while (continuer) {
            System.out.println("\n=========================================");
            System.out.println("              ⚙️ PARAMÈTRES");
            System.out.println("=========================================");
            System.out.println("[1] ➤ Modifier mes informations personnelles");
            System.out.println("[2] ➤ Supprimer mon compte");
            System.out.println("[3] ➤ Se déconnecter");
            System.out.println("[0] ➤ Retour au menu principal");
            System.out.print("Votre choix : ");
            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    System.out.println("\n--- Modification de vos informations ---");
                    System.out.println("[1] Modifier le prénom (actuel : " + u.getPrenom() + ")");
                    System.out.println("[2] Modifier le nom (actuel : " + u.getNom() + ")");
                    System.out.println("[3] Modifier le Gmail (actuel : " + u.getGmail() + ")");
                    System.out.println("[4] Modifier le mot de passe");
                    System.out.println("[0] Terminer la modification");
                    System.out.print("Votre choix : ");
                    String modChoix = sc.nextLine();

                    // Valeurs initiales
                    String newPrenom = u.getPrenom();
                    String newNom = u.getNom();
                    String newGmail = u.getGmail();
                    String newMotDePasse = u.getMotDePasse();

                    switch (modChoix) {
                        case "1":
                            System.out.print("Nouveau prénom : ");
                            newPrenom = sc.nextLine();
                            break;
                        case "2":
                            System.out.print("Nouveau nom : ");
                            newNom = sc.nextLine();
                            break;
                        case "3":
                            boolean valide = false;
                            do {
                                System.out.print("Nouveau Gmail : ");
                                newGmail = sc.nextLine();
                                if (!isValidEmail(newGmail)) {
                                    System.out.println("Email invalide. Veuillez réessayer.");
                                } else {
                                    valide = true;
                                }
                            } while (!valide);
                            break;
                        case "4":
                            Console console = System.console();
                            if (console != null) {
                                char[] pwd = console.readPassword("Nouveau mot de passe : ");
                                newMotDePasse = new String(pwd);
                            } else {
                                System.out.print("Nouveau mot de passe : ");
                                newMotDePasse = sc.nextLine();
                            }
                            break;
                        case "0":
                            break;
                        default:
                            System.out.println("❌ Option invalide.");
                            continue;
                    }
                    if (gestionnaireUtilisateurs.modifierInfosUtilisateur(u.getGmail(), newPrenom, newNom, newGmail, newMotDePasse)) {
                        u = new Utilisateur(newPrenom, newNom, newGmail, newMotDePasse);
                        compteModifie = true;
                        System.out.println("Les informations ont été mises à jour.");
                    }
                    break;
                case "2":
                    System.out.print("Êtes-vous sûr de vouloir supprimer votre compte ? (OUI/NON) : ");
                    String confirmation = sc.nextLine();
                    if (confirmation.equalsIgnoreCase("OUI")) {
                        if (gestionnaireUtilisateurs.supprimerUtilisateur(u.getGmail())) {
                            System.out.println("Votre compte a été supprimé. Au revoir !");
                            return null;
                        }
                    } else {
                        System.out.println("Suppression annulée.");
                    }
                    break;
                case "3":
                    System.out.println("Vous êtes déconnecté.");
                    return null;
                case "0":
                    continuer = false;
                    break;
                default:
                    System.out.println("❌ Option invalide, réessayez.");
                    break;
            }
        }
        if (compteModifie) {
            System.out.println("Vos nouvelles informations seront utilisées lors du prochain affichage.");
        }
        return u;
    }

    public static void afficherAccueil(Utilisateur utilisateur) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===================================================");
        System.out.println("      👍 HEALTH TRACK CLI - Bienvenue " + utilisateur.getPrenom() + " !");
        System.out.println("===================================================\n");

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
            switch(choix) {
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
                case "5":
                    gererNotificationsEtRappels(utilisateur);
                    break;
                case "6":
                    Utilisateur utilisateurModifie = gererParametres(utilisateur);
                    if (utilisateurModifie == null) {
                        return;
                    } else {
                        utilisateur = utilisateurModifie;
                    }
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
                    
                    String gmail = "";
                    do {
                        System.out.print("Gmail : ");
                        gmail = scanner.nextLine();
                        if (!isValidEmail(gmail)) {
                            System.out.println("Email invalide. Veuillez réessayer.");
                        }
                    } while (!isValidEmail(gmail));
                    
                    String motDePasse = "";
                    Console console = System.console();
                    if (console != null) {
                        char[] pwd = console.readPassword("Mot de passe : ");
                        motDePasse = new String(pwd);
                    } else {
                        System.out.print("Mot de passe : ");
                        motDePasse = scanner.nextLine();
                    }
                    
                    gestionnaire.inscrireUtilisateur(prenom, nom, gmail, motDePasse);
                    break;
                case "2":
                    System.out.print("Gmail : ");
                    String gmailConnexion = scanner.nextLine();
                    
                    String motDePasseConnexion = "";
                    Console consoleLogin = System.console();
                    if (consoleLogin != null) {
                        char[] pwd = consoleLogin.readPassword("Mot de passe : ");
                        motDePasseConnexion = new String(pwd);
                    } else {
                        System.out.print("Mot de passe : ");
                        motDePasseConnexion = scanner.nextLine();
                    }
                    
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
