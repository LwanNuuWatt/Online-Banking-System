package Form;

import Controller.userController;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

import static CheckPassword.passwordCheck.verifyPassword;
import static Controller.userController.*;
import static Hash.passwordHash.hashPassword;

public class UpdatePassword {
    private JPanel updatePassword;
    private JButton btnCancel;
    private JButton btnSave;
    private final JFrame frame;
    private JPasswordField confirmPassword;
    private JPasswordField oldPassword;
    private JPasswordField newPassword;;

    public void ClearControls()
    {
        confirmPassword.setText("");
        oldPassword.setText("");
        newPassword.setText("");
        oldPassword.requestFocus();
    }

    public UpdatePassword(String Phone_No){
        frame = new JFrame("Update Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(370, 330));
        frame.add(updatePassword);
        frame.pack();
        btnCancel.addActionListener(_-> ClearControls());
        ActionListener submit = (_ -> {
            UserModel user = userController.getCustomer(Phone_No);
            assert user != null;
            byte[] salt = Base64.getDecoder().decode(user.getSalt());
            if(String.valueOf(oldPassword.getPassword()).isEmpty() || String.valueOf(confirmPassword.getPassword()).isEmpty())
            {
                JOptionPane.showMessageDialog(frame,"All fields must be filled!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Objects.equals(String.valueOf(newPassword.getPassword()), String.valueOf(confirmPassword.getPassword())))
            {
                JOptionPane.showMessageDialog(null, "New password doesn't match with confirm password!");
                newPassword.setText("");
                confirmPassword.setText("");
                newPassword.requestFocus();
            } else
            {
                try {
                    boolean isValid = verifyPassword(String.valueOf(oldPassword.getPassword()), user.getPassword(), salt);
                    String hashedPassword = hashPassword(String.valueOf(confirmPassword.getPassword()), salt);
                    if(isValid)
                    {
                        userController.updatePassword(Phone_No, hashedPassword);
                        frame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(frame, "Invalid password!","Error",
                                JOptionPane.ERROR_MESSAGE);
                        confirmPassword.setText("");
                        oldPassword.requestFocus();
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnSave.addActionListener(submit);
        oldPassword.addActionListener(submit);
        confirmPassword.addActionListener(submit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
