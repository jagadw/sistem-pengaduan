/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import projectpbo.Connection.Database;
import projectpbo.Model.SessionUser;
import projectpbo.DaftarPengaduan;
import projectpbo.MenuUtama;
import projectpbo.Model.User;
import projectpbo.Tanggapan;

public class DaftarPengaduanController {

    private DaftarPengaduan daftarPengaduan;

    public DaftarPengaduanController(DaftarPengaduan daftarPengaduan) {
        this.daftarPengaduan = daftarPengaduan;

        initTable();
        loadData();
        initAction();
        initView();
    }

    private void initView() {
        User user = SessionUser.get();
        if (user == null) {
            return;
        }

        if (!"Admin".equals(user.getRole())) {
            daftarPengaduan.getButtonTanggapi().setVisible(false);
        }


    }
    
    private void initTable() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "ID",
                "Nama",
                "Kategori",
                "Pesan",
                "Tanggal",
                "Status",
                "Tanggapan"
            }
        );
        daftarPengaduan.getTable().setModel(model);
        daftarPengaduan.getTable().setRowHeight(28);
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) daftarPengaduan.getTable().getModel();
        model.setRowCount(0);

        try {
            Connection c = Database.getConnection();
            Statement s = c.createStatement();

            String sql =
                "SELECT p.id_pengaduan, u.nama, k.nama_kategori, p.pesan, " +
                "p.tanggal_kirim, p.status, t.tanggapan " +
                "FROM Pengaduan p " +
                "JOIN Users u ON p.id_user = u.id " +
                "JOIN Kategori k ON p.id_kategori = k.id_kategori " +
                "LEFT JOIN Tanggapan t ON p.id_tanggapan = t.id_tanggapan";

            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                model.addRow(new Object[]{
                    r.getInt("id_pengaduan"),
                    r.getString("nama"),
                    r.getString("nama_kategori"),
                    r.getString("pesan"),
                    r.getString("tanggal_kirim"),
                    r.getString("status"),
                    r.getString("tanggapan")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(daftarPengaduan, "Gagal memuat data");
        }
    }

    private void initAction() {

        daftarPengaduan.getButtonTanggapi().addActionListener(e -> {
            int row = daftarPengaduan.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(daftarPengaduan, "Pilih data terlebih dahulu");
                return;
            }

            int idPengaduan = Integer.parseInt(
                daftarPengaduan.getTable().getValueAt(row, 0).toString()
            );

            new Tanggapan(idPengaduan).setVisible(true);
            daftarPengaduan.dispose();
        });

        daftarPengaduan.getButtonKembali().addActionListener(e -> {
            MenuUtama menu = new MenuUtama();
            new MenuUtamaController(menu);
            menu.setVisible(true);
            daftarPengaduan.dispose();
        });
    }
    
}
