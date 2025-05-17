import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fget = frame.tfname.getText();
                String lget = frame.tlname.getText();
                String pget = frame.tpos.getText();
                String mget = frame.tmrate.getText();
                String wget = frame.tdwork.getText();
                String tget = frame.ttype.getText();

                if (!fget.isEmpty() && !lget.isEmpty()) {
                    try {
                        Connection conn = dbConnection.getConnection();
                        String query = "INSERT INTO employees (firstname, lastname, position, type, rate, days_worked) VALUES (?, ?, ?, ?, ?, ?)";
                        java.sql.PreparedStatement stmt = conn.prepareStatement(query);

                        stmt.setString(1, fget);
                        stmt.setString(2, lget);
                        stmt.setString(3, pget);
                        stmt.setString(4, tget);
                        stmt.setString(5, mget);
                        stmt.setString(6, wget);
                        stmt.executeUpdate();
                        conn.close();

                        frame.dtable.addRow(new Object[]{fget, lget, pget, tget, mget, wget});
                        frame.tfname.setText("");
                        frame.tlname.setText("");
                        frame.tpos.setText("");
                        frame.ttype.setText("");
                        frame.tmrate.setText("");
                        frame.tdwork.setText("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Database insert failed."); }

                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all required fields."); }
            }
        });

        frame.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbConnection.loadEmployees(frame);
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

                    if (fget.isEmpty() || lget.isEmpty() || pget.isEmpty() || mget.isEmpty() || wget.isEmpty() || tget.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please retype your changes.");
                        return;
                    }
                    try {
                        Double.parseDouble(mget);
                        Integer.parseInt(wget);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Monthly Rate and Days Worked must be numbers.");
                        return;
                    }
                    try {
                        String originalFirst = frame.dtable.getValueAt(selectedRow, 0).toString();
                        String originalLast = frame.dtable.getValueAt(selectedRow, 1).toString();

                        Connection conn = dbConnection.getConnection();
                        String query = "UPDATE employees SET firstname=?, lastname=?, position=?, type=?, rate=?, days_worked=? WHERE firstname=? AND lastname=?";
                        java.sql.PreparedStatement stmt = conn.prepareStatement(query);

                        stmt.setString(1, fget);
                        stmt.setString(2, lget);
                        stmt.setString(3, pget);
                        stmt.setString(4, tget);
                        stmt.setString(5, mget);
                        stmt.setString(6, wget);
                        stmt.setString(7, originalFirst);
                        stmt.setString(8, originalLast);

                        int affectedRows = stmt.executeUpdate();
                        conn.close();

                        if (affectedRows > 0) {
                            frame.dtable.setValueAt(fget, selectedRow, 0);
                            frame.dtable.setValueAt(lget, selectedRow, 1);
                            frame.dtable.setValueAt(pget, selectedRow, 2);
                            frame.dtable.setValueAt(tget, selectedRow, 3);
                            frame.dtable.setValueAt(mget, selectedRow, 4);
                            frame.dtable.setValueAt(wget, selectedRow, 5);

                            JOptionPane.showMessageDialog(frame, "Employee updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Update failed. Employee not found.");
                        }

                        frame.tfname.setText("");
                        frame.tlname.setText("");
                        frame.tpos.setText("");
                        frame.ttype.setText("");
                        frame.tmrate.setText("");
                        frame.tdwork.setText("");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Database update failed.");
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an employee row to update.");
                }
            }
        });
    }
}