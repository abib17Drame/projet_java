package com.HealthTrack.HealthTrack;

public class Utilisateur {
    private String prenom;
    private String nom;
    private String gmail;
    private String motDePasse;

    public Utilisateur(String prenom, String nom, String gmail, String motDePasse) {
        this.prenom = prenom;
        this.nom = nom;
        this.gmail = gmail;
        this.motDePasse = motDePasse;
    }

    public String getGmail() {
        return gmail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }
}
