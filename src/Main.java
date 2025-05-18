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
                String id = String.valueOf(frame.empId++);
                String fname = frame.tfname.getText();
                String lname = frame.tlname.getText();
                String position = frame.tpos.getText();
                String type = frame.ttype.getText();
                String rate = frame.tmrate.getText();
                String daysWorked = frame.tdwork.getText();

                if (!fname.isEmpty() && !lname.isEmpty()) {
                    try {
                        Connection conn = dbConnection.getConnection();
                        String query = "INSERT INTO employees (id, firstname, lastname, position, type, rate, days_worked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        java.sql.PreparedStatement stmt = conn.prepareStatement(query);

                        stmt.setString(1, id);
                        stmt.setString(2, fname);
                        stmt.setString(3, lname);
                        stmt.setString(4, position);
                        stmt.setString(5, type);
                        stmt.setString(6, rate);
                        stmt.setString(7, daysWorked);
                        stmt.executeUpdate();
                        conn.close();

                        frame.dtable.addRow(new Object[]{id, fname, lname, position, type, rate, daysWorked});
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
                int selectedRow = frame.table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a row in the table first.");
                    return;
                }

                Object rateObj = frame.table.getValueAt(selectedRow, 5);
                Object daysObj = frame.table.getValueAt(selectedRow, 6);

                if (rateObj == null || daysObj == null) {
                    JOptionPane.showMessageDialog(frame, "Rate or Days Worked is empty in the selected row.");
                    return;
                }

                try {
                    double rate = Double.parseDouble(rateObj.toString());
                    int days = Integer.parseInt(daysObj.toString());

                    double gross = rate * days;
                    double tax = gross * 0.10;
                    double net = gross - tax;

                    JTextArea resultArea = new JTextArea(
                            "Gross Pay: " + gross +
                                    "\nTax (10%): " + tax +
                                    "\nNet Pay: " + net
                    );
                    resultArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(resultArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(250, 100));

                    JOptionPane.showMessageDialog(frame, scrollPane, "Payroll Results", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number format in selected row (Rate or Days Worked).");
                }
            }
        });


    }
}