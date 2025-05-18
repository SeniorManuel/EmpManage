import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();
        //tfname, tlname, tpos, tmrate, tdwork

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fget = frame.tfname.getText();
                String lget = frame.tlname.getText();
                String pget = frame.tpos.getText();
                String mget= frame.tmrate.getText();
                String wget = frame.tdwork.getText();
                String tget = frame.ttype.getText();
                if (!fget.isEmpty() && !lget.isEmpty()) {
                    frame.dtable.addRow(new Object[]{fget, lget, pget, tget, mget, wget});
                    frame.tfname.setText("");
                    frame.tlname.setText("");
                    frame.tpos.setText("");
                    frame.ttype.setText("");
                    frame.tmrate.setText("");
                    frame.tdwork.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Kupal ka!");
                }
            }
        });
        frame.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frame.table.getSelectedRow();
                if (selectedRow != -1) {
                    String id = (String) frame.dtable.getValueAt(selectedRow, 0);

                    int confirm = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete employee ID " + id + "?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            Connection conn = dbConnection.getConnection();
                            String sql = "DELETE FROM employees WHERE id = ?";
                            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setString(1, id);
                            int rowsDeleted = stmt.executeUpdate();
                            conn.close();

                            if (rowsDeleted > 0) {
                                frame.dtable.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(frame, "Employee deleted successfully.");
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

    }
}
