package com.HealthTrack.HealthTrack; 
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnexionDB {

    public static Connection connectDB() {
        Connection con = null;
        try {
            Properties p = new Properties();
            try (InputStream file = ConnexionDB.class.getClassLoader().getResourceAsStream("Config.properties")) {
                if (file == null) {
                    throw new IllegalArgumentException("❌ Fichier Config.properties introuvable !");
                }
                p.load(file);
            }

            Class.forName("com.mysql.cj.jdbc.Driver"); 
            System.out.println("✅ Pilote chargé avec succès.");

            String urlBD = p.getProperty("jdbc.url");
            String user = p.getProperty("jdbc.login");
            String password = p.getProperty("jdbc.password");

            con = DriverManager.getConnection(urlBD, user, password);
            System.out.println("✅ Connexion à la base de données réussie.");
        } catch (Exception e) {
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }


    public static void main(String[] args) throws SQLException {
        Connection connexion = connectDB();
        Statement s = connexion.createStatement();
        System.out.println("🎉 Connexion établie avec succès !");
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "prenom TEXT NOT NULL, " +
                "nom TEXT NOT NULL, " +
                "mail TEXT NOT NULL UNIQUE, " +
                "mot_passe TEXT NOT NULL)";

		s.executeUpdate(sql);
    }
}
