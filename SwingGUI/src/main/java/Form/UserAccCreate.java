package Form;

import CheckPassword.passwordCheck;
import Controller.userController;
import Hash.passwordHash;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class UserAccCreate {
    private final JTextField txtName;
    private final JTextField txtPhNo;
    private final JTextField txtAge;
    private final JTextField txtAddress;
    private final JPasswordField Password;
    private final JPasswordField confirmPassword;
    private final JCheckBox showPasswordCheckbox;
    private final JCheckBox showPassword;
    private String selectedOption = "Current";

    public UserAccCreate() {

        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(900, 650);
        frame.setLayout(null);

        // Left panel for branding and title
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 350, 645);
        leftPanel.setBackground(new Color(30, 90, 83));
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        // Logo image at the top
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(75, 100, 200, 200);
        setLogoImage(logoLabel);
        leftPanel.add(logoLabel);

        // Horizon title label
        JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
        horizonLabel.setForeground(Color.WHITE);
        horizonLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        horizonLabel.setBounds(75, 300, 200, 30);
        leftPanel.add(horizonLabel);

        // Create Account label below Horizon title
        JLabel createAccountLabel = new JLabel("Create Account", SwingConstants.CENTER);
        createAccountLabel.setForeground(Color.WHITE);
        createAccountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        createAccountLabel.setBounds(75, 340, 200, 20);
        leftPanel.add(createAccountLabel);

        // Right panel for the form inputs
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(350, 0, 550, 645);
        rightPanel.setLayout(null);
        frame.add(rightPanel);

        // Sign Up title label at the top center
        JLabel signUpLabel = new JLabel("SIGN UP");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 22));
        signUpLabel.setBounds(225, 30, 100, 30);
        rightPanel.add(signUpLabel);

        // Name label and text field
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setBounds(50, 70, 400, 25);
        rightPanel.add(nameLabel);

        txtName = new JTextField();
        txtName.setBounds(50, 95, 400, 30);
        rightPanel.add(txtName);

        // Phone Number label and text field
        JLabel phoneLabel = new JLabel("Enter Phone Number:");
        phoneLabel.setBounds(50, 135, 400, 25);
        rightPanel.add(phoneLabel);

        txtPhNo = new JTextField();
        txtPhNo.setBounds(50, 160, 400, 30);
        rightPanel.add(txtPhNo);

        // Dropdown list (JComboBox)
        JLabel comboBoxLabel = new JLabel("Select Account Type:");
        comboBoxLabel.setBounds(50, 200, 400, 25);
        rightPanel.add(comboBoxLabel);

        String[] options = {"Current", "Fixed"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(50, 225, 400, 30);
        rightPanel.add(comboBox);

        JLabel selectedOptionLabel = new JLabel("Selected Option: None");
        selectedOptionLabel.setBounds(50, 260, 400, 25);
        rightPanel.add(selectedOptionLabel);

        comboBox.addActionListener(_ -> {
            selectedOption = String.valueOf(comboBox.getSelectedItem());
            selectedOptionLabel.setText("Selected Option: " + selectedOption);
        });

        // Age label and text field
        JLabel ageLabel = new JLabel("Enter Age:");
        ageLabel.setBounds(50, 290, 400, 25);
        rightPanel.add(ageLabel);

        txtAge = new JTextField();
        txtAge.setBounds(50, 315, 400, 30);
        rightPanel.add(txtAge);

        // Address label and text field
        JLabel addressLabel = new JLabel("Enter Address:");
        addressLabel.setBounds(50, 355, 400, 25);
        rightPanel.add(addressLabel);

        txtAddress = new JTextField();
        txtAddress.setBounds(50, 380, 400, 30);
        rightPanel.add(txtAddress);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 420, 400, 25);
        rightPanel.add(passwordLabel);

        Password = new JPasswordField();
        Password.setBounds(50, 445, 400, 30);
        rightPanel.add(Password);

        showPasswordCheckbox = new JCheckBox();
        showPasswordCheckbox.setFont(new Font("Helvetica", Font.PLAIN, 14));
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.setBounds(460, 445, 30, 25);
        rightPanel.add(showPasswordCheckbox);
        setLogoImageForPassword("hidePasswordIcon.png");

        // Show/Hide password functionality
        showPasswordCheckbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Password.setEchoChar((char) 0); // Show password
                setLogoImageForPassword("showPasswordIcon.png");
            } else {
                Password.setEchoChar('•'); // Hide password
                setLogoImageForPassword("hidePasswordIcon.png");
            }
        });

        // Password hint
        JLabel passwordHintLabel = new JLabel("<html>At least 6 characters, 1 special character, and 1 digit</html>");
        passwordHintLabel.setForeground(Color.RED);
        passwordHintLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        passwordHintLabel.setBounds(50, 475, 400, 25);
        rightPanel.add(passwordHintLabel);

        // Confirm Password label and field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 505, 400, 25);
        rightPanel.add(confirmPasswordLabel);

        confirmPassword = new JPasswordField();
        confirmPassword.setBounds(50, 530, 400, 30);
        rightPanel.add(confirmPassword);

        showPassword = new JCheckBox();
        showPassword.setFont(new Font("Helvetica", Font.PLAIN, 14));
        showPassword.setOpaque(false);
        showPassword.setBounds(460, 530, 30, 25);
        rightPanel.add(showPassword);
        setLogoImageForConfirmPassword("hidePasswordIcon.png");

        showPassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                confirmPassword.setEchoChar((char) 0); // Show password
                setLogoImageForConfirmPassword("showPasswordIcon.png");
            } else {
                confirmPassword.setEchoChar('•'); // Hide password
                setLogoImageForConfirmPassword("hidePasswordIcon.png");
            }
        });

        // Sign Up button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 580, 100, 30);
        rightPanel.add(backButton);

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(200, 580, 100, 30);
        rightPanel.add(resetButton);

        // Back button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(350, 580, 100, 30);
        rightPanel.add(signUpButton);

        // Action listeners for buttons
        backButton.addActionListener(_ -> {
            new adminSide();
            frame.dispose();
        });

        resetButton.addActionListener(_ -> ClearControls());

        // Add action listeners to fields
        ActionListener submit = (_ -> {
            String hashedPassword;
            byte[] salt = passwordHash.generateSalt();
            String SaltBase64 = Base64.getEncoder().encodeToString(salt);
            UserModel user = new UserModel();
            int age = Integer.parseInt(txtAge.getText().trim());
            try {
                hashedPassword = passwordHash.hashPassword(String.valueOf(Password.getPassword()).trim(), salt);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException a) {
                JOptionPane.showMessageDialog(null, "Error hashing password: " + a.getMessage());
                return;
            }
            if (userController.CheckUser(txtPhNo.getText())) {
                JOptionPane.showMessageDialog(null, "User with this phone number already exists.");
                ClearPhoneNo();
            } else if (Objects.equals(txtName.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Please enter your Name!");
                txtName.requestFocus();
            } else if (Objects.equals(txtPhNo.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Please enter your Phone No!");
                txtPhNo.requestFocus();
            } else if (txtPhNo.getText().length() < 11) {
                JOptionPane.showMessageDialog(null, "Invalid Phone number.");
                ClearPhoneNo();
            } else if (Objects.equals(txtAge.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Please enter age!");
                txtAge.requestFocus();
            } else if (Objects.equals(txtAddress.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Please enter address!");
                txtAddress.requestFocus();
            } else if (Objects.equals(String.valueOf(Password.getPassword()), "")) {
                JOptionPane.showMessageDialog(null, "Please enter password!");
                Password.requestFocus();
            } else if (Objects.equals(String.valueOf(confirmPassword.getPassword()), "")) {
                JOptionPane.showMessageDialog(null, "Please enter confirm Password!");
                confirmPassword.requestFocus();
            } else if (!passwordCheck.isValidPassword(String.valueOf(Password.getPassword()))) {
                ClearPassword();
            } else if (!Arrays.equals(Password.getPassword(), confirmPassword.getPassword())) {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
                ClearPassword();
            }else if(age < 18){
                JOptionPane.showMessageDialog(null, "You are too young to use this system.");
                txtAge.setText("");
                txtAge.requestFocus();
            }
            else {
                user.setName(txtName.getText().trim());
                user.setPhoneNo(txtPhNo.getText().trim());
                user.setAge(age);
                user.setAddress(txtAddress.getText().trim());
                user.setPassword(hashedPassword);
                user.setAmount(BigDecimal.valueOf(0.0));
                user.setSalt(SaltBase64);
                String status = selectedOption;
                user.setStatus(status);

                boolean result = userController.CreateUser(user);
                if (result && Objects.equals(selectedOption, "Fixed"))
                {
                    new DepositForFix(txtPhNo.getText());
                }else {
                    new DepositPanel(txtPhNo.getText());
                }
                frame.dispose();
                ClearControls();
            }
        });

        signUpButton.addActionListener(submit);
        txtName.addActionListener(submit);
        txtAge.addActionListener(submit);
        txtAddress.addActionListener(submit);
        txtPhNo.addActionListener(submit);
        Password.addActionListener(submit);
        confirmPassword.addActionListener(submit);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void ClearControls() {
        txtName.setText("");
        txtPhNo.setText("");
        txtAge.setText("");
        txtAddress.setText("");
        Password.setText("");
        confirmPassword.setText("");
        txtName.requestFocus();
    }

    public void ClearPassword() {
        Password.setText("");
        confirmPassword.setText("");
        Password.requestFocus();
    }

    public void ClearPhoneNo() {
        txtPhNo.setText("");
        txtPhNo.requestFocus();
    }

    private void setLogoImageForPassword(String path) {
        try {
            ImageIcon logoIcon = new ImageIcon(path);
            showPasswordCheckbox.setIcon(new ImageIcon(logoIcon.getImage()
                    .getScaledInstance(30, 25, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setLogoImageForConfirmPassword(String path) {
        try {
            ImageIcon logoIcon = new ImageIcon(path);
            showPassword.setIcon(new ImageIcon(logoIcon.getImage()
                    .getScaledInstance(30, 25, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setLogoImage(JLabel logoLabel) {
        try {
            ImageIcon logoIcon = new ImageIcon("Logo.png");
            logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(200, 200,
                    Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
