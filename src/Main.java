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
                try {
                    String fget = frame.tfname.getText();
                    String lget = frame.tlname.getText();
                    String pget = frame.tpos.getText();
                    String mget= frame.tmrate.getText();
                    String wget = frame.tdwork.getText();
                    String tget = frame.ttype.getText();
                    Connection conn = dbConnection.getConnection();
                    String sql = "INSERT INTO employees (firstname, lastname, position, type, rate, days_worked) VALUES (?, ?, ?, ?, ?, ?)";
                    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, fget);
                    stmt.setString(2, lget);
                    stmt.setString(3, pget);
                    stmt.setString(4, tget);
                    stmt.setDouble(5, Double.parseDouble(mget));
                    stmt.setInt(6, Integer.parseInt(wget));
                    stmt.executeUpdate();
                    conn.close();
                    System.out.println("Inserted successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to insert into database: " + ex.getMessage());
                }
            }
        });
    }
}
