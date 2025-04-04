package com.HealthTrack.HealthTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionnaireHumeurJournal {

	public void enregistrerHumeur(String gmail, int humeur, String journal) {
	    try (Connection con = ConnexionDB.connectDB();
	         Statement stmt = con.createStatement()) {

	        // Récupération de l'ID de l'utilisateur via son gmail
	        String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
	        ResultSet rs = stmt.executeQuery(requeteUser);

	        if (rs.next()) {
	            int idUtilisateur = rs.getInt("id");

	            // Échapper les apostrophes pour éviter les erreurs SQL
	            journal = journal.replace("'", "''");

	            // Insertion de l'humeur et du journal dans la base de données
	            String requeteInsertion = "INSERT INTO humeurs_journal (id_utilisateur, humeur, journal, date_enregistrement) " +
	                                      "VALUES (" + idUtilisateur + ", " + humeur + ", '" + journal + "', NOW())";

	            int lignesAffectees = stmt.executeUpdate(requeteInsertion);
	            if (lignesAffectees > 0) {
	                System.out.println("✅ Humeur et journal enregistrés !");
	            } else {
	                System.out.println("❌ Échec de l'enregistrement.");
	            }
	        } else {
	            System.out.println("❌ Utilisateur non trouvé !");
	        }

	    } catch (SQLException e) {
	        System.err.println("❌ Erreur SQL : " + e.getMessage());
	    }
	}

}
