package Form;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminLogin {
    private final JPasswordField passwordField;

    public AdminLogin() {

        String adminPassword = "myAdmin";

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 250, 450);
        leftPanel.setBackground(new Color(30, 90, 83)); // Background color
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
        horizonLabel.setForeground(Color.WHITE);
        horizonLabel.setFont(new Font("Arial", Font.BOLD, 24));
        horizonLabel.setBounds(50, 140, 150, 30);
        leftPanel.add(horizonLabel);

        JLabel loginLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginLabel.setBounds(50, 180, 150, 20);
        leftPanel.add(loginLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(250, 0, 400, 450);
        rightPanel.setLayout(null);
        frame.add(rightPanel);

        JLabel passwordLabel = new JLabel("Enter Password");
        passwordLabel.setBounds(50, 150, 100, 25);
        rightPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 180, 250, 25);
        rightPanel.add(passwordField);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(200, 230, 100, 30);
        rightPanel.add(signInButton);

        JButton resetButton = new JButton("Back");
        resetButton.setBounds(50, 230, 100, 30);
        rightPanel.add(resetButton);

        resetButton.addActionListener(_ -> {
            new HomePage();
            frame.dispose();
        });

        ActionListener submit = (_ -> {
            String password = String.valueOf(passwordField.getPassword());
            if(!Objects.equals(password, adminPassword))
            {
                JOptionPane.showMessageDialog(null,"Invalid password!",
                        "Wrong",JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
            else{
                new adminSide();
                frame.dispose();
            }
        });

        signInButton.addActionListener(submit);
        passwordField.addActionListener(submit);

        frame.setVisible(true);
    }

    private void ClearPassword()
    {
        passwordField.setText("");
        passwordField.requestFocus();
    }
}