package Button;

import Controller.userController;
import Form.LoanDetailsForm;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class btnEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private boolean isPushed;
    private final JTable table;

    public btnEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(_ -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            Long ID = (Long) table.getValueAt(row, 1);
            if(Objects.equals(label, "Details"))
            {
                new LoanDetailsForm(ID);
            }
            else {
                userController.deleteLoan(ID);
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}