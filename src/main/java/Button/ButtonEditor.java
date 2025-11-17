package Button;

import Controller.userController;
import Form.DepositForm;
import Form.TransferForm;
import Form.WithdrawForm;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private boolean clicked;
    private final String PhoneNo;
    private final JTable table;
    private int row;

    public ButtonEditor(JTable table, String Phone_No) {
        super(new JCheckBox());
        this.table = table;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(_ -> fireEditingStopped());
        PhoneNo = Phone_No;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            Long Id = (Long) table.getValueAt(row, 0);
            if(Objects.equals(label, "Transfer"))
            {
                new TransferForm(PhoneNo, Id);
            }
            else if(Objects.equals(label, "Deposit"))
            {
                new DepositForm(PhoneNo, Id);
                userController.updateFixedDeposit(Id);
            }
            else {
                new WithdrawForm(PhoneNo, Id);
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
}
