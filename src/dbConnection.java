import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/emp_manage";
        String user = "root";
        String pass = "EF2120762";
        return DriverManager.getConnection(url, user, pass);
    }
}
