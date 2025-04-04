package com.HealthTrack.HealthTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionnaireObjectifs {

    /**
     * Récupère les objectifs de l'utilisateur à partir de son gmail.
     * Retourne null si aucun objectif n'est défini.
     */
    public Objectifs recupererObjectifs(String gmail) {
        Objectifs objectifs = null;
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            // Récupérer l'ID de l'utilisateur
            String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rsUser = stmt.executeQuery(requeteUser);

            if (rsUser.next()) {
                int idUtilisateur = rsUser.getInt("id");

                // Rechercher les objectifs pour cet utilisateur
                String requeteObj = "SELECT objectif_pas, objectif_eau, objectif_poids, objectif_sommeil " +
                                    "FROM objectifs WHERE id_utilisateur = " + idUtilisateur;
                ResultSet rsObj = stmt.executeQuery(requeteObj);

                if (rsObj.next()) {
                    int objectifPas = rsObj.getInt("objectif_pas");
                    int objectifEau = rsObj.getInt("objectif_eau");
                    double objectifPoids = rsObj.getDouble("objectif_poids");
                    double objectifSommeil = rsObj.getDouble("objectif_sommeil");
                    objectifs = new Objectifs(objectifPas, objectifEau, objectifPoids, objectifSommeil);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (objectifs) : " + e.getMessage());
        }
        return objectifs;
    }

    /**
     * Enregistre ou met à jour les objectifs de l'utilisateur.
     * S'il n'existe pas, on insère une nouvelle ligne, sinon on met à jour.
     */
    public void enregistrerOuModifierObjectifs(String gmail, int objectifPas, int objectifEau, double objectifPoids, double objectifSommeil) {
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            // Récupérer l'ID de l'utilisateur
            String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rsUser = stmt.executeQuery(requeteUser);
            if (rsUser.next()) {
                int idUtilisateur = rsUser.getInt("id");

                // Vérifier si des objectifs existent déjà pour cet utilisateur
                String requeteCheck = "SELECT id FROM objectifs WHERE id_utilisateur = " + idUtilisateur;
                ResultSet rsCheck = stmt.executeQuery(requeteCheck);
                if (rsCheck.next()) {
                    // Mise à jour des objectifs existants
                    String requeteUpdate = "UPDATE objectifs SET " +
                            "objectif_pas = " + objectifPas + ", " +
                            "objectif_eau = " + objectifEau + ", " +
                            "objectif_poids = " + objectifPoids + ", " +
                            "objectif_sommeil = " + objectifSommeil + ", " +
                            "date_enregistrement = NOW() " +
                            "WHERE id_utilisateur = " + idUtilisateur;
                    int lignesAffectees = stmt.executeUpdate(requeteUpdate);
                    if (lignesAffectees > 0) {
                        System.out.println("✅ Objectifs mis à jour avec succès !");
                    } else {
                        System.out.println("❌ Échec de la mise à jour des objectifs.");
                    }
                } else {
                    // Insertion d'un nouvel enregistrement d'objectifs
                    String requeteInsert = "INSERT INTO objectifs (id_utilisateur, objectif_pas, objectif_eau, objectif_poids, objectif_sommeil, date_enregistrement) " +
                            "VALUES (" + idUtilisateur + ", " + objectifPas + ", " + objectifEau + ", " + objectifPoids + ", " + objectifSommeil + ", NOW())";
                    int lignesAffectees = stmt.executeUpdate(requeteInsert);
                    if (lignesAffectees > 0) {
                        System.out.println("✅ Objectifs enregistrés avec succès !");
                    } else {
                        System.out.println("❌ Échec de l'enregistrement des objectifs.");
                    }
                }
            } else {
                System.out.println("❌ Utilisateur non trouvé !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (objectifs) : " + e.getMessage());
        }
    }
}
