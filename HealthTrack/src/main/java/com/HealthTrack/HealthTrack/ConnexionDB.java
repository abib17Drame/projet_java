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
            String urlBD = p.getProperty("jdbc.url");
            String user = p.getProperty("jdbc.login");
            String password = p.getProperty("jdbc.password");

            con = DriverManager.getConnection(urlBD, user, password);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
