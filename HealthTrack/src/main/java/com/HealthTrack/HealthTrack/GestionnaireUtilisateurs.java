package com.HealthTrack.HealthTrack;

import java.sql.*;

public class GestionnaireUtilisateurs {

    public boolean inscrireUtilisateur(String prenom, String nom, String gmail, String motDePasse) {
        try (Connection con = ConnexionDB.connectDB();
             Statement p = con.createStatement()) {
             
            String requeteInsertion = "INSERT INTO users (prenom, nom, mail, mot_passe) VALUES ('" 
                                      + prenom + "', '" + nom + "', '" + gmail + "', '" + motDePasse + "')";
            int lignesAffectees = p.executeUpdate(requeteInsertion);

            if (lignesAffectees > 0) {
                System.out.println("✅ Inscription réussie pour " + gmail + " !");
                return true;
            } else {
                System.out.println("❌ Problème lors de l'inscription.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'insertion dans la base de données : " + e.getMessage());
            return false;
        }
    }

    public Utilisateur connecterUtilisateur(String gmail, String motDePasse) {
        try (Connection con = ConnexionDB.connectDB();
             Statement p = con.createStatement()) {
             
            String requeteConnexion = "SELECT * FROM users WHERE mail = '" + gmail 
                                      + "' AND mot_passe = '" + motDePasse + "'";

            ResultSet rs = p.executeQuery(requeteConnexion);

            if (rs.next()) {
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String gmailResult = rs.getString("mail");
                String motDePasseResult = rs.getString("mot_passe");

                Utilisateur utilisateur = new Utilisateur(prenom, nom, gmailResult, motDePasseResult);
                System.out.println("✅ Connexion réussie, bienvenue " + gmail + " !");
                return utilisateur;
            } else {
                System.out.println("❌ Gmail ou mot de passe incorrect !");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la connexion : " + e.getMessage());
            return null;
        }
    }
}
