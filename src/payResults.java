import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class payResults extends JFrame {
    // UI Components
    private JPanel mainPanel, headerPanel, employeeInfoPanel, payrollDetailsPanel, buttonPanel;
    private JLabel titleLabel;
    private JLabel fnameLabel, lnameLabel, posLabel;
    private JLabel grossLabel, sssLabel, philLabel, pagibigLabel, incomeTaxLabel, netPayLabel;
    private JLabel fnameVal, lnameVal, posVal;
    private JLabel grossVal, sssVal, philVal, pagibigVal, incomeTaxVal, netPayVal;
    private JButton okButton;

    // Colors and Fonts
    private final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private final Color HEADER_COLOR = new Color(34, 139, 34); // Dark green
    private final Color PANEL_COLOR = Color.WHITE;
    private final Color LABEL_COLOR = new Color(73, 80, 87);
    private final Color VALUE_COLOR = new Color(40, 167, 69);
    private final Color NEGATIVE_COLOR = new Color(220, 53, 69);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font VALUE_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font NET_PAY_FONT = new Font("Segoe UI", Font.BOLD, 16);

    // Formatter
    private DecimalFormat currencyFormat = new DecimalFormat("₱#,##0.00");

    public payResults(String fname, String lname, String position,
                      double gross, double sss, double philhealth, double pagibig, double incomeTax, double netPay) {

        // Frame setup
        setTitle("Payroll Results");
        setSize(650, 580); // Further increased size for better text visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize components
        initializeComponents(fname, lname, position, gross, sss, philhealth, pagibig, incomeTax, netPay);
        setupLayout();

        setVisible(true);
    }

    private void initializeComponents(String fname, String lname, String position,
                                      double gross, double sss, double philhealth, double pagibig,
                                      double incomeTax, double netPay) {

        // Main panel with background
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel
        headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        titleLabel = new JLabel("Payroll Results", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Employee Information Panel
        employeeInfoPanel = new JPanel(new GridLayout(2, 4, 20, 15)); // Increased spacing
        employeeInfoPanel.setBackground(PANEL_COLOR);
        employeeInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                        "Employee Information",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        SECTION_FONT,
                        LABEL_COLOR
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Increased padding
        ));

        // Employee info labels
        fnameLabel = createLabel("First Name:");
        lnameLabel = createLabel("Last Name:");
        posLabel = createLabel("Position:");

        // Employee info values
        fnameVal = createValueLabel(fname);
        lnameVal = createValueLabel(lname);
        posVal = createValueLabel(position);

        // Add to employee info panel
        employeeInfoPanel.add(fnameLabel);
        employeeInfoPanel.add(lnameLabel);
        employeeInfoPanel.add(posLabel);
        employeeInfoPanel.add(fnameVal);
        employeeInfoPanel.add(lnameVal);
        employeeInfoPanel.add(posVal);

        // Payroll Details Panel
        payrollDetailsPanel = new JPanel(new GridLayout(6, 2, 30, 18)); // Further increased spacing
        payrollDetailsPanel.setBackground(PANEL_COLOR);
        payrollDetailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                        "Payroll Breakdown",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        SECTION_FONT,
                        LABEL_COLOR
                ),
                BorderFactory.createEmptyBorder(25, 25, 25, 25) // Further increased padding
        ));

        // Payroll labels - all same size, not bold
        grossLabel = createLabel("Gross Pay:");
        sssLabel = createLabel("SSS Contribution:");
        philLabel = createLabel("PhilHealth:");
        pagibigLabel = createLabel("Pag-IBIG:");
        incomeTaxLabel = createLabel("Income Tax:");
        netPayLabel = createLabel("Net Pay:");

// Payroll values with formatted and color-coded values
        grossVal = createCurrencyLabel(gross, VALUE_COLOR);
        sssVal = createCurrencyLabel(sss, NEGATIVE_COLOR);           // Red for deduction
        philVal = createCurrencyLabel(philhealth, NEGATIVE_COLOR);   // Red for deduction
        pagibigVal = createCurrencyLabel(pagibig, NEGATIVE_COLOR);   // Red for deduction
        incomeTaxVal = createCurrencyLabel(incomeTax, NEGATIVE_COLOR); // Red for deduction
        netPayVal = createCurrencyLabel(netPay, VALUE_COLOR);

        // Add to payroll details panel
        payrollDetailsPanel.add(grossLabel);
        payrollDetailsPanel.add(grossVal);
        payrollDetailsPanel.add(sssLabel);
        payrollDetailsPanel.add(sssVal);
        payrollDetailsPanel.add(philLabel);
        payrollDetailsPanel.add(philVal);
        payrollDetailsPanel.add(pagibigLabel);
        payrollDetailsPanel.add(pagibigVal);
        payrollDetailsPanel.add(incomeTaxLabel);
        payrollDetailsPanel.add(incomeTaxVal);
        payrollDetailsPanel.add(netPayLabel);
        payrollDetailsPanel.add(netPayVal);

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.setBackground(new Color(0, 123, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(LABEL_FONT);
        okButton.setFocusPainted(false);
        okButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okButton.setBackground(new Color(0, 86, 179));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                okButton.setBackground(new Color(0, 123, 255));
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(okButton);
    }

    private void setupLayout() {
        // Create content panels
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20)); // Increased spacing
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(employeeInfoPanel, BorderLayout.NORTH);
        contentPanel.add(payrollDetailsPanel, BorderLayout.CENTER);

        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(VALUE_FONT);
        label.setForeground(VALUE_COLOR);
        return label;
    }

    private JLabel createCurrencyLabel(double amount, Color color) {
        JLabel label = new JLabel(currencyFormat.format(amount));
        label.setFont(VALUE_FONT);
        label.setForeground(color);
        return label;
    }

    private JLabel createUniformCurrencyLabel(double amount) {
        JLabel label = new JLabel(currencyFormat.format(amount));
        label.setFont(VALUE_FONT);
        label.setForeground(VALUE_COLOR); // All values in green
        return label;
    }
}
