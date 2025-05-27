import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class markPresent extends JFrame {
    JTextField workField, absentField, leaveTypeField, startDateField, endDateField;
    JLabel totalLabel;
    JButton calculate, submit, submitLeave;
    JComboBox<String> statusCombo;
    JTextArea leaveDisplay;
    int totalWorked;
    private int employeeId;

    public markPresent(String fullName, int employeeId) {
        this.employeeId = employeeId;
        setTitle("Mark Attendance for " + fullName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        JPanel panAttendance = new JPanel(new GridBagLayout());
        panAttendance.setBorder(BorderFactory.createTitledBorder("Attendance"));

        workField = new JTextField(10);
        absentField = new JTextField(10);
        totalLabel = new JLabel("Total Worked Days: 0");
        calculate = new JButton("Calculate");
        submit = new JButton("Submit");

        addP(panAttendance, new JLabel("Worked Days:"), 0, 0, 1, 1);
        addP(panAttendance, workField, 1, 0, 1, 1);
        addP(panAttendance, new JLabel("Absent Days:"), 0, 1, 1, 1);
        addP(panAttendance, absentField, 1, 1, 1, 1);
        addP(panAttendance, totalLabel, 0, 2, 2, 1);
        addP(panAttendance, calculate, 0, 3, 1, 1);
        addP(panAttendance, submit, 1, 3, 1, 1);

        JPanel panLeave = new JPanel(new GridBagLayout());
        panLeave.setBorder(BorderFactory.createTitledBorder("Leave Request"));

        leaveTypeField = new JTextField(15);
        startDateField = new JTextField("YYYY-MM-DD", 10);
        endDateField = new JTextField("YYYY-MM-DD", 10);
        statusCombo = new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"});
        submitLeave = new JButton("Submit Leave");
        leaveDisplay = new JTextArea(10, 30);
        leaveDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaveDisplay);

        addP(panLeave, new JLabel("Leave Type:"), 0, 0, 1, 1);
        addP(panLeave, leaveTypeField, 1, 0, 1, 1);
        addP(panLeave, new JLabel("Start Date:"), 0, 1, 1, 1);
        addP(panLeave, startDateField, 1, 1, 1, 1);
        addP(panLeave, new JLabel("End Date:"), 0, 2, 1, 1);
        addP(panLeave, endDateField, 1, 2, 1, 1);
        addP(panLeave, new JLabel("Status:"), 0, 3, 1, 1);
        addP(panLeave, statusCombo, 1, 3, 1, 1);
        addP(panLeave, submitLeave, 0, 4, 2, 1);
        addP(panLeave, scrollPane, 0, 5, 2, 1);

        addP(mainPanel, panAttendance, 0, 0, 2, 1);
        addP(mainPanel, panLeave, 0, 1, 2, 1);

        setContentPane(new JScrollPane(mainPanel));
        setVisible(true);
    }

    public void addP(JPanel panel, Component comp, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridw;
        gbc.gridheight = gridh;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comp, gbc);
    }

    public void loadExistingLeaves() {
        ResultSet rs = null;
        try {
            rs = dbConnection.getLeaves(employeeId);
            leaveDisplay.setText("Existing Leaves for Employee ID " + employeeId + ":\n\n");
            while (rs.next()) {
                leaveDisplay.append("Type: " + rs.getString(1) + "\n");
                leaveDisplay.append("Start Date: " + rs.getDate(2) + "\n");
                leaveDisplay.append("End Date: " + rs.getDate(3) + "\n");
                leaveDisplay.append("Status: " + rs.getString(4) + "\n\n");
            }
        } catch (SQLException ex) {
            leaveDisplay.setText("Error loading leaves: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
