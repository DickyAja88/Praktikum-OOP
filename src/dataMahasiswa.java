import com.mysql.cj.xdevapi.Column;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class dataMahasiswa extends javax.swing.JInternalFrame {
    private JTextField textnpm;
    private JTextField textnama;
    private JComboBox<String> comboBox1;
    private JRadioButton RadioButton1;
    private JRadioButton RadioButton2;
    private JButton tambahButton;
    private JButton simpanButton;
    private JButton hapusButton;
    private JButton keluarButton;
    private JTable table;
    private JTextField textalamat;
    private JTextField SearchField;
    private JButton cariButton;
    private JPanel Main;

    public dataMahasiswa() {
        initComponents();
        GetAllItem();
    }

    private void initComponents() {
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer NPM = Integer.parseInt(textnpm.getText());
                String Nama = textnama.getText();
                String Jurusan = (String) comboBox1.getSelectedItem();
                String Alamat = textalamat.getText();
                String Jenis_kelamin = null;
                String sql = "INSERT INTO Data_mahasiswa (NPM, Nama, Jurusan, Jenis_kelamin, Alamat) VALUES(?,?,?,?,?)";

                if (RadioButton1.isSelected()) {
                    Jenis_kelamin = "Laki-laki";
                } else if (RadioButton2.isSelected()) {
                    Jenis_kelamin = "Perempuan";
                }

                try (Connection conn = Connect.getConnection();
                     PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, NPM);
                    statement.setString(2, Nama);
                    statement.setString(3, Jurusan);
                    statement.setString(4, Jenis_kelamin);
                    statement.setString(5, Alamat);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah berhasil disimpan");
                    GetAllItem(); // Refresh tampilan tabel setelah penyimpanan data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + ex.getMessage());
                }
            }
        });

        cariButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = SearchField.getText();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                String sql = "SELECT * FROM Data_mahasiswa WHERE NPM LIKE ? OR Nama LIKE ?";

                try (Connection conn = Connect.getConnection();
                     PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, "%" + keyword + "%");
                    statement.setString(2, "%" + keyword + "%");
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        Object[] row = new Object[5];
                        row[0] = resultSet.getInt("NPM");
                        row[1] = resultSet.getString("Nama");
                        row[2] = resultSet.getString("Jurusan");
                        row[3] = resultSet.getString("Jenis_kelamin");
                        row[4] = resultSet.getString("Alamat");
                        model.addRow(row);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
                }
            }
        });

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textnpm.setText("");
                textnama.setText("");
                comboBox1.setSelectedItem("");
                textalamat.setText("");
                RadioButton1.setSelected(false);
                RadioButton2.setSelected(false);
            }
        });

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int npm = (int) table.getValueAt(selectedRow, 0);
                String sql = "DELETE FROM Data_mahasiswa WHERE NPM = ?";

                try (Connection conn = Connect.getConnection();
                     PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, npm);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah berhasil dihapus");
                    GetAllItem(); // Refresh tampilan tabel setelah penghapusan data
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + ex.getMessage());
                }
            }
        });

        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(keluarButton).dispose();
            }
        });
    }


    private void GetAllItem() {

        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("NPM");
        model.addColumn("Nama");
        model.addColumn("Jurusan");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Alamat");
        table.setModel(model);
        String sql = "SELECT * FROM Data_mahasiswa";

        try (Connection conn = Connect.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getInt("NPM");
                row[1] = resultSet.getString("Nama");
                row[2] = resultSet.getString("Jurusan");
                row[3] = resultSet.getString("Jenis_kelamin");
                row[4] = resultSet.getString("Alamat");
                model.addRow(row);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Data Mahasiswa");
                frame.setContentPane(new dataMahasiswa().Main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setMinimumSize(new Dimension(600, 500));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
