package com.HealthTrack.HealthTrack;

import java.util.List;
import java.util.ArrayList;

class GestionnaireUtilisateurs {
    private List<Utilisateur> listeUtilisateurs;

    public GestionnaireUtilisateurs() {
        this.listeUtilisateurs = new ArrayList<>();
    }

    // Inscription d'un utilisateur
    public boolean inscrireUtilisateur(String prenom, String nom, String gmail, String motDePasse) {
        for (Utilisateur u : listeUtilisateurs) {
            if (u.getGmail().equalsIgnoreCase(gmail)) {
                System.out.println("⚠️ Ce gmail est déjà pris !");
                return false;
            }
        }
        Utilisateur nouvelUtilisateur = new Utilisateur(prenom, nom, gmail, motDePasse);
        listeUtilisateurs.add(nouvelUtilisateur);
        System.out.println("✅ Inscription réussie pour " + gmail + " !");
        return true;
    }

    // Connexion d'un utilisateur
    public Utilisateur connecterUtilisateur(String gmail, String motDePasse) {
        for (Utilisateur u : listeUtilisateurs) {
            if (u.getGmail().equalsIgnoreCase(gmail) && u.getMotDePasse().equals(motDePasse)) {
                System.out.println("✅ Connexion réussie, bienvenue " + gmail + " !");
                return u;
            }
        }
        System.out.println("❌ gmail ou mot de passe incorrect !");
        return null;
    }
}
