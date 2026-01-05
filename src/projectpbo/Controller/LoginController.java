/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */
import projectpbo.Login;
import projectpbo.Model.User;
import projectpbo.Connection.Database;
import projectpbo.menuUtama;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import projectpbo.Register;

public class LoginController {

    private Login view;

    public LoginController(Login view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getButtonLogin().addActionListener(e -> login());
        view.getButtonRegister().addActionListener(e -> register());
    }

    private void login() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Email dan password wajib diisi");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT id, nama, role, email FROM Users WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getString("id"));
                user.setNama(rs.getString("nama"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));

                menuUtama menuView = new menuUtama();
                new MenuUtamaController(menuView, user);
                menuView.setVisible(true);

                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Email atau password salah");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void register() {
        new Register().setVisible(true);
        view.dispose();
    }
}
