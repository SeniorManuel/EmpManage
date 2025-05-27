import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gui extends JFrame {
    JLabel fname, lname, pos, mrate;
    JTextField tfname, tlname, tpos, tmrate;
    JButton add, update, delete, genResults, payResults, present;
    DefaultTableModel dtable;
    JTable table;
    GridBagLayout layout;
    Container container;

    public Gui() {
        fname = new JLabel("Firstname:");
        lname = new JLabel("Lastname:");
        pos = new JLabel("Position:");
        mrate = new JLabel("Monthly Rate:");

        tfname = new JTextField(12);
        tlname = new JTextField(12);
        tpos = new JTextField(12);
        tmrate = new JTextField(12);

        add = new JButton("Add Employee");
        update = new JButton("Update Employee");
        delete = new JButton("Delete Employee");
        genResults = new JButton("Generate Results");
        payResults = new JButton("Calculate Payroll");
        present = new JButton("Mark Attendance");

        Dimension bSize = new Dimension(200, 30);
        add.setPreferredSize(bSize);
        update.setPreferredSize(bSize);
        delete.setPreferredSize(bSize);
        genResults.setPreferredSize(bSize);
        payResults.setPreferredSize(bSize);
        present.setPreferredSize(bSize);

        dtable = new DefaultTableModel(new String[]{"ID", "Firstname", "Lastname", "Position", "Rate", "Days Worked"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(dtable);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel mainPanel = new JPanel();
        layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        JPanel panDetails = new JPanel(new GridBagLayout());
        panDetails.setBorder(BorderFactory.createTitledBorder("Add Employee Details"));
        addP(panDetails, fname, 0, 0, 1, 1);
        addP(panDetails, tfname, 1, 0, 1, 1);
        addP(panDetails, lname, 2, 0, 1, 1);
        addP(panDetails, tlname, 3, 0, 1, 1);
        addP(panDetails, pos, 0, 1, 1, 1);
        addP(panDetails, tpos, 1, 1, 1, 1);
        addP(panDetails, mrate, 2, 1, 1, 1);
        addP(panDetails, tmrate, 3, 1, 1, 1);
        add(mainPanel, panDetails, 0, 0, 4, 1);

        JPanel panActions = new JPanel(new GridBagLayout());
        panActions.setBorder(BorderFactory.createTitledBorder("Actions"));
        addP(panActions, add, 0, 0, 2, 1);
        addP(panActions, present, 2, 0, 2, 1);
        addP(panActions, payResults, 0, 1, 2, 1);
        addP(panActions, genResults, 2, 1, 2, 1);
        add(mainPanel, panActions, 0, 1, 4, 1);

        JPanel panTable = new JPanel(new BorderLayout());
        panTable.setBorder(BorderFactory.createTitledBorder("Employee Records"));
        panTable.add(new JScrollPane(table), BorderLayout.CENTER);
        add(mainPanel, panTable, 0, 2, 4, 1);

        JPanel panBottom = new JPanel(new GridBagLayout());
        panBottom.setBorder(BorderFactory.createTitledBorder("Modify Records"));
        addP(panBottom, update, 0, 0, 2, 1);
        addP(panBottom, delete, 2, 0, 2, 1);
        add(mainPanel, panBottom, 0, 3, 4, 1);

        JScrollPane scrollPane = new JScrollPane(mainPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(scrollPane);
        scrollPane.setPreferredSize(new Dimension(600, 600));

        this.setTitle("Employee Management System");
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void add(Container parent, Component component, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = gridx;
        grid.gridy = gridy;
        grid.gridwidth = gridw;
        grid.gridheight = gridh;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 10, 5, 10);
        parent.add(component, grid);
    }

    public void addP(JPanel panel, Component component, int gridx, int gridy, int gridw, int gridh) {
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = gridx;
        grid.gridy = gridy;
        grid.gridwidth = gridw;
        grid.gridheight = gridh;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 10, 5, 10);
        panel.add(component, grid);
    }
}