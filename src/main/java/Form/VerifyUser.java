package Form;

import Model.UserModel;
import UserLists.UserLst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Controller.userController.*;

public class VerifyUser{
    private JPanel verify;
    private JButton btnVerify;
    private JButton btnCancel;
    private JPasswordField txtPassword;
    private final JFrame frame;

    public void ClearControls()
    {
        txtPassword.setText("");
        txtPassword.requestFocus();
    }

    public VerifyUser(String phoneNo, int Column)
    {
        String adminPassword = "myAdmin";
        frame = new JFrame("Verify User");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(380, 330));
        frame.add(verify);
        frame.pack();
        btnCancel.addActionListener(_ -> frame.dispose());
        ActionListener submit = (_ -> {
            if (Column == 9) {
                if (Objects.equals(String.valueOf(txtPassword.getPassword()), adminPassword)) {
                    String status = "Restricted";
                    boolean result = restrictUser(phoneNo, status);
                    String message = result ? "Account restricted successfully!" : "Something went wrong!";
                    JOptionPane.showMessageDialog(frame, message,
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    UserLst.refreshTableData();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password!");
                    txtPassword.setText("");
                    txtPassword.requestFocus();
                }
            } else {
                UserModel userResult = userVerifyForUpdate(phoneNo, String.valueOf(txtPassword.getPassword()));
                if (userResult != null) {
                    new UpdateUser(userResult);
                    frame.dispose();
                } else {
                    ClearControls();
                }
            }
        });

        btnVerify.addActionListener(submit);
        txtPassword.addActionListener(submit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}