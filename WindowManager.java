/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.windowmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WindowManager extends JFrame {

    private JPanel mainPanel;

    public WindowManager(String title, int width, int height) {
        super(title);

        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setContentPane(mainPanel);
    }


    public static void main(String[] args){
        HomeWindow hw = new HomeWindow();
        hw.main(null);
    }
    
    
    // Remplacer le panneau principal
    public void setMainPanel(JPanel panel) {
        mainPanel = panel;
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // Ajouter un bouton
    public JButton addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);

        mainPanel.add(button);
        mainPanel.revalidate();
        mainPanel.repaint();

        return button;
    }

    // Ajouter un texte
    public JLabel addLabel(String text) {
        JLabel label = new JLabel(text);

        mainPanel.add(label);
        mainPanel.revalidate();
        mainPanel.repaint();

        return label;
    }

    // Ajouter un champ de texte
    public JTextField addTextField(int columns) {
        JTextField textField = new JTextField(columns);

        mainPanel.add(textField);
        mainPanel.revalidate();
        mainPanel.repaint();

        return textField;
    }

    // Afficher la fenêtre
    public void showWindow() {
        setVisible(true);
    }

    // Fermer la fenêtre
    public void closeWindow() {
        dispose();
    }
}






