

package com.mycompany.windowmanager;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;




public class OffersWindow extends WindowManager {

    private final ConDB con = ConDB.getCon();
    private final int userId;
    
    private JPanel offersPanel;
    private JScrollPane scrollPane;

    public OffersWindow(int userId) {
        super("JobBoard - Offres d'emploi", 800, 600);
        
        this.userId = userId;    

        // Fenêtre principale
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
            new EmptyBorder(15, 20, 15, 20)
        );

        // =========================
        // EN-TÊTE
        // =========================

        JPanel header = new JPanel(new BorderLayout());

        JLabel title = new JLabel("JobBoard - Offres d'emploi");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        header.add(title, BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton profileButton = new JButton("Mon profil");
        JButton createButton = new JButton("Créer une offre");
        JButton refreshButton = new JButton("Actualiser");
        JButton quitButton = new JButton("Quitter");

        buttons.add(profileButton);
        buttons.add(createButton);
        buttons.add(refreshButton);
        buttons.add(quitButton);

        header.add(buttons, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // =========================
        // LISTE DES OFFRES
        // =========================


        offersPanel = new JPanel();
        offersPanel.setLayout(
            new BoxLayout(offersPanel, BoxLayout.Y_AXIS)
        );

        offersPanel.setBorder(
            new EmptyBorder(10, 0, 10, 0)
        );

        scrollPane = new JScrollPane(offersPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Chargement initial des offres
        refreshOffers();

        setMainPanel(mainPanel);

        // =========================
        // ACTIONS DES BOUTONS
        // =========================

        // Pour l'instant, ces deux boutons ne font rien.

        profileButton.addActionListener(e -> {
            // À implémenter plus tard
        });

        createButton.addActionListener(e -> {
            new CreateOfferWindow(userId).showWindow();
        });
        
        refreshButton.addActionListener(e -> {
            refreshOffers();
        });

        // Quitter l'application
        quitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void refreshOffers() {

    // On vide la liste actuelle
    offersPanel.removeAll();

    try {
        // On récupère les offres depuis PostgreSQL
        List<Offer> offers = con.getOffers();

        if (offers.isEmpty()) {
            JLabel emptyLabel = new JLabel(
                "Aucune offre disponible pour le moment."
            );

            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            offersPanel.add(emptyLabel);

        } else {

            // On reconstruit les cartes
            for (Offer offer : offers) {

                JPanel offerCard = createOfferCard(offer);

                offersPanel.add(offerCard);
                offersPanel.add(Box.createVerticalStrut(12));
            }
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            this,
            "Impossible de récupérer les offres : " + e.getMessage(),
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // Actualisation graphique
    offersPanel.revalidate();
    offersPanel.repaint();

    // Retour en haut de la liste
    scrollPane.getVerticalScrollBar().setValue(0);
    }
    
    
    // Crée l'affichage d'une offre
    private JPanel createOfferCard(Offer offer) {

        JPanel card = new JPanel(new BorderLayout(10, 8));

        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(12, 15, 12, 15)
        ));

        card.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 150)
        );

        // Titre
        JLabel titleLabel = new JLabel(offer.titre);
        titleLabel.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        // Rémunération
        JLabel salaryLabel = new JLabel(
            String.format("%.2f € / an", offer.remuneration)
                .replace(',', ' ')
        );

        salaryLabel.setForeground(new Color(30, 130, 70));
        salaryLabel.setFont(
            new Font("Arial", Font.BOLD, 14)
        );

        // Description
        JTextArea description = new JTextArea(offer.offre);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setOpaque(false);
        description.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(titleLabel, BorderLayout.WEST);
        top.add(salaryLabel, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(description, BorderLayout.CENTER);

        return card;
    }
}


