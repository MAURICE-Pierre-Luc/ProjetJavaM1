/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.windowmanager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

    public LoginWindow() {
        super("JobBoard - Connexion", 400, 200);

        setMainPanel(new JPanel(new FlowLayout()));

        addLabel("Fenêtre de connexion");

        addButton("Retour à l'accueil", e -> {
            new HomeWindow().showWindow();
            closeWindow();
        });
    }
}


class RegisterWindow extends WindowManager {

    public RegisterWindow() {
        super("JobBoard - Inscription", 400, 200);

        setMainPanel(new JPanel(new FlowLayout()));

        addLabel("Fenêtre de création de compte");

        addButton("Retour à l'accueil", e -> {
            new HomeWindow().showWindow();
            closeWindow();
        });
    }
}