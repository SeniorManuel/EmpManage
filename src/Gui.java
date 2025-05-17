import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gui extends JFrame {
    JLabel fname, lname, pos, mrate, dwork, type, presentDays, absentDays;
    JTextField tfname, tlname, tpos, tmrate, tdwork, ttype, tpresent, tabsent;
    JButton add, update, delete, load, markAttendance;
    DefaultTableModel dtable;
    JTable table;
    GridBagLayout layout;
    Container container;
    int empId = 1;

    public Gui() {
        fname = new JLabel("Firstname: ");
        lname = new JLabel("Lastname: ");
        pos = new JLabel("Position: ");
        mrate = new JLabel("Monthly Rate: ");
        dwork = new JLabel("Days Work: ");
        type = new JLabel("Type: ");
        presentDays = new JLabel("Present Days: ");
        absentDays = new JLabel("Absent Days: ");

        tfname = new JTextField(7);
        tlname = new JTextField(7);
        tpos = new JTextField(7);
        tmrate = new JTextField(7);
        tdwork = new JTextField(7);
        ttype = new JTextField(7);
        tpresent = new JTextField(7);
        tabsent = new JTextField(7);

        add = new JButton("Add Employee");
        update = new JButton("Update Employee");
        delete = new JButton("Delete Employee");
        load = new JButton("Load All Employees");
        markAttendance = new JButton("Mark Attendance");

        dtable = new DefaultTableModel(new Object[]{"ID", "Firstname", "Lastname", "Position", "Type", "Rate", "Days Worked", "Present", "Absent"}, 0);
        table = new JTable(dtable);
        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);

        add(fname, 0,0,1,1);
        add(tfname, 1,0,1,1);
        add(lname, 2,0,1,1);
        add(tlname, 3,0,1,1);
        add(pos, 0,1,1,1);
        add(tpos, 1,1,1,1);
        add(mrate, 2,1,1,1);
        add(tmrate, 3,1,1,1);
        add(dwork, 0,2,1,1);
        add(tdwork, 1,2,1,1);
        add(type ,2,2,1,1);
        add(ttype, 3,2,1,1);
        add(presentDays, 0,3,1,1);
        add(tpresent, 1,3,1,1);
        add(absentDays, 2,3,1,1);
        add(tabsent, 3,3,1,1);
        add(add, 0,4,2,1);
        add(load, 2,4,2,1);
        add(markAttendance, 0,5,4,1);
        add(new JScrollPane(table), 0,6,4,1);
        add(update, 0,7,2,1);
        add(delete, 2,7,2,1);

        add.addActionListener(e -> {
            String id = String.valueOf(empId++);
            String fname = tfname.getText();
            String lname = tlname.getText();
            String position = tpos.getText();
            String type = ttype.getText();
            String rate = tmrate.getText();
            String daysWorked = tdwork.getText();
            String present = tpresent.getText();
            String absent = tabsent.getText();
            dtable.addRow(new Object[]{id, fname, lname, position, type, rate, daysWorked, present, absent});
        });

        markAttendance.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int present = Integer.parseInt(JOptionPane.showInputDialog("Enter Present Days:"));
                    int absent = Integer.parseInt(JOptionPane.showInputDialog("Enter Absent Days:"));
                    dtable.setValueAt(String.valueOf(present), selectedRow, 7);
                    dtable.setValueAt(String.valueOf(absent), selectedRow, 8);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

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

    public static void main(String[] args) {
        new Gui();
    }
}
