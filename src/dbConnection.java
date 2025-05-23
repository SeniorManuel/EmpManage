import javax.swing.*;
import java.sql.*;

public class dbConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/emp_manage";
        String user = "root";
        String pass = "kyzha123";
        return DriverManager.getConnection(url, user, pass);
    }

    public static void loadEmployees(Gui frame) {
        try {
            frame.dtable.setRowCount(0);

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emp_manage", "root", "kyzha123"
            );

            String query = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String pos = rs.getString("position");
                String rate = rs.getString("rate");
                String daysWorked = rs.getString("days_worked");

                frame.dtable.addRow(new Object[]{id, fname, lname, pos, rate, daysWorked});
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load data.");
        }
    }

    //butngan pgd sng ga check pgd daysWorked
    public static void insertPayroll(String fname, String lname, String position, double gross,
                                             double sss, double philhealth, double pagibig, double incomeTax,
                                             double netPay, double monthlyRate) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_manage", "root", "EF2120762")) {

            String checkSql = "SELECT COUNT(*) FROM payroll WHERE fname = ? AND lname = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, fname);
            checkStmt.setString(2, lname);

            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                String updateSql = "UPDATE payroll SET position = ?, gross = ?, sss = ?, philhealth = ?, pagibig = ?, incomeTax = ?, netPay = ?, monthRate = ? WHERE fname = ? AND lname = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, position);
                updateStmt.setDouble(2, gross);
                updateStmt.setDouble(3, sss);
                updateStmt.setDouble(4, philhealth);
                updateStmt.setDouble(5, pagibig);
                updateStmt.setDouble(6, incomeTax);
                updateStmt.setDouble(7, netPay);
                updateStmt.setDouble(8, monthlyRate);
                updateStmt.setString(9, fname);
                updateStmt.setString(10, lname);

                updateStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Payroll updated successfully.");
            } else {
                String insertSql = "INSERT INTO payroll (fname, lname, position, gross, sss, philhealth, pagibig, incomeTax, netPay, monthRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, fname);
                insertStmt.setString(2, lname);
                insertStmt.setString(3, position);
                insertStmt.setDouble(4, gross);
                insertStmt.setDouble(5, sss);
                insertStmt.setDouble(6, philhealth);
                insertStmt.setDouble(7, pagibig);
                insertStmt.setDouble(8, incomeTax);
                insertStmt.setDouble(9, netPay);
                insertStmt.setDouble(10, monthlyRate);

                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Payroll saved successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }
}