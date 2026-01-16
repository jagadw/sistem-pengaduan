/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */
import java.sql.*;
import projectpbo.Connection.Database;
import projectpbo.Model.SessionUser;
import projectpbo.Tanggapan;
import projectpbo.DaftarPengaduan;

public class TanggapanController {

private Tanggapan tanggapan;
private int idPengaduan;

public TanggapanController(Tanggapan tanggapan, int idPengaduan) {
    this.tanggapan = tanggapan;
    this.idPengaduan = idPengaduan;

    loadPengaduan();
    initAction();
}

private void loadPengaduan() {
    try {
        Connection c = Database.getConnection();
        String sql =
            "SELECT u.nama, k.nama_kategori " +
            "FROM Pengaduan p " +
            "JOIN Users u ON p.id_user = u.id " +
            "JOIN Kategori k ON p.id_kategori = k.id_kategori " +
            "WHERE p.id_pengaduan = ?";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, idPengaduan);
        ResultSet r = ps.executeQuery();

        if (r.next()) {
            tanggapan.setNamaPengirim(r.getString("nama"));
            tanggapan.setJudul(r.getString("nama_kategori"));
        }

    } catch (Exception e) {
        tanggapan.showMessage("Gagal memuat data");
    }
}

private void initAction() {

    tanggapan.getButtonKirim().addActionListener(e -> simpanTanggapan());

    tanggapan.getButtonKembali().addActionListener(e -> {
        new DaftarPengaduan().setVisible(true);
        tanggapan.dispose();
    });
}

private void simpanTanggapan() {

    String isi = tanggapan.getIsiTanggapan();
    if (isi.isEmpty()) {
        tanggapan.showMessage("Tanggapan tidak boleh kosong");
        return;
    }

    try {
        Connection c = Database.getConnection();

        String sqlInsert =
            "INSERT INTO Tanggapan (id_user, tanggapan) VALUES (?, ?)";

        PreparedStatement psInsert =
            c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

        psInsert.setString(1, SessionUser.get().getIdUser());
        psInsert.setString(2, isi);
        psInsert.executeUpdate();

        ResultSet rs = psInsert.getGeneratedKeys();
        int idTanggapan = 0;
        if (rs.next()) {
            idTanggapan = rs.getInt(1);
        }

        String sqlUpdate =
            "UPDATE Pengaduan SET id_tanggapan = ?, status = 'Dibalas' WHERE id_pengaduan = ?";

        PreparedStatement psUpdate = c.prepareStatement(sqlUpdate);
        psUpdate.setInt(1, idTanggapan);
        psUpdate.setInt(2, idPengaduan);
        psUpdate.executeUpdate();

        tanggapan.showMessage("Tanggapan berhasil disimpan");
        DaftarPengaduan daftar = new DaftarPengaduan();
        new DaftarPengaduanController(daftar);
        daftar.setVisible(true);
        tanggapan.dispose();

    } catch (Exception e) {
        tanggapan.showMessage("Gagal menyimpan tanggapan");
    }
}


}