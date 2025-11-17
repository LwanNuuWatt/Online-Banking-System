package Form;

import Model.UserModel;
import UserLists.UserLst;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import static Controller.userController.updateUser;

public class UpdateUser {
    private JPanel Update;
    private JTextField txtName;
    private JTextField txtPhoneNo;
    private JButton btnCancel;
    private JButton btnSave;
    private final JFrame frame;
    private String message;

    public void ClearControls()
    {
        txtName.setText("");
        txtPhoneNo.setText("");
        txtName.requestFocus();
    }

    public UpdateUser(UserModel user) {
        frame = new JFrame("Update User");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(370, 330));
        frame.add(Update);
        frame.pack();
        btnCancel.addActionListener(_ -> ClearControls());
        ActionListener submit = (_ -> {
            if(Objects.equals(txtName.getText(), "") && Objects.equals(txtPhoneNo.getText(), ""))
            {
                message = updateUser((int) user.getId(), user.getName(), user.getPhoneNo());
            }
            else if(Objects.equals(txtName.getText(), ""))
            {
                message = updateUser((int) user.getId(), user.getName(), txtPhoneNo.getText());
            }
            else if(Objects.equals(txtPhoneNo.getText(), ""))
            {
                message = updateUser((int) user.getId(), txtName.getText(), user.getPhoneNo());
            }
            else{
                message = updateUser((int) user.getId(), txtName.getText(), txtPhoneNo.getText());
            }
            if(Objects.equals(message, "Update successful"))
            {
                JOptionPane.showMessageDialog(null, message);
                frame.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, message);
                ClearControls();
            }

            UserLst.refreshTableData();
        });

        btnSave.addActionListener(submit);
        txtName.addActionListener(submit);
        txtPhoneNo.addActionListener(submit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
