import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class payResults extends JFrame {
    private JPanel mainPanel;
    private JLabel fnameLabel, lnameLabel, posLabel;
    private JLabel grossLabel, sssLabel, philLabel, pagibigLabel, incomeTaxLabel, netPayLabel;
    private JLabel fnameVal, lnameVal, posVal;
    private JLabel grossVal, sssVal, philVal, pagibigVal, incomeTaxVal, netPayVal;
    private JButton okButton;

    private DecimalFormat currencyFormat = new DecimalFormat("₱#,##0.00");

    public payResults(String fname, String lname, String position,
                      double gross, double sss, double philhealth, double pagibig,
                      double incomeTax, double netPay) {

        setTitle("Payroll Results");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Employee Info
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));

        fnameLabel = new JLabel("First Name:");
        lnameLabel = new JLabel("Last Name:");
        posLabel = new JLabel("Position:");

        fnameVal = new JLabel(fname);
        lnameVal = new JLabel(lname);
        posVal = new JLabel(position);

        infoPanel.add(fnameLabel);
        infoPanel.add(fnameVal);
        infoPanel.add(lnameLabel);
        infoPanel.add(lnameVal);
        infoPanel.add(posLabel);
        infoPanel.add(posVal);

        // Payroll Breakdown
        JPanel payrollPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        payrollPanel.setBorder(BorderFactory.createTitledBorder("Payroll Breakdown"));

        grossLabel = new JLabel("Gross Pay:");
        sssLabel = new JLabel("SSS Contribution:");
        philLabel = new JLabel("PhilHealth:");
        pagibigLabel = new JLabel("Pag-IBIG:");
        incomeTaxLabel = new JLabel("Income Tax:");
        netPayLabel = new JLabel("Net Pay:");

        grossVal = new JLabel(formatCurrency(gross));
        sssVal = new JLabel("-" + formatCurrency(sss));
        philVal = new JLabel("-" + formatCurrency(philhealth));
        pagibigVal = new JLabel("-" + formatCurrency(pagibig));
        incomeTaxVal = new JLabel("-" + formatCurrency(incomeTax));
        netPayVal = new JLabel(formatCurrency(netPay));

        payrollPanel.add(grossLabel);
        payrollPanel.add(grossVal);
        payrollPanel.add(sssLabel);
        payrollPanel.add(sssVal);
        payrollPanel.add(philLabel);
        payrollPanel.add(philVal);
        payrollPanel.add(pagibigLabel);
        payrollPanel.add(pagibigVal);
        payrollPanel.add(incomeTaxLabel);
        payrollPanel.add(incomeTaxVal);
        payrollPanel.add(netPayLabel);
        payrollPanel.add(netPayVal);

        // OK Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okButton = new JButton("OK");
        buttonPanel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add to main panel
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(payrollPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }
}
