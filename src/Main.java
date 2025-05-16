import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Gui frame = new Gui();
        //tfname, tlname, tpos, tmrate, tdwork

        frame.add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fget = frame.tfname.getText();
                String lget = frame.tlname.getText();
                String pget = frame.tpos.getText();
                String mget= frame.tmrate.getText();
                String wget = frame.tdwork.getText();
                String tget = frame.ttype.getText();
                if (!fget.isEmpty() && !lget.isEmpty()) {
                    frame.dtable.addRow(new Object[]{fget, lget, pget, tget, mget, wget});
                    frame.tfname.setText("");
                    frame.tlname.setText("");
                    frame.tpos.setText("");
                    frame.ttype.setText("");
                    frame.tmrate.setText("");
                    frame.tdwork.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "very good!");
                }
            }
        });

                frame.update.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = frame.table.getSelectedRow();
                        if (selectedRow != -1) {
                            String fget = frame.tfname.getText();
                            String lget = frame.tlname.getText();
                            String pget = frame.tpos.getText();
                            String mget = frame.tmrate.getText();
                            String wget = frame.tdwork.getText();
                            String tget = frame.ttype.getText();

                            if (fget.isEmpty() || lget.isEmpty() || pget.isEmpty() || mget.isEmpty() || wget.isEmpty() || tget.isEmpty()) {
                                JOptionPane.showMessageDialog(frame, " retype your changes ");
                                return;
                            }

                            try {
                                Double.parseDouble(mget);
                                Integer.parseInt(wget);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, " Monthly Rate and Days Worked must be number ");
                                return;
                            }

                            frame.dtable.setValueAt(fget, selectedRow, 0);
                            frame.dtable.setValueAt(lget, selectedRow, 1);
                            frame.dtable.setValueAt(pget, selectedRow, 2);
                            frame.dtable.setValueAt(tget, selectedRow, 3);
                            frame.dtable.setValueAt(mget, selectedRow, 4);
                            frame.dtable.setValueAt(wget, selectedRow, 5);

                            frame.tfname.setText("");
                            frame.tlname.setText("");
                            frame.tpos.setText("");
                            frame.ttype.setText("");
                            frame.tmrate.setText("");
                            frame.tdwork.setText("");
                        } else {
                            JOptionPane.showMessageDialog(frame, " select a row of employee you want to update ");
                        }
                    }
                });
            }
        }

