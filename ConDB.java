package com.mycompany.windowmanager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConDB {
    
    // Informations de connexion
    private final String adresse;
    private final String nomBDD;
    private final String utilisateur;
    private final String motDePasse;
    
    // Constructeur
    public ConDB(String adresse, String nomBDD, String utilisateur, String motDePasse) {

        this.adresse = adresse;
        this.nomBDD = nomBDD;
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
    }
    
    
    public Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://" + adresse + "/" + nomBDD;

        return DriverManager.getConnection(url, utilisateur, motDePasse);
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
                BigDecimal pay = resultat.getBigDecimal("pay");
                int ownerId = resultat.getInt("owner_id");

                Offer offre = new Offer(id, pay, title, description, ownerId );

                offres.add(offre);
                
            }
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

        String sql = """
            SELECT id, title, description, pay, owner_id
            FROM offer
            WHERE title LIKE ?
            """;

        try (Connection connexion = getConnection();
             PreparedStatement requete = connexion.prepareStatement(sql)) {

            requete.setString(1, "%" + titleContains + "%");

            try (ResultSet resultat = requete.executeQuery()) {
                while (resultat.next()) {
                    Offer offre = new Offer(
                        resultat.getInt("id"),
                        resultat.getBigDecimal("pay"),
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
