import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();

        frame.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fget = frame.ftext.getText();
                String lget = frame.ltext.getText();
                if (!fget.isEmpty() && !lget.isEmpty()) {
                    frame.dtable.addRow(new Object[]{fget, lget});
                    frame.ftext.setText("");
                    frame.ltext.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Kupal ka!");
                }
            }
        });
    }
}
