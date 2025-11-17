package Form;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

import static CheckPassword.passwordCheck.verifyPassword;
import static Controller.userController.UserLogin;

public class UserAccLogin {

    private final JTextField txtPh_No;
    private final JPasswordField Password;

    public UserAccLogin() {
        // Create JFrame
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Left panel for the company information
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 250, 450);
        leftPanel.setBackground(new Color(30, 90, 83)); // Background color
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        // Horizon label centered
        JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
        horizonLabel.setForeground(Color.WHITE);
        horizonLabel.setFont(new Font("Arial", Font.BOLD, 24));
        horizonLabel.setBounds(50, 140, 150, 30);
        leftPanel.add(horizonLabel);

        // Login label centered below Horizon
        JLabel loginLabel = new JLabel("Login", SwingConstants.CENTER);
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginLabel.setBounds(50, 180, 150, 20);
        leftPanel.add(loginLabel);

        // Right panel for the login form
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(250, 0, 400, 450);
        rightPanel.setLayout(null);
        frame.add(rightPanel);

        // Login title
        JLabel loginTitleLabel = new JLabel("LOGIN");
        loginTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginTitleLabel.setBounds(130, 30, 100, 30);
        rightPanel.add(loginTitleLabel);

        // Username label and text field
        JLabel phoneLabel = new JLabel("Enter Phone Number");
        phoneLabel.setBounds(50, 80, 150, 25);
        rightPanel.add(phoneLabel);

        txtPh_No = new JTextField();
        txtPh_No.setBounds(50, 110, 250, 25);
        rightPanel.add(txtPh_No);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Enter Password");
        passwordLabel.setBounds(50, 150, 100, 25);
        rightPanel.add(passwordLabel);

        Password = new JPasswordField();
        Password.setBounds(50, 180, 250, 25);
        rightPanel.add(Password);

        // Sign In button
        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(200, 230, 100, 30);
        rightPanel.add(signInButton);

        // Reset button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(50, 230, 100, 30);
        rightPanel.add(btnCancel);

        // Add action listener to reset the form fields
        btnCancel.addActionListener(_ ->{
            new HomePage();
            frame.dispose();
        });

        ActionListener submit=(_ -> {
            UserModel user = new UserModel();
            user.setPhoneNo(txtPh_No.getText());
            user = UserLogin(user.getPhoneNo());
            assert user != null;
            byte[] salt = Base64.getDecoder().decode(user.getSalt());
            boolean isValid;

            if(txtPh_No.getText().isEmpty() || String.valueOf(Password.getPassword()).isEmpty())
            {
                JOptionPane.showMessageDialog(frame,"All fields must be filled!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(Objects.equals(user.getStatus(), "Restricted"))
            {
                JOptionPane.showMessageDialog(frame, "Cannot login. Your account is restricted!",
                        "Restrict", JOptionPane.ERROR_MESSAGE);
                txtPh_No.setText("");
                Password.setText("");
                txtPh_No.requestFocus();
            }
            else {
                try {
                    isValid = verifyPassword(String.valueOf(Password.getPassword()), user.getPassword(), salt);
                    if(isValid)
                    {
                        new customerSide(txtPh_No.getText());
                        frame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(frame, "Invalid password!","Error",
                                JOptionPane.ERROR_MESSAGE);
                        Password.setText("");
                        Password.requestFocus();
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        signInButton.addActionListener(submit);
        txtPh_No.addActionListener(submit);
        Password.addActionListener(submit);

        frame.setVisible(true);
    }
}