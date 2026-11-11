/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author katuro
 */

package com.mycompany.windowmanager;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;


public class CreateOfferWindow extends WindowManager {

    private final int userId;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField payField;

    public CreateOfferWindow(int userId) {
        super("JobBoard - Créer une offre", 500, 450);

        this.userId = userId;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Titre de la fenêtre
        JLabel title = new JLabel("Publier une offre d'emploi");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Champ titre
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Titre de l'offre :"), gbc);

        titleField = new JTextField(20);

        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Champ description
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description :"), gbc);

        descriptionArea = new JTextArea(6, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollDescription =
            new JScrollPane(descriptionArea);

        gbc.gridx = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollDescription, gbc);

        // Champ rémunération
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Rémunération annuelle (€) :"), gbc);

        payField = new JTextField(20);

        gbc.gridx = 1;
        panel.add(payField, gbc);

        // Boutons
        JPanel buttons = new JPanel(new FlowLayout());

        JButton publishButton = new JButton("Publier l'offre");
        JButton cancelButton = new JButton("Annuler");

        buttons.add(publishButton);
        buttons.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        setMainPanel(panel);

        // Annuler
        cancelButton.addActionListener(e -> closeWindow());
   

        publishButton.addActionListener(e -> {

            // Récupération des informations
            String offerTitle = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String payText = payField.getText().trim().replace(',', '.');

            // Vérification des champs
            if (offerTitle.isEmpty() || description.isEmpty() || payText.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Veuillez remplir tous les champs.",
                    "Champs manquants",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                BigDecimal pay = new BigDecimal(payText);

                if (pay.compareTo(BigDecimal.ZERO) < 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "La rémunération ne peut pas être négative.",
                        "Valeur incorrecte",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Enregistrement dans PostgreSQL
                ConDB con = ConDB.getCon();

                con.addToTable(offerTitle, description, pay, userId);

                JOptionPane.showMessageDialog(
                    this,
                    "Votre offre a été publiée avec succès !",
                    "Publication réussie",
                    JOptionPane.INFORMATION_MESSAGE
                );

                closeWindow();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir une rémunération valide (ex : 35000 ou 35000,50).",
                    "Rémunération incorrecte",
                    JOptionPane.ERROR_MESSAGE
                );

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la publication : " + ex.getMessage(),
                    "Erreur SQL",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

    }
}

