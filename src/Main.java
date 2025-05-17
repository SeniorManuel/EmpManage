import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = String.valueOf(frame.empId++);
                String fname = frame.tfname.getText();
                String lname = frame.tlname.getText();
                String position = frame.tpos.getText();
                String type = frame.ttype.getText();
                String rate = frame.tmrate.getText();

                if (!fname.isEmpty() && !lname.isEmpty()) {
                    try {
                        Connection conn = dbConnection.getConnection();
                        String query = "INSERT INTO employees (id, firstname, lastname, position, type, rate, days_worked, present, absent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        java.sql.PreparedStatement stmt = conn.prepareStatement(query);

                        stmt.setString(1, id);
                        stmt.setString(2, fname);
                        stmt.setString(3, lname);
                        stmt.setString(4, position);
                        stmt.setString(5, type);
                        stmt.setString(6, rate);
                        stmt.executeUpdate();
                        conn.close();

                        frame.dtable.addRow(new Object[]{id, fname, lname, position, type, rate});
                        frame.tfname.setText("");
                        frame.tlname.setText("");
                        frame.tpos.setText("");
                        frame.ttype.setText("");
                        frame.tmrate.setText("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Database insert failed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all required fields.");
                }
            }
        });

        frame.markAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int present = Integer.parseInt(JOptionPane.showInputDialog("Enter Present Days:"));
                        int absent = Integer.parseInt(JOptionPane.showInputDialog("Enter Absent Days:"));
                        frame.dtable.setValueAt(String.valueOf(present), selectedRow, 7);
                        frame.dtable.setValueAt(String.valueOf(absent), selectedRow, 8);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an employee from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        frame.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbConnection.loadEmployees(frame);
                    JOptionPane.showMessageDialog(frame, "All employees are loaded.");
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
                    String tget = frame.ttype.getText();

                    if (!fget.isEmpty() && !lget.isEmpty()) {
                        try {
                            Connection conn = dbConnection.getConnection();
                            String sql = "UPDATE employees SET position = ?, type = ?, rate = ?, days_worked = ? WHERE firstname = ? AND lastname = ?";
                            java.sql.PreparedStatement  stmt = conn.prepareStatement(sql);
                            stmt.setString(1, pget);
                            stmt.setString(2, tget);
                            stmt.setDouble(3, Double.parseDouble(mget));
                            stmt.setString(4, fget);
                            stmt.setString(5, lget);
                            stmt.executeUpdate();
                            conn.close();

                            frame.dtable.setValueAt(fget, selectedRow, 0);
                            frame.dtable.setValueAt(lget, selectedRow, 1);
                            frame.dtable.setValueAt(pget, selectedRow, 2);
                            frame.dtable.setValueAt(tget, selectedRow, 3);
                            frame.dtable.setValueAt(mget, selectedRow, 4);
                            frame.tfname.setText("");
                            frame.tlname.setText("");
                            frame.tpos.setText("");
                            frame.ttype.setText("");
                            frame.tmrate.setText("");

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

        frame.payResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame payResults = new JFrame("Pay Results");
                payResults.setSize(300, 200);
                payResults.setLocationRelativeTo(null);
                payResults.setVisible(true);
                payResults.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
}

