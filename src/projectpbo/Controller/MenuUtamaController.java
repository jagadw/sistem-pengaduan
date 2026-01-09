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
import projectpbo.MenuUtama;
import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;
import projectpbo.DaftarPengaduan;
import projectpbo.FormPengaduan;
import projectpbo.Kategori;
import projectpbo.Login;
import projectpbo.Model.SessionUser;

public class MenuUtamaController {

    private MenuUtama menu;
    private User user;

    public MenuUtamaController(MenuUtama menu) {
        this.menu = menu;
        this.user = SessionUser.get();

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
        SessionUser.clear();
        Login login = new Login();
        new LoginController(login);
        login.setVisible(true);
        menu.dispose();
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
        FormPengaduan pengaduan = new FormPengaduan();
        new FormPengaduanController(pengaduan);
        pengaduan.setVisible(true);
        menu.dispose();
    }

    private void daftarPengaduan() {
        DaftarPengaduan daftar = new DaftarPengaduan();
        new DaftarPengaduanController(daftar);
        daftar.setVisible(true);
        menu.dispose();
    }

    private void kategori() {
        Kategori kategori = new Kategori();
        new KategoriController(kategori);
        kategori.setVisible(true);
        menu.dispose();
    }
}
