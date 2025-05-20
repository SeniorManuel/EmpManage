import javax.swing.*;
import java.awt.*;

public class payResults extends JFrame {
    JLabel fnameLabel, lnameLabel, posLabel, typeLabel;
    JLabel grossLabel, sssLabel, philLabel, pagibigLabel, incomeTaxLabel, netPayLabel;

    JLabel fnameVal, lnameVal, posVal, typeVal;
    JLabel grossVal, sssVal, philVal, pagibigVal, incomeTaxVal, netPayVal;

    GridBagLayout layout;
    Container container;

    public payResults(String fname, String lname, String position, String type,
                      double gross, double sss, double philhealth, double pagibig, double incomeTax, double netPay) {

        setTitle("Payroll Results");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new GridBagLayout();
        container = getContentPane();
        container.setLayout(layout);

        fnameLabel = new JLabel("Firstname: ");
        lnameLabel = new JLabel("Lastname: ");
        posLabel = new JLabel("Position: ");
        typeLabel = new JLabel("Type: ");

        grossLabel = new JLabel("Gross Pay: ");
        sssLabel = new JLabel("SSS: ");
        philLabel = new JLabel("PhilHealth: ");
        pagibigLabel = new JLabel("Pag-IBIG: ");
        incomeTaxLabel = new JLabel("Income Tax: ");
        netPayLabel = new JLabel("Net Pay: ");

        fnameVal = new JLabel(fname);
        lnameVal = new JLabel(lname);
        posVal = new JLabel(position);
        typeVal = new JLabel(type);

        grossVal = new JLabel(String.format("₱ %.2f", gross));
        sssVal = new JLabel(String.format("₱ %.2f", sss));
        philVal = new JLabel(String.format("₱ %.2f", philhealth));
        pagibigVal = new JLabel(String.format("₱ %.2f", pagibig));
        incomeTaxVal = new JLabel(String.format("₱ %.2f", incomeTax));
        netPayVal = new JLabel(String.format("₱ %.2f", netPay));

        add(fnameLabel, 0, 0, 1, 1);
        add(fnameVal, 1, 0, 1, 1);
        add(lnameLabel, 2, 0, 1, 1);
        add(lnameVal, 3, 0, 1, 1);
        add(posLabel, 0, 1, 1, 1);
        add(posVal, 1, 1, 1, 1);
        add(typeLabel, 2, 1, 1, 1);
        add(typeVal, 3, 1, 1, 1);
        add(grossLabel, 0, 4, 1, 1);
        add(grossVal, 1, 4, 1, 1);
        add(sssLabel, 0, 5, 1, 1);
        add(sssVal, 1, 5, 1, 1);
        add(philLabel, 0, 6, 1, 1);
        add(philVal, 1, 6, 1, 1);
        add(pagibigLabel, 0, 7, 1, 1);
        add(pagibigVal, 1, 7, 1, 1);
        add(incomeTaxLabel, 0, 8, 1, 1);
        add(incomeTaxVal, 1, 8, 1, 1);
        add(netPayLabel, 0, 9, 1, 1);
        add(netPayVal, 1, 9, 1, 1);

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
