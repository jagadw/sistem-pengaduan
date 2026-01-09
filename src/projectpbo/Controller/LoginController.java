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
import projectpbo.MenuUtama;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import projectpbo.Model.SessionUser;
import projectpbo.Register;

public class LoginController {

    private Login login;

    public LoginController(Login login) {
        this.login = login;
        initController();
    }

    private void initController() {
        login.getButtonLogin().addActionListener(e -> login());
        login.getButtonRegister().addActionListener(e -> register());
    }

    private void login() {
        String email = login.getEmail();
        String password = login.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(login, "Email dan password wajib diisi");
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

                SessionUser.set(user);
                
                MenuUtama menu = new MenuUtama();
                new MenuUtamaController(menu);
                menu.setVisible(true);

                login.dispose();
            } else {
                JOptionPane.showMessageDialog(login, "Email atau password salah");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(login, e.getMessage());
        }
    }

    private void register() {
        Register register = new Register();
        new RegisterController(register);
        register.setVisible(true);
        login.dispose();
    }

}
