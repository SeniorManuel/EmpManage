import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gui extends JFrame {
    JLabel fname, lname, pos, mrate, dwork;
    JTextField tfname, tlname, tpos, tmrate, tdwork;
    JButton add, update, delete, genResuslts, payResults, present;
    DefaultTableModel dtable;
    JTable table;
    GridBagLayout layout;
    Container container;

    public Gui() {
        fname = new JLabel("Firstname: ");
        lname = new JLabel("Lastname: ");
        pos = new JLabel("Position: ");
        mrate = new JLabel("Monthly Rate: ");
        dwork = new JLabel("Days Work per Month: ");

        tfname = new JTextField(7);
        tlname = new JTextField(7);
        tpos = new JTextField(7);
        tmrate = new JTextField(7);
        tdwork = new JTextField(7);

        add = new JButton("Add Employee");
        update = new JButton("Update Employee");
        delete = new JButton("Delete Employee");
        genResuslts = new JButton("Generate Results");
        payResults = new JButton("Calculate Payroll");
        present = new JButton("Mark Attendance");

        dtable = new DefaultTableModel(new Object[]{"ID", "Firstname", "Lastname", "Position", "Rate", "Days Work"}, 0);
        table = new JTable(dtable);
        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);

        add(fname, 0, 0, 1, 1);
        add(tfname, 1, 0, 1, 1);
        add(lname, 2, 0, 1, 1);
        add(tlname, 3, 0, 1, 1);
        add(pos, 0, 1, 1, 1);
        add(tpos, 1, 1, 1, 1);
        add(mrate, 2, 1, 1, 1);
        add(tmrate, 3, 1, 1, 1);
        add(dwork, 0, 2, 1, 1);
        add(tdwork, 1, 2, 1, 1);

        add(add, 0, 4, 2, 1);
        add(present, 2, 4, 2, 1);
        add(genResuslts, 2,5,2,1);
        add(payResults, 0, 5, 2, 1);
        add(new JScrollPane(table), 0, 6, 4, 1);
        add(update, 0, 7, 2, 1);
        add(delete, 2, 7, 2, 1);

        this.setTitle("Employee Management System");
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void add(Component component, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = gridx;
        grid.gridy = gridy;
        grid.gridwidth = gridw;
        grid.gridheight = gridh;
        grid.fill = GridBagConstraints.BOTH;
        container.add(component, grid);
    }
}
