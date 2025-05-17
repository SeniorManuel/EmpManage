import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();

        // Add button action listener
        frame.add.addActionListener(e -> {
            String id = String.valueOf(frame.empId++);
            String fname = frame.tfname.getText();
            String lname = frame.tlname.getText();
            String position = frame.tpos.getText();
            String type = frame.ttype.getText();
            String rate = frame.tmrate.getText();
            String daysWorked = frame.tdwork.getText();
            String present = frame.tpresent.getText();
            String absent = frame.tabsent.getText();

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
                    stmt.setString(7, daysWorked);
                    stmt.setString(8, present);
                    stmt.setString(9, absent);
                    stmt.executeUpdate();
                    conn.close();

                    frame.dtable.addRow(new Object[]{id, fname, lname, position, type, rate, daysWorked, present, absent});
                    frame.tfname.setText("");
                    frame.tlname.setText("");
                    frame.tpos.setText("");
                    frame.ttype.setText("");
                    frame.tmrate.setText("");
                    frame.tdwork.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database insert failed.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields.");
            }
        });

        frame.markAttendance.addActionListener(e -> {
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
        });
        
        frame.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbConnection.loadEmployees(frame);
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
