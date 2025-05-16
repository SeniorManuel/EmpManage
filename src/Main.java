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
    }
}
