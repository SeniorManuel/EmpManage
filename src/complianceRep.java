import javax.swing.*;
import java.awt.*;

public class complianceRep extends JFrame {
    public JButton bsss, bphealth, bpagibig, bbir, bgenerate, bdone;
    public JTextArea reportArea;

    GridBagLayout layout;
    Container container;

    public complianceRep() {
        setTitle("Generate Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new GridBagLayout();
        container = getContentPane();
        container.setLayout(layout);

        bsss = new JButton("SSS");
        bphealth = new JButton("PhilHealth");
        bpagibig = new JButton("Pag-Ibig");
        bbir = new JButton("BIR");
        bgenerate = new JButton("Generate Payslip");
        bdone = new JButton("Done");

        reportArea = new JTextArea(25, 40);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(reportArea);

        add(bsss, 0, 0, 1, 1);
        add(bphealth, 0, 1, 1, 1);
        add(bpagibig, 0, 2, 1, 1);
        add(bbir, 0, 3, 1, 1);
        add(bgenerate, 0, 4, 1, 1);
        add(bdone, 0, 5, 1, 1);

        add(scrollPane, 1, 0, 1, 6);

        setVisible(true);
    }

    public void add(Component component, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridw;
        gbc.gridheight = gridh;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        container.add(component, gbc);
    }
}
