import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();
        //tfname, tlname, tpos, tmrate, tdwork

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fget = frame.tfname.getText();
                String lget = frame.tlname.getText();
                String pget = frame.tpos.getText();
                String mget= frame.tmrate.getText();
                String wget = frame.tdwork.getText();
                String tget = frame.ttype.getText();
                if (!fget.isEmpty() && !lget.isEmpty()) {
                    frame.dtable.addRow(new Object[]{fget, lget, pget, tget, mget, wget});
                    frame.tfname.setText("");
                    frame.tlname.setText("");
                    frame.tpos.setText("");
                    frame.ttype.setText("");
                    frame.tmrate.setText("");
                    frame.tdwork.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "very good!");
                }
            }
        });

        frame.update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    String fget = frame.tfname.getText();
                    String lget = frame.tlname.getText();
                    String pget = frame.tpos.getText();
                    String mget = frame.tmrate.getText();
                    String wget = frame.tdwork.getText();
                    String tget = frame.ttype.getText();

                    if (!fget.isEmpty() && !lget.isEmpty()) {
                        try {
                            Connection conn = dbConnection.getConnection();
                            String sql = "UPDATE employees SET position = ?, type = ?, rate = ?, days_worked = ? WHERE firstname = ? AND lastname = ?";
                            java.sql.PreparedStatement  stmt = conn.prepareStatement(sql);
                            stmt.setString(1, pget);
                            stmt.setString(2, tget);
                            stmt.setDouble(3, Double.parseDouble(mget));
                            stmt.setInt(4, Integer.parseInt(wget));
                            stmt.setString(5, fget);
                            stmt.setString(6, lget);
                            stmt.executeUpdate();
                            conn.close();

                            frame.dtable.setValueAt(fget, selectedRow, 0);
                            frame.dtable.setValueAt(lget, selectedRow, 1);
                            frame.dtable.setValueAt(pget, selectedRow, 2);
                            frame.dtable.setValueAt(tget, selectedRow, 3);
                            frame.dtable.setValueAt(mget, selectedRow, 4);
                            frame.dtable.setValueAt(wget, selectedRow, 5);

                            frame.tfname.setText("");
                            frame.tlname.setText("");
                            frame.tpos.setText("");
                            frame.ttype.setText("");
                            frame.tmrate.setText("");
                            frame.tdwork.setText("");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Error updating database: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Fill in Firstname and Lastname.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Select a row to update.");
                }
            }
        });

    }
}

