/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import projectpbo.Connection.Database;
import projectpbo.Login;
import projectpbo.Model.User;
import projectpbo.Register;
/**
 *
 * @author JAGAD
 */
public class RegisterController {

    private Register view;

    public RegisterController(Register view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getButtonRegister().addActionListener(e -> register());
        view.getButtonLogin().addActionListener(e -> backToLogin());
    }

    private void register() {
        if (view.getNama().isEmpty()
                || view.getEmail().isEmpty()
                || view.getPassword().isEmpty()) {

            JOptionPane.showMessageDialog(view, "Semua field wajib diisi");
            return;
        }

        User user = new User();
        user.setNama(view.getNama());
        user.setRole(view.getRole());
        user.setEmail(view.getEmail());
        user.setPassword(view.getPassword());

        simpanKeDatabase(user);

        JOptionPane.showMessageDialog(view, "Registrasi berhasil");

        Login loginView = new Login();
        new LoginController(loginView);
        loginView.setVisible(true);
        view.dispose();
    }

    private void backToLogin() {
        Login loginView = new Login();
        new LoginController(loginView);
        loginView.setVisible(true);
        view.dispose();
    }

    private void simpanKeDatabase(User user) {
        try {
            Connection conn = Database.getConnection();
            String sql = "INSERT INTO Users (nama, role, email, password) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getNama());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

}
