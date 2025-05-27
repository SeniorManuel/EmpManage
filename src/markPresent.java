import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class markPresent extends JFrame {
    JTextField workField = new JTextField(10);
    JTextField absentField = new JTextField(10);
    JLabel totalLabel = new JLabel("Total Worked Days: 0");
    JButton submit = new JButton("Submit");
    JButton calculate = new JButton("Calculate");
    JButton manageLeaves = new JButton("Manage Leaves");
    JButton submitLeave = new JButton("Submit Leave");
    JTextField leaveTypeField = new JTextField(15);
    JTextField startDateField = new JTextField("YYYY-MM-DD", 10);
    JTextField endDateField = new JTextField("YYYY-MM-DD", 10);
    JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"});
    JTextArea leaveDisplay = new JTextArea(10, 30);
    int totalWorked;
    private int employeeId;

    public markPresent(String fullName, int employeeId) {
        this.employeeId = employeeId;
        System.out.println("Initializing markPresent for " + fullName);
        setTitle("Mark Attendance for " + fullName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        try {
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(new JLabel("Worked Days:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(workField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(new JLabel("Absent Days:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(absentField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(calculate, gbc);
            gbc.gridx = 1;
            inputPanel.add(submit, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            inputPanel.add(totalLabel, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            inputPanel.add(new JLabel("Leave Type:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(leaveTypeField, gbc);

            gbc.gridx = 0; gbc.gridy = 5;
            inputPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(startDateField, gbc);

            gbc.gridx = 0; gbc.gridy = 6;
            inputPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(endDateField, gbc);

            gbc.gridx = 0; gbc.gridy = 7;
            inputPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(statusCombo, gbc);

            gbc.gridx = 0; gbc.gridy = 8;
            inputPanel.add(submitLeave, gbc);
            gbc.gridx = 1;
            inputPanel.add(manageLeaves, gbc);

            JScrollPane scrollPane = new JScrollPane(leaveDisplay);
            leaveDisplay.setEditable(false);

            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            System.out.println("markPresent UI components added");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing markPresent: " + ex.getMessage());
        }
    }

    public void loadExistingLeaves() {
        System.out.println("Loading existing leaves for employee ID: " + employeeId);
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