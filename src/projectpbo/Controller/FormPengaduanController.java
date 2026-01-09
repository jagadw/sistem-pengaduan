/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */
import projectpbo.FormPengaduan;
import projectpbo.MenuUtama;
import projectpbo.Connection.Database;
import projectpbo.Model.SessionUser;
import projectpbo.Model.User;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormPengaduanController {

    private FormPengaduan pengaduan;
    private User user;

    public FormPengaduanController(FormPengaduan pengaduan) {
        this.pengaduan = pengaduan;
        this.user = SessionUser.get();

        initView();
        initController();
    }

    private void initView() {
        pengaduan.setNama(user.getNama());
        loadKategori();
    }

    private void initController() {
        pengaduan.getButtonKirim().addActionListener(e -> kirim());
        pengaduan.getButtonKembali().addActionListener(e -> kembali());
    }

    private void loadKategori() {
        try {
            Connection con = Database.getConnection();
            String sql = "SELECT id_kategori, nama_kategori FROM Kategori";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            pengaduan.getSelectKategori().removeAllItems();

            while (rs.next()) {
                pengaduan.getSelectKategori().addItem(
                    rs.getInt("id_kategori") + " - " + rs.getString("nama_kategori")
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pengaduan, "Gagal load kategori");
        }
    }

    private void kirim() {
        String pesan = pengaduan.getPesan();

        if (pesan.isEmpty()) {
            JOptionPane.showMessageDialog(pengaduan, "Pesan wajib diisi");
            return;
        }

        String kategoriDipilih = pengaduan.getKategoriTerpilih();
        int idKategori = Integer.parseInt(kategoriDipilih.split(" - ")[0]);

        try {
            Connection con = Database.getConnection();
            String sql =
                "INSERT INTO Pengaduan (id_user, id_kategori, pesan, tanggal_kirim, status) " +
                "VALUES (?, ?, ?, NOW(), 'Menunggu')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(user.getIdUser()));
            ps.setInt(2, idKategori);
            ps.setString(3, pesan);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(pengaduan, "Pengaduan berhasil dikirim");
            pengaduan.resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(pengaduan, "Gagal mengirim pengaduan");
        }
    }

    private void kembali() {
        MenuUtama menu = new MenuUtama();
        new MenuUtamaController(menu);
        menu.setVisible(true);
        pengaduan.dispose();
    }
}
