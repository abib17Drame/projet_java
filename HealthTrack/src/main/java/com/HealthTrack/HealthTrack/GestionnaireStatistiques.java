package com.HealthTrack.HealthTrack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionnaireStatistiques {

    /**
     * Récupère la dernière ligne de données_sante pour l'utilisateur donné (triée par date_enregistrement DESC).
     */
    public DonneesSante recupererDernieresDonnees(String gmail) {
        DonneesSante donnees = null;
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            // Récupération de l'id utilisateur
            String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rsUser = stmt.executeQuery(requeteUser);

            if (rsUser.next()) {
                int idUtilisateur = rsUser.getInt("id");

                // Récupérer la dernière saisie de donnees_sante
                String requeteData = 
                    "SELECT pas, eau, poids, sommeil, date_enregistrement " +
                    "FROM donnees_sante " +
                    "WHERE id_utilisateur = " + idUtilisateur + " " +
                    "ORDER BY date_enregistrement DESC LIMIT 1";

                ResultSet rsData = stmt.executeQuery(requeteData);

                if (rsData.next()) {
                    int pas = rsData.getInt("pas");
                    int eau = rsData.getInt("eau");
                    double poids = rsData.getDouble("poids");
                    double sommeil = rsData.getDouble("sommeil");
                    donnees = new DonneesSante(pas, eau, poids, sommeil);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (statistiques) : " + e.getMessage());
        }
        return donnees;
    }
}
