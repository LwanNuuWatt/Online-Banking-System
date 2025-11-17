package Button;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.userController;
import Form.VerifyUser;
import Form.verifyForRestrict;

import static UserLists.UserLst.refreshTableData;

public class Editor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private final JButton button;
    private String label;
    private boolean clicked;
    private final JTable table;
    private int row;
    private int column;

    public Editor(JTable table) {
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        this.row = row;
        this.column = column;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            String phoneNo;
            if(column == 9)
            {
                phoneNo = (String) table.getValueAt(row, column-4);
                String status = (String) table.getValueAt(row, column-2);
                new verifyForRestrict(phoneNo, status);
            }
            else if(column == 10)
            {
                phoneNo = (String) table.getValueAt(row, column-5);
                new VerifyUser(phoneNo, column);
            }else {
                phoneNo = (String) table.getValueAt(row, column-6);
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete the user?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    boolean result = userController.deleteUser(phoneNo);
                    String message = result ? "Deleting successful." : "Deleting failed.";
                    JOptionPane.showMessageDialog(null, message);
                    refreshTableData();
                } else {
                    JOptionPane.showMessageDialog(null, "Deletion canceled");
                }
            }
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }
}