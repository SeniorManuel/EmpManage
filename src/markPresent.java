import javax.swing.*;
import java.awt.*;

public class markPresent extends JFrame {
    JLabel nameLabel, workLabel, absentLabel, totalLabel;
    JTextField workField, absentField;
    JButton submit, calculate;
    GridBagLayout layout;
    Container container;

    public int totalWorked = -1;

    public markPresent(String fullName) {
        setTitle("Mark Attendance");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new GridBagLayout();
        container = getContentPane();
        container.setLayout(layout);

        nameLabel = new JLabel("Employee: " + fullName);
        workLabel = new JLabel("Work Days:");
        absentLabel = new JLabel("Absent Days:");
        totalLabel = new JLabel("Total Worked Days: ");

        workField = new JTextField(10);
        absentField = new JTextField(10);

        submit = new JButton("Submit Attendance");
        calculate = new JButton("Calculate");

        add(nameLabel, 0, 0, 3, 1);
        add(workLabel, 0, 1, 1, 1);
        add(workField, 1, 1, 2, 1);
        add(absentLabel, 0, 2, 1, 1);
        add(absentField, 1, 2, 2, 1);
        add(totalLabel, 0, 3, 3, 1);
        add(calculate, 0, 4, 1, 1);
        add(submit, 1, 4, 1, 1);


        setVisible(true);
    }

    public void add(Component component, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridw;
        gbc.gridheight = gridh;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        container.add(component, gbc);
    }
}
