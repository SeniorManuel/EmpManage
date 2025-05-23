import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();
        dbConnection.loadEmployees(frame);

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = frame.tfname.getText();
                String lname = frame.tlname.getText();
                String position = frame.tpos.getText();
                String rate = frame.tmrate.getText();

                if (!fname.isEmpty() && !lname.isEmpty()) {
                    try {
                        Connection conn = dbConnection.getConnection();

                        String query = "INSERT INTO employees (firstname, lastname, position, rate) VALUES (?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                        stmt.setString(1, fname);
                        stmt.setString(2, lname);
                        stmt.setString(3, position);
                        stmt.setString(4, rate);

                        stmt.executeUpdate();
                        ResultSet rs = stmt.getGeneratedKeys();
                        int sId = 0;
                        if (rs.next()) {
                            sId = rs.getInt(1);
                        }
                        conn.close();

                        frame.dtable.addRow(new Object[]{sId, fname, lname, position, rate, "0"});
                        frame.tfname.setText("");
                        frame.tlname.setText("");
                        frame.tpos.setText("");
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

        frame.update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    String fget = frame.tfname.getText();
                    String lget = frame.tlname.getText();
                    String pget = frame.tpos.getText();
                    String mget = frame.tmrate.getText();

                    if (!fget.isEmpty() && !lget.isEmpty()) {
                        try {
                            Connection conn = dbConnection.getConnection();
                            String sql = "UPDATE employees SET firstname = ?, lastname = ?, position = ?, rate = ? WHERE id = ?";
                            PreparedStatement stmt = conn.prepareStatement(sql);

                            stmt.setString(1, fget);
                            stmt.setString(2, lget);
                            stmt.setString(3, pget);
                            stmt.setDouble(4, Double.parseDouble(mget));
                            int id = (int) frame.dtable.getValueAt(selectedRow, 0);
                            stmt.setInt(5, id);
                            stmt.executeUpdate();
                            conn.close();

                            frame.dtable.setValueAt(fget, selectedRow, 1);
                            frame.dtable.setValueAt(lget, selectedRow, 2);
                            frame.dtable.setValueAt(pget, selectedRow, 3);
                            frame.dtable.setValueAt(mget, selectedRow, 4);

                            frame.tfname.setText("");
                            frame.tlname.setText("");
                            frame.tpos.setText("");
                            frame.tmrate.setText("");

                            JOptionPane.showMessageDialog(frame, "Employee Updated Successfully");
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

        frame.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    String id = String.valueOf(frame.dtable.getValueAt(selectedRow, 0));
                    String fname = String.valueOf(frame.dtable.getValueAt(selectedRow, 1));
                    String lname = String.valueOf(frame.dtable.getValueAt(selectedRow, 2));

                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete employee ID " + id + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            Connection conn = dbConnection.getConnection();
                            String sql = "DELETE FROM employees WHERE id = ?";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setString(1, id);
                            int rowsDeleted = stmt.executeUpdate();
                            String payrollSQL = "DELETE FROM payroll WHERE fname = ? AND lname = ?";
                            PreparedStatement payrollStmt = conn.prepareStatement(payrollSQL);
                            payrollStmt.setString(1, fname);
                            payrollStmt.setString(2, lname);
                            payrollStmt.executeUpdate();
                            String attendanceSQL = "DELETE FROM attendance WHERE firstname = ? AND lastname = ?";
                            PreparedStatement attendanceStmt = conn.prepareStatement(attendanceSQL);
                            attendanceStmt.setString(1, fname);
                            attendanceStmt.setString(2, lname);
                            attendanceStmt.executeUpdate();
                            conn.close();

                            if (rowsDeleted > 0) {
                                frame.dtable.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(frame, "Record deleted successfully.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Employee not found or could not be deleted.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error deleting employee: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Select an employee to delete.");
                }
            }
        });

        frame.payResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select an employee from the table.");
                    return;
                }

                String fname = frame.table.getValueAt(selectedRow, 1).toString();
                String lname = frame.table.getValueAt(selectedRow, 2).toString();
                String fullName = fname + " " + lname;

                try {
                    String position = frame.table.getValueAt(selectedRow, 3).toString();
                    String totalPresentDays = frame.table.getValueAt(selectedRow, 5).toString();
                    String monthRate = frame.table.getValueAt(selectedRow, 4).toString();

                    if (totalPresentDays.isEmpty() || monthRate.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Monthly Rate or Total Present Days field is empty.");
                        return;
                    }

                    int workTotal = Integer.parseInt(totalPresentDays);
                    if (workTotal <= 0) {
                        JOptionPane.showMessageDialog(frame, "Total worked days must be greater than zero. Please mark attendance.");
                        return;
                    }

                    double monthlyRate = Double.parseDouble(monthRate);
                    double dailyRate = monthlyRate / 22.0;
                    double gross = dailyRate * workTotal;

                    double sss = Math.min(gross, 30000) * 0.045;
                    double philhealth = Math.min(gross, 100000) * 0.05;
                    double pagibig = Math.min(gross * 0.02, 100);

                    double totalContributions = sss + philhealth + pagibig;
                    double taxableIncome = gross - totalContributions;

                    double incomeTax;
                    if (taxableIncome <= 20833) {
                        incomeTax = 0;
                    } else if (taxableIncome <= 33333) {
                        incomeTax = (taxableIncome - 20833) * 0.15;
                    } else if (taxableIncome <= 66667) {
                        incomeTax = 1875 + (taxableIncome - 33333) * 0.20;
                    } else if (taxableIncome <= 166667) {
                        incomeTax = 8541.80 + (taxableIncome - 66667) * 0.25;
                    } else if (taxableIncome <= 666667) {
                        incomeTax = 33541.80 + (taxableIncome - 166667) * 0.30;
                    } else {
                        incomeTax = 183541.80 + (taxableIncome - 666667) * 0.35;
                    }

                    double netPay = gross - totalContributions - incomeTax;

                    new payResults(fname, lname, position, gross, sss, philhealth, pagibig, incomeTax, netPay);
                    dbConnection.insertPayroll(fname, lname, position, gross, sss, philhealth, pagibig, incomeTax, netPay, monthlyRate);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number format in Monthly Rate or Total Present Days.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
                }
            }
        });

        frame.present.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    String fname = frame.table.getValueAt(selectedRow, 1).toString();
                    String lname = frame.table.getValueAt(selectedRow, 2).toString();
                    String fullName = " " + fname + " " + lname;

                    markPresent mp = new markPresent(fullName);

                    mp.submit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                int worked = Integer.parseInt(mp.workField.getText());
                                int absent = Integer.parseInt(mp.absentField.getText());
                                int total = worked - absent;
                                mp.totalWorked = total;

                                dbConnection.markAttendance(fname, lname, worked, absent, total, frame);

                                try (Connection conn = dbConnection.getConnection()) {
                                    String syncQuery = "UPDATE employees e " +
                                            "JOIN attendance a ON e.firstname = a.firstname AND e.lastname = a.lastname " +
                                            "SET e.days_worked = a.totalWorkedDays";
                                    PreparedStatement syncStmt = conn.prepareStatement(syncQuery);
                                    syncStmt.executeUpdate();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "Error syncing days_worked: " + ex.getMessage());
                                }

                                JOptionPane.showMessageDialog(frame, "Attendance submitted or updated.");
                                mp.dispose();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for worked and absent days.");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(frame, "Error submitting attendance: " + ex.getMessage());
                            }
                        }
                    });

                    mp.calculate.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                int worked = Integer.parseInt(mp.workField.getText());
                                int absent = Integer.parseInt(mp.absentField.getText());
                                int total = worked - absent;
                                mp.totalLabel.setText("Total Worked Days: " + total);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Enter valid numbers for worked and absent days.");
                            }
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(frame, "Select an employee to mark attendance.");
                }
            }
        });

    }
}

//id in the database should be followed by other tables
//theres no error handling in the calculate payroll if the user update the attendance