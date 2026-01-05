/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projectpbo;

import projectpbo.Controller.LoginController;

/**
 *
 * @author JAGAD
 */
public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Login view = new Login();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}
