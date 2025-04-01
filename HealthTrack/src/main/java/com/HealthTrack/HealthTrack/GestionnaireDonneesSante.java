package com.HealthTrack.HealthTrack;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class GestionnaireDonneesSante {

    public void enregistrerDonneesSante(String gmail, int pas, int eau, double poids, double sommeil) {
        try (Connection con = ConnexionDB.connectDB();
             Statement s = con.createStatement()) {
            String id = "SELECT id FROM users WHERE mail = '" + gmail + "'";
            ResultSet rs = s.executeQuery(id);

            if (rs.next()) {
                int idUtilisateur = rs.getInt("id");
                String requete = "INSERT INTO donnees_sante (id_utilisateur, pas, eau, poids, sommeil, date_enregistrement) " +
                                          "VALUES (" + idUtilisateur + ", " + pas + ", " + eau + ", " + poids + ", " + sommeil + ", NOW())";

                int lignesAffectees = s.executeUpdate(requete);
                if (lignesAffectees > 0) {
                    System.out.println("✅ Données de santé enregistrées avec succès !");
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
