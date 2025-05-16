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
                    frame.dtable.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Select a row to delete.");
                }
            }
        });

    }
}
