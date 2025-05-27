import javax.swing.*;
import java.sql.*;

public class dbConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/emp_manage";
        String user = "root";
        String pass = "usls123";
        return DriverManager.getConnection(url, user, pass);
    }

    public static void loadEmployees(Gui frame) {
        try (Connection conn = getConnection()) {
            frame.dtable.setRowCount(0);
            String query = "SELECT e.id, e.firstname, e.lastname, e.position, e.rate, COALESCE(a.totalWorkedDays, 0) AS total_present_days " +
                    "FROM employees e LEFT JOIN attendance a ON e.id = a.employee_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String pos = rs.getString("position");
                String rate = rs.getString("rate");
                String totalPresentDays = rs.getString("total_present_days");

                frame.dtable.addRow(new Object[]{id, fname, lname, pos, rate, totalPresentDays});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load data: " + e.getMessage());
        }
    }

    public static void insertPayroll(int employeeId, String fname, String lname, String position,
                                     double gross, double sss, double philhealth, double pagibig,
                                     double incomeTax, double netPay, double monthlyRate) {
        try {
            Connection conn = getConnection();
            String check = "SELECT COUNT(*) FROM payroll WHERE employee_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                String update = "UPDATE payroll SET fname = ?, lname = ?, position = ?, gross = ?, sss = ?, philhealth = ?, pagibig = ?, incomeTax = ?, netPay = ?, monthRate = ? WHERE employee_id = ?";
                PreparedStatement pst = conn.prepareStatement(update);
                pst.setString(1, fname);
                pst.setString(2, lname);
                pst.setString(3, position);
                pst.setDouble(4, gross);
                pst.setDouble(5, sss);
                pst.setDouble(6, philhealth);
                pst.setDouble(7, pagibig);
                pst.setDouble(8, incomeTax);
                pst.setDouble(9, netPay);
                pst.setDouble(10, monthlyRate);
                pst.setInt(11, employeeId);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Payroll updated successfully.");
            } else {
                String insert = "INSERT INTO payroll (employee_id, fname, lname, position, gross, sss, philhealth, pagibig, incomeTax, netPay, monthRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(insert);
                pst.setInt(1, employeeId);
                pst.setString(2, fname);
                pst.setString(3, lname);
                pst.setString(4, position);
                pst.setDouble(5, gross);
                pst.setDouble(6, sss);
                pst.setDouble(7, philhealth);
                pst.setDouble(8, pagibig);
                pst.setDouble(9, incomeTax);
                pst.setDouble(10, netPay);
                pst.setDouble(11, monthlyRate);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Payroll saved successfully.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void markAttendance(int employeeId, String firstName, String lastName, int work, int absent, int total, Gui frame) {
        System.out.println("markAttendance called for employee ID: " + employeeId);
        try (Connection conn = getConnection()) {
            String check = "SELECT COUNT(*) FROM attendance WHERE employee_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            System.out.println("Attendance record exists: " + (count > 0));

            if (count > 0) {
                String update = "UPDATE attendance SET firstName = ?, lastName = ?, workDays = ?, daysAbsent = ?, totalWorkedDays = ? WHERE employee_id = ?";
                PreparedStatement pst = conn.prepareStatement(update);
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setInt(3, work);
                pst.setInt(4, absent);
                pst.setInt(5, total);
                pst.setInt(6, employeeId);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Attendance updated.");
            } else {
                String insert = "INSERT INTO attendance (employee_id, firstName, lastName, workDays, daysAbsent, totalWorkedDays) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(insert);
                pst.setInt(1, employeeId);
                pst.setString(2, firstName);
                pst.setString(3, lastName);
                pst.setInt(4, work);
                pst.setInt(5, absent);
                pst.setInt(6, total);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Attendance added.");
            }

            loadEmployees(frame);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to mark attendance: " + e.getMessage());
        }
    }

    public static void insertLeave(int employeeId, String leaveType, String startDate, String endDate, String status) {
        System.out.println("insertLeave called for employee ID: " + employeeId);
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO leaves (employee_id, leave_type, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            stmt.setString(2, leaveType);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setString(5, status);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting leave: " + ex.getMessage());
        }
    }

    public static ResultSet getLeaves(int employeeId) throws SQLException {
        System.out.println("getLeaves called for employee ID: " + employeeId);
        Connection conn = getConnection();
        String sql = "SELECT leave_type, start_date, end_date, status FROM leaves WHERE employee_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, employeeId);
        return stmt.executeQuery();
    }
}