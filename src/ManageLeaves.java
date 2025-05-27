import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageLeaves extends JFrame {
    private JTextField leaveTypeField = new JTextField(15);
    private JTextField startDateField = new JTextField("YYYY-MM-DD", 10);
    private JTextField endDateField = new JTextField("YYYY-MM-DD", 10);
    private JButton submit = new JButton("Submit Leave");
    private JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"});
    private JTextArea leaveDisplay = new JTextArea(10, 30);
    private int employeeId;

    public ManageLeaves(int employeeId, String fname, String lname) {
        this.employeeId = employeeId;
        setTitle("Manage Leaves for " + fname + " " + lname);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Leave Type:"));
        inputPanel.add(leaveTypeField);
        inputPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusCombo);
        inputPanel.add(submit);

        JScrollPane scrollPane = new JScrollPane(leaveDisplay);
        leaveDisplay.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadExistingLeaves();

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String leaveType = leaveTypeField.getText().trim();
                String startDate = startDateField.getText().trim();
                String endDate = endDateField.getText().trim();
                String status = (String) statusCombo.getSelectedItem();

                if (leaveType.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                    JOptionPane.showMessageDialog(ManageLeaves.this, "All fields are required.");
                    return;
                }

                try {
                    dbConnection.insertLeave(employeeId, leaveType, startDate, endDate, status);
                    JOptionPane.showMessageDialog(ManageLeaves.this, "Leave request submitted successfully.");
                    leaveTypeField.setText("");
                    startDateField.setText("YYYY-MM-DD");
                    endDateField.setText("YYYY-MM-DD");
                    loadExistingLeaves();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ManageLeaves.this, "Error submitting leave: " + ex.getMessage());
                }
            }
        });
    }

    private void loadExistingLeaves() {
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