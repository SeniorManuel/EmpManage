import javax.swing.*;
import java.sql.*;

public class dbConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/emp_manage";
        String user = "root";
        String pass = "EF2120762";
        return DriverManager.getConnection(url, user, pass);
    }

    public static void loadEmployees(Gui frame) {
        try {
            frame.dtable.setRowCount(0);

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emp_manage", "root", "EF2120762"
            );

            String query = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String pos = rs.getString("position");
                String type = rs.getString("type");
                String rate = rs.getString("rate");
                String daysWorked = rs.getString("days_worked");

                frame.dtable.addRow(new Object[]{id, fname, lname, pos, type, rate, daysWorked});
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load data.");
        }
    }
}

