package com.HealthTrack.HealthTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionnaireUtilisateurs {

    public boolean inscrireUtilisateur(String prenom, String nom, String gmail, String motDePasse) {
        try (Connection con = ConnexionDB.connectDB();
             Statement s = con.createStatement()) {
             
            String requeteInsertion = "INSERT INTO users (prenom, nom, mail, mot_passe) VALUES ('" 
                                      + prenom + "', '" + nom + "', '" + gmail + "', '" + motDePasse + "')";
            int lignesAffectees = s.executeUpdate(requeteInsertion);

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
             Statement s = con.createStatement()) {
             
            String requeteConnexion = "SELECT * FROM users WHERE mail = '" + gmail 
                                      + "' AND mot_passe = '" + motDePasse + "'";
            ResultSet rs = s.executeQuery(requeteConnexion);

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

    public boolean modifierInfosUtilisateur(String oldGmail, String newPrenom, String newNom, String newGmail, String newMotDePasse) {
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            String query = "UPDATE users SET prenom = '" + newPrenom + "', nom = '" + newNom +
                           "', mail = '" + newGmail + "', mot_passe = '" + newMotDePasse + "' " +
                           "WHERE mail = '" + oldGmail + "'";
            int rows = stmt.executeUpdate(query);
            if (rows > 0) {
                System.out.println("✅ Informations mises à jour !");
                return true;
            } else {
                System.out.println("❌ Aucune mise à jour effectuée.");
                return false;
            }
        } catch(SQLException e) {
            System.err.println("❌ Erreur SQL (modifierInfosUtilisateur) : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerUtilisateur(String gmail) {
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            String query = "DELETE FROM users WHERE mail = '" + gmail + "'";
            int rows = stmt.executeUpdate(query);
            if (rows > 0) {
                System.out.println("✅ Compte supprimé avec succès.");
                return true;
            } else {
                System.out.println("❌ Suppression échouée.");
                return false;
            }
        } catch(SQLException e) {
            System.err.println("❌ Erreur SQL (supprimerUtilisateur) : " + e.getMessage());
            return false;
        }
    }
}
