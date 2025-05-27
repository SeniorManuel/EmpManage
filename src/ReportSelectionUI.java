import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportSelectionUI extends JFrame {
    private String fname;
    private String lname;
    private String position;
    private double gross;
    private double sssEmployee;
    private double philhealthEmployee;
    private double pagibigEmployee;
    private double incomeTax;
    private double netPay;

    public ReportSelectionUI(String fname, String lname, String position, double gross, double sssEmployee,
                             double philhealthEmployee, double pagibigEmployee, double incomeTax, double netPay) {
        this.fname = fname;
        this.lname = lname;
        this.position = position;
        this.gross = gross;
        this.sssEmployee = sssEmployee;
        this.philhealthEmployee = philhealthEmployee;
        this.pagibigEmployee = pagibigEmployee;
        this.incomeTax = incomeTax;
        this.netPay = netPay;

        setTitle("Select Report to View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton mandatedReportsButton = new JButton("View Government Mandated Reports");
        JButton birFormButton = new JButton("View BIR Form 2316");
        JButton payslipButton = new JButton("View Payslip");

        panel.add(new JLabel("Select a report to view for " + fname + " " + lname, SwingConstants.CENTER));
        panel.add(mandatedReportsButton);
        panel.add(birFormButton);
        panel.add(payslipButton);


        add(panel);

        ReportGenerator generator = new ReportGenerator();

        mandatedReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String birReport = generator.generateBIRReport();
                    String sssReport = generator.generateSSSReport();
                    String philhealthReport = generator.generatePhilHealthReport();
                    String pagibigReport = generator.generatePagIBIGReport();

                    String combinedMandatedReports = "========== Government Mandated Reports ==========\n\n" +
                            "BIR Report:\n" + birReport + "\n\n" +
                            "--------------------------------------------\n" +
                            "SSS Report:\n" + sssReport + "\n\n" +
                            "--------------------------------------------\n" +
                            "PhilHealth Report:\n" + philhealthReport + "\n\n" +
                            "--------------------------------------------\n" +
                            "Pag-IBIG Report:\n" + pagibigReport + "\n" +
                            "=============================================\n";

                    JFrame mandatedFrame = new JFrame("Government Mandated Reports");
                    JTextArea mandatedTextArea = new JTextArea(combinedMandatedReports);
                    mandatedTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    mandatedTextArea.setEditable(false);
                    JScrollPane mandatedScrollPane = new JScrollPane(mandatedTextArea);
                    mandatedScrollPane.setPreferredSize(new Dimension(600, 500));
                    mandatedFrame.add(mandatedScrollPane);
                    mandatedFrame.pack();
                    mandatedFrame.setLocation(100, 100);
                    mandatedFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error generating mandated reports: " + ex.getMessage());
                }
            }
        });


        birFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String birForm2316 = generator.generateBIRForm2316(fname, lname, position, gross, sssEmployee,
                            philhealthEmployee, pagibigEmployee, incomeTax, netPay);

                    JFrame birFormFrame = new JFrame("BIR Form 2316 - " + fname + " " + lname);
                    JTextArea birFormTextArea = new JTextArea(birForm2316);
                    birFormTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    birFormTextArea.setEditable(false);
                    JScrollPane birFormScrollPane = new JScrollPane(birFormTextArea);
                    birFormScrollPane.setPreferredSize(new Dimension(600, 400));
                    birFormFrame.add(birFormScrollPane);
                    birFormFrame.pack();
                    birFormFrame.setLocation(750, 100);
                    birFormFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error generating BIR Form 2316: " + ex.getMessage());
                }
            }
        });


        payslipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payslip = "Payslip for " + fname + " " + lname + "\n" +
                        "Position: " + position + "\n" +
                        "----------------------------------------\n" +
                        String.format("%-20s ₱%10.2f\n", "Gross Pay:", gross) +
                        "Deductions:\n" +
                        String.format("%-20s ₱%10.2f\n", "  SSS:", sssEmployee) +
                        String.format("%-20s ₱%10.2f\n", "  PhilHealth:", philhealthEmployee) +
                        String.format("%-20s ₱%10.2f\n", "  Pag-IBIG:", pagibigEmployee) +
                        String.format("%-20s ₱%10.2f\n", "  Withholding Tax:", incomeTax) +
                        "----------------------------------------\n" +
                        String.format("%-20s ₱%10.2f\n", "Net Pay:", netPay);

                JFrame payslipFrame = new JFrame("Payslip - " + fname + " " + lname);
                JTextArea payslipTextArea = new JTextArea(payslip);
                payslipTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                payslipTextArea.setEditable(false);
                JScrollPane payslipScrollPane = new JScrollPane(payslipTextArea);
                payslipScrollPane.setPreferredSize(new Dimension(400, 300));
                payslipFrame.add(payslipScrollPane);
                payslipFrame.pack();
                payslipFrame.setLocation(750, 550);
                payslipFrame.setVisible(true);
            }
        });
    }
}