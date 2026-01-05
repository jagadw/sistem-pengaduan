/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */
import projectpbo.Model.User;
import projectpbo.menuUtama;
import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;
import projectpbo.DaftarPengaduan;
import projectpbo.FormPengaduan;
import projectpbo.Kategori;
import projectpbo.Login;

public class MenuUtamaController {

    private menuUtama menu;
    private User user;

    public MenuUtamaController(menuUtama menu, User user) {
        this.menu = menu;
        this.user = user;

        initView();
        initController();
    }

    private void initView() {
        menu.getUser().setText(user.getNama());
        menu.getRole().setText(user.getRole());

        if (!user.getRole().equals("Admin")) {
            menu.getTambahKategori().setVisible(false);
        }
    }

    private void initController() {
        menu.getLogout().addActionListener(e -> logout());
        menu.getBantuan().addActionListener(e -> bantuan());
        menu.getFormPengaduan().addActionListener(e -> formPengaduan());
        menu.getDaftarPengaduan().addActionListener(e -> daftarPengaduan());
        menu.getTambahKategori().addActionListener(e -> kategori());
    }

    private void logout() {
        menu.dispose();
        new Login().setVisible(true);
    }

    private void bantuan() {
        try {
            String url =
                "https://api.whatsapp.com/send?phone=628873461479&text=Halo";
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(menu, "Gagal membuka WhatsApp");
        }
    }

    private void formPengaduan() {
        new FormPengaduan(
            user.getIdUser(),
            user.getNama(),
            user.getRole()
        ).setVisible(true);
        menu.dispose();
    }

    private void daftarPengaduan() {
        new DaftarPengaduan(
            user.getIdUser(),
            user.getNama(),
            user.getRole()
        ).setVisible(true);
        menu.dispose();
    }

    private void kategori() {
        new Kategori(
            user.getIdUser(),
            user.getNama(),
            user.getRole()
        ).setVisible(true);
        menu.dispose();
    }
}
