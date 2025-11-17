package Form;

import Model.UserModel;
import UserLists.UserLst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Controller.userController.*;

public class verifyForRestrict extends JFrame{
    private JButton btnVerify;
    private JButton btnCancel;
    private JPasswordField txtPassword;
    private JPanel restrictVerify;

    public void ClearControls()
    {
        txtPassword.setText("");
        txtPassword.requestFocus();
    }

    public verifyForRestrict(String phoneNo, String status) {
        String adminPassword = "myAdmin";
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(380, 330));
        add(restrictVerify);
        pack();
        btnCancel.addActionListener(_ -> dispose());
        ActionListener submit = (_ -> {
            if(Objects.equals(String.valueOf(txtPassword.getPassword()), adminPassword))
            {
                if(Objects.equals(status, "Restricted"))
                {
                    boolean result = checkUserFromFixed(phoneNo);
                    String Status = result ? "Fixed" : "Current";
                    boolean res = restrictUser(phoneNo, Status);
                    String message = res ? "Account unrestricted successfully!" : "Something went wrong!";
                    JOptionPane.showMessageDialog(null, message,
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    String sts = "Restricted";
                    boolean result = restrictUser(phoneNo, sts);
                    String message = result ? "Account restricted successfully!" : "Something went wrong!";
                    JOptionPane.showMessageDialog(null, message,
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                UserLst.refreshTableData();
                dispose();
            }
             else {
                JOptionPane.showMessageDialog(null, "Wrong password!");
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        });

        btnVerify.addActionListener(submit);
        txtPassword.addActionListener(submit);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
