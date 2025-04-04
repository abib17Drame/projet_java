package com.HealthTrack.HealthTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireRappels {

    /**
     * Récupère l'id d'un utilisateur à partir de son mail.
     * Retourne -1 si l'utilisateur n'existe pas.
     */
    private int getIdUtilisateur(String gmail) {
        int idUser = -1;
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {
            String query = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                idUser = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (getIdUtilisateur) : " + e.getMessage());
        }
        return idUser;
    }

    /**
     * Liste tous les rappels d'un utilisateur (par ordre d'heure croissant).
     */
    public List<Rappel> listerRappels(String gmail) {
        List<Rappel> liste = new ArrayList<>();
        int idUser = getIdUtilisateur(gmail);
        if (idUser == -1) {
            return liste;
        }

        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM rappels " +
                           "WHERE id_utilisateur = " + idUser +
                           " ORDER BY heure ASC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idUtilisateur = rs.getInt("id_utilisateur");
                String description = rs.getString("description");
                Time heure = rs.getTime("heure");
                Rappel rappel = new Rappel(id, idUtilisateur, description, heure);
                liste.add(rappel);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (listerRappels) : " + e.getMessage());
        }
        return liste;
    }

    /**
     * Ajoute un nouveau rappel pour l'utilisateur.
     */
    public void ajouterRappel(String gmail, String description, Time heure) {
        int idUser = getIdUtilisateur(gmail);
        if (idUser == -1) {
            System.out.println("❌ Utilisateur introuvable !");
            return;
        }

        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {
            // Échapper les apostrophes dans la description
            description = description.replace("'", "''");

            String query = "INSERT INTO rappels (id_utilisateur, description, heure, date_creation) " +
                           "VALUES (" + idUser + ", '" + description + "', '" + heure + "', NOW())";
            int lignes = stmt.executeUpdate(query);
            if (lignes > 0) {
                System.out.println("✅ Nouveau rappel ajouté avec succès !");
            } else {
                System.out.println("❌ Échec de l'ajout du rappel.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (ajouterRappel) : " + e.getMessage());
        }
    }

    /**
     * Supprime un rappel par son ID.
     */
    public void supprimerRappel(int idRappel) {
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {
            String query = "DELETE FROM rappels WHERE id = " + idRappel;
            int lignes = stmt.executeUpdate(query);
            if (lignes > 0) {
                System.out.println("✅ Rappel supprimé avec succès !");
            } else {
                System.out.println("❌ Aucun rappel avec cet ID !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (supprimerRappel) : " + e.getMessage());
        }
    }

    /**
     * Modifie la description et l'heure d'un rappel.
     */
    public void modifierRappel(int idRappel, String nouvelleDescription, Time nouvelleHeure) {
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            nouvelleDescription = nouvelleDescription.replace("'", "''");

            String query = "UPDATE rappels SET " +
                           "description = '" + nouvelleDescription + "', " +
                           "heure = '" + nouvelleHeure + "' " +
                           "WHERE id = " + idRappel;
            int lignes = stmt.executeUpdate(query);
            if (lignes > 0) {
                System.out.println("✅ Rappel modifié avec succès !");
            } else {
                System.out.println("❌ Aucune mise à jour effectuée (ID invalide ?).");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (modifierRappel) : " + e.getMessage());
        }
    }
}
