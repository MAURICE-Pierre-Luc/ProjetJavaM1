/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.windowmanager;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author katuro
 */

public class HomeWindow extends WindowManager {

    public HomeWindow() {
        super("JobBoard - Accueil", 450, 300);

        // Organisation verticale
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(
            new BoxLayout(content, BoxLayout.Y_AXIS)
        );

        content.setBorder(
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        JLabel title = new JLabel("Bienvenue sur JobBoard");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
            "Trouvez votre prochaine opportunité."
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("Créer un compte");

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setMaximumSize(new Dimension(250, 40));
        registerButton.setMaximumSize(new Dimension(250, 40));

        content.add(title);
        content.add(Box.createVerticalStrut(10));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(30));
        content.add(loginButton);
        content.add(Box.createVerticalStrut(15));
        content.add(registerButton);

        setMainPanel(content);
        
        // Navigation
        loginButton.addActionListener(e -> {
            new LoginWindow().showWindow();
            closeWindow();
        });

        registerButton.addActionListener(e -> {
            new RegisterWindow().showWindow();
            closeWindow();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeWindow().showWindow();
        });
    }
}





class LoginWindow extends WindowManager {

    // Identifiants de test
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "1234";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        super("JobBoard - Connexion", 400, 250);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel title = new JLabel("Connexion");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Identifiant
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Identifiant :"), gbc);

        usernameField = new JTextField(15);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Mot de passe
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Mot de passe :"), gbc);

        passwordField = new JPasswordField(15);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Boutons
        JPanel buttons = new JPanel(new FlowLayout());

        JButton loginButton = new JButton("Se connecter");
        JButton backButton = new JButton("Retour");

        buttons.add(loginButton);
        buttons.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        setMainPanel(panel);

        // Vérification des identifiants
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(
                passwordField.getPassword()
            );

            if (username.equals(TEST_USERNAME)
                    && password.equals(TEST_PASSWORD)) {

                new OffersWindow().showWindow();
                closeWindow();

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Identifiant ou mot de passe incorrect.",
                    "Erreur de connexion",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        });

        // Retour à l'accueil
        backButton.addActionListener(e -> {
            new HomeWindow().showWindow();
            closeWindow();
        });

        getRootPane().setDefaultButton(loginButton);
    }
}


class RegisterWindow extends WindowManager {

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterWindow() {
        super("JobBoard - Inscription", 450, 350);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel title = new JLabel("Créer un compte");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Nom
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Nom :"), gbc);

        nameField = new JTextField(15);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Mot de passe
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Mot de passe :"), gbc);

        passwordField = new JPasswordField(15);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Confirmation
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Confirmer :"), gbc);

        confirmPasswordField = new JPasswordField(15);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        // Boutons
        JPanel buttons = new JPanel(new FlowLayout());

        JButton registerButton = new JButton("Créer mon compte");
        JButton backButton = new JButton("Retour");

        buttons.add(registerButton);
        buttons.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        setMainPanel(panel);

        // Le bouton d'inscription ne fait rien pour le moment.

        backButton.addActionListener(e -> {
            new HomeWindow().showWindow();
            closeWindow();
        });
    }
}