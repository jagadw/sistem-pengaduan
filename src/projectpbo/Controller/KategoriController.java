/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpbo.Controller;

/**
 *
 * @author JAGAD
 */

import projectpbo.Kategori;
import projectpbo.Connection.Database;
import projectpbo.MenuUtama;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import projectpbo.Model.SessionUser;
import projectpbo.Model.User;

public class KategoriController {

    private Kategori kategori;
    private User user;
    private DefaultTableModel model;

    public KategoriController(Kategori kategori) {
        this.kategori = kategori;
        this.user = SessionUser.get();
        initTable();
        initController();
        loadData();
    }

    private void initTable() {
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Kategori");
        model.addColumn("Keterangan");
        kategori.getTable().setModel(model);
    }

    private void initController() {
        kategori.getButtonSimpan().addActionListener(e -> simpan());
        kategori.getButtonEdit().addActionListener(e -> edit());
        kategori.getButtonHapus().addActionListener(e -> hapus());
        kategori.getButtonKembali().addActionListener(e -> kembali());

        kategori.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = kategori.getTable().getSelectedRow();

                kategori.setSelectedId(
                    Integer.parseInt(
                        kategori.getTable().getValueAt(row, 0).toString()
                    )
                );

                kategori.setNamaKategori(
                    kategori.getTable().getValueAt(row, 1).toString()
                );

                kategori.setPesan(
                    kategori.getTable().getValueAt(row, 2).toString()
                );
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Database.getConnection();
            ResultSet rs = conn.prepareStatement(
                "SELECT * FROM Kategori"
            ).executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori"),
                    rs.getString("keterangan")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kategori, e.getMessage());
        }
    }

    private void simpan() {
        if (kategori.getNamaKategori().isEmpty()) {
            JOptionPane.showMessageDialog(kategori, "Nama kategori wajib diisi");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Kategori (nama_kategori, keterangan) VALUES (?,?)"
            );
            ps.setString(1, kategori.getNamaKategori());
            ps.setString(2, kategori.getPesan());
            ps.executeUpdate();

            loadData();
            kategori.resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kategori, e.getMessage());
        }
    }

    private void edit() {
        if (kategori.getSelectedId() == -1) {
            JOptionPane.showMessageDialog(kategori, "Pilih data dulu");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE Kategori SET nama_kategori=?, keterangan=? WHERE id_kategori=?"
            );
            ps.setString(1, kategori.getNamaKategori());
            ps.setString(2, kategori.getPesan());
            ps.setInt(3, kategori.getSelectedId());
            ps.executeUpdate();

            loadData();
            kategori.resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kategori, e.getMessage());
        }
    }

    private void hapus() {
        if (kategori.getSelectedId() == -1) {
            JOptionPane.showMessageDialog(kategori, "Pilih data dulu");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM Kategori WHERE id_kategori=?"
            );
            ps.setInt(1, kategori.getSelectedId());
            ps.executeUpdate();

            loadData();
            kategori.resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kategori, e.getMessage());
        }
    }

    private void kembali() {
        MenuUtama menu = new MenuUtama();
        new MenuUtamaController(menu);
        menu.setVisible(true);
        kategori.dispose();
    }
}
