import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gui extends JFrame {
    JLabel flabel;
    JLabel llable;
    JTextField ftext;
    JTextField ltext;
    JButton submit;
    DefaultTableModel dtable;
    JTable table;
    GridBagLayout layout;
    Container container;

    public Gui() {
        flabel = new JLabel("FirstName: ");
        llable = new JLabel("LastName: ");
        ftext = new JTextField(8);
        ltext = new JTextField(8);
        submit = new JButton("Submit");
        dtable = new DefaultTableModel(new Object[]{"Firstname", "Lastname"}, 0);
        table = new JTable(dtable);
        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);

        add(flabel, 0,0,1,1);
        add(llable, 0,1,1,1);
        add(ftext,1,0,1,1);
        add(ltext, 1,1,1,1);
        add(submit,2,0,1,2);
        add(new JScrollPane(table), 0,2,3,1);

        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void add(Component component, int gridx, int gridy, int gridw, int gridh ){
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = gridx;
        grid.gridy = gridy;
        grid.gridwidth = gridw;
        grid.gridheight = gridh;
        grid.fill = GridBagConstraints.BOTH;
        container.add(component, grid);
    }


}
