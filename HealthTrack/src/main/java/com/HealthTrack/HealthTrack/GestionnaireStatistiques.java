package com.HealthTrack.HealthTrack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireStatistiques {

    public DonneesSante recupererDernieresDonnees(String gmail) {
        DonneesSante donnees = null;
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rsUser = stmt.executeQuery(requeteUser);

            if (rsUser.next()) {
                int idUtilisateur = rsUser.getInt("id");

                String requeteData = "SELECT pas, eau, poids, sommeil, date_enregistrement " +
                        "FROM donnees_sante WHERE id_utilisateur = " + idUtilisateur +
                        " ORDER BY date_enregistrement DESC LIMIT 1";
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

    public List<DonneesSante> recupererDonneesSemaine(String gmail) {
        List<DonneesSante> donneesSemaine = new ArrayList<>();
        try (Connection con = ConnexionDB.connectDB();
             Statement stmt = con.createStatement()) {

            String requeteUser = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rsUser = stmt.executeQuery(requeteUser);

            if (rsUser.next()) {
                int idUtilisateur = rsUser.getInt("id");
                String requeteData = "SELECT pas, eau, poids, sommeil, date_enregistrement " +
                        "FROM donnees_sante WHERE id_utilisateur = " + idUtilisateur +
                        " AND date_enregistrement >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
                        "ORDER BY date_enregistrement ASC";
                ResultSet rsData = stmt.executeQuery(requeteData);

                while (rsData.next()) {
                    int pas = rsData.getInt("pas");
                    int eau = rsData.getInt("eau");
                    double poids = rsData.getDouble("poids");
                    double sommeil = rsData.getDouble("sommeil");
                    DonneesSante ds = new DonneesSante(pas, eau, poids, sommeil);
                    donneesSemaine.add(ds);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL (donnees semaine) : " + e.getMessage());
        }
        return donneesSemaine;
    }
}
