package com.mycompany.windowmanager;

import io.github.cdimascio.dotenv.Dotenv;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConDB {

    private static ConDB instance;
    
    // Informations de connexion
    private static final Dotenv dotenv = Dotenv.load();

    private static final String HOST = dotenv.get("DB_HOST");
    private static final String PORT = dotenv.get("DB_PORT");
    private static final String DATABASE = dotenv.get("DB_NAME");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");
    private static final String DBTYPE = dotenv.get("DB_TYPE");

    private static final String URL ="jdbc:" + DBTYPE + "://" + HOST + ":" + PORT + "/" + DATABASE;
    
    // Constructeur
    private ConDB() {}
    
    public static ConDB getCon(){
        if (ConDB.instance == null){
            ConDB.instance = new ConDB();
        }
        
        return ConDB.instance;
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
// Ajouts dans la base de donnee
    public void addToTable(String userName, String password) throws SQLException {

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, userName);
            requete.setString(2, password);

            requete.executeUpdate();

            System.out.println("Utilisateur ajouté.");
        }
    }
    public void addToTable(String title, String description, String pay, int ownerId) throws SQLException {

        String sql = "INSERT INTO offer (title, description, pay, owner_id) VALUES (?, ?, ?, ?)";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, title);
            requete.setString(2, description);
            requete.setString(3, pay);
            requete.setInt(4, ownerId);

            requete.executeUpdate();

            System.out.println("Utilisateur ajouté.");
            
        }
    }
    public void addToTable(int userId, int offerId) throws SQLException {

        String sql = "INSERT INTO interesse (user_id, offer_id) VALUES (?, ?)";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setInt(1, userId);
            requete.setInt(2, offerId);

            requete.executeUpdate();

            System.out.println("Utilisateur ajouté.");
        }
    }
    
// Recuperation depuis la base de donne
    public List<Offer> getOffers() throws SQLException {

        List<Offer> offres = new ArrayList<>();
        
        String sql = "SELECT id, title, description, pay, owner_id FROM offer";

        try (Connection connexion = getConnection();
            PreparedStatement requete = connexion.prepareStatement(sql);
            ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String title = resultat.getString("title");
                String description = resultat.getString("description");
                double pay = resultat.getDouble("pay");
                int ownerId = resultat.getInt("owner_id");

                Offer offre = new Offer(id, pay, title, description, ownerId );

                offres.add(offre);
                
            }
        } catch(Exception e){
            System.out.println("Erreur lors de la récupération des offres: "+ e);
        }
        return offres;
    }
    public String getPassword(String userName) throws SQLException {

        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, userName);

            try (ResultSet resultat = requete.executeQuery()) {

                if (resultat.next()) {
                    return resultat.getString("password");
                }

                return null; // Utilisateur introuvable
            }
        }
    }
    public List<Offer> searchOfferTitle(String titleContains) throws SQLException {

        List<Offer> offres = new ArrayList<>();

        String sql = "SELECT id, title, description, pay, owner_id FROM offer WHERE title LIKE ? ";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, "%" + titleContains + "%");

            try (ResultSet resultat = requete.executeQuery()) {
                while (resultat.next()) {
                    Offer offre = new Offer(
                        resultat.getInt("id"),
                        resultat.getDouble("pay"),
                        resultat.getString("title"),
                        resultat.getString("description"),
                        resultat.getInt("owner_id")
                    );

                    offres.add(offre);
                }
            }
        }

        return offres;
    }
    
    public boolean userExists(String username) throws SQLException {

        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, username);

            try (ResultSet resultat = requete.executeQuery()) {
                return resultat.next();
            }
        }
    }

// Met a jour une offre de la base de donne
    public void updateOffer( int id, String title, String description, BigDecimal pay) throws SQLException {

        String sql = "UPDATE offer SET title = ?, description = ?, pay = ? WHERE id = ? ";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, title);
            requete.setString(2, description);
            requete.setBigDecimal(3, pay);
            requete.setInt(4, id);

            int lignesModifiees = requete.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("Offre modifiée avec succès.");
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }
        }
    }

// Supprime un element de la base de donne
    public void delete(String table, int id) throws SQLException {

        // Vérifier les tables autorisées
        if (!table.equals("users") && !table.equals("offer")) {
            throw new IllegalArgumentException("Table non autorisée");
        }

        String sql = "DELETE FROM " + table + " WHERE id = ?";

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setInt(1, id);

            int lignesSupprimees = requete.executeUpdate();

            if (lignesSupprimees > 0) {
                System.out.println("Élément supprimé avec succès.");
            } else {
                System.out.println("Aucun élément trouvé avec cet ID.");
            }
        }
    }
}
