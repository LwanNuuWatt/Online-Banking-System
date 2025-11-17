package Form;

import javax.swing.*;
import java.awt.*;

public class CustomerDetails extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField typeField;
    private JTextField ageField;
    private JTextField balanceField;
    private JTextField regDateField;

    public CustomerDetails() {
        SwingUtilities.invokeLater(() ->{
            setTitle("User Account Details");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            getContentPane().setBackground(new Color(252, 253, 253));
            setLayout(new GridBagLayout()); // Using GridBagLayout for flexible layout

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel titleLabel = new JLabel("Personal Details");
            titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
            titleLabel.setForeground(new Color(30, 90, 83));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4; // Span the title across 4 columns
            add(titleLabel, gbc);

            JLabel idLabel = createStyledLabel("ID:");
            JLabel nameLabel = createStyledLabel("Name:");
            JLabel phoneLabel = createStyledLabel("Phone Number:");
            JLabel addressLabel = createStyledLabel("Address:");
            JLabel typeLabel = createStyledLabel("Account Type:");
            JLabel ageLabel = createStyledLabel("Age:");
            JLabel balanceLabel = createStyledLabel("Balance:");
            JLabel regDateLabel = createStyledLabel("Registration Date:");

            idField = createStyledTextField();
            nameField = createStyledTextField();
            phoneField = createStyledTextField();
            addressField = createStyledTextField();
            typeField = createStyledTextField();
            ageField = createStyledTextField();
            balanceField = createStyledTextField();
            regDateField = createStyledTextField();

            JButton btnBack = new JButton("Back");
            btnBack.setBackground(new Color(81, 85, 88));
            btnBack.setForeground(Color.blue);
            btnBack.setFont(new Font("Arial", Font.BOLD, 16));
            btnBack.setFocusPainted(false);
            btnBack.setBounds(50, 100, 300, 40);

            gbc.gridx = 3; gbc.gridy = 5; gbc.gridwidth = 2;
            add(btnBack,gbc);

            gbc.gridwidth = 1;

            gbc.gridx = 0; gbc.gridy = 1;
            add(idLabel, gbc);
            gbc.gridx = 1; add(idField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            add(nameLabel, gbc);
            gbc.gridx = 1; add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            add(phoneLabel, gbc);
            gbc.gridx = 1; add(phoneField, gbc);

            gbc.gridx = 0; gbc.gridy = 4;
            add(addressLabel, gbc);
            gbc.gridx = 1; add(addressField, gbc);

            gbc.gridx = 2; gbc.gridy = 1;
            add(typeLabel, gbc);
            gbc.gridx = 3; add(typeField, gbc);

            gbc.gridx = 2; gbc.gridy = 2;
            add(ageLabel, gbc);
            gbc.gridx = 3; add(ageField, gbc);

            gbc.gridx = 2; gbc.gridy = 3;
            add(balanceLabel, gbc);
            gbc.gridx = 3; add(balanceField, gbc);

            gbc.gridx = 2; gbc.gridy = 4;
            add(regDateLabel, gbc);
            gbc.gridx = 3; add(regDateField, gbc);

            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.BOLD, 14));
        label.setForeground(new Color(30, 90, 83)); // Dark grey/navy text
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(36, 244, 221));
        textField.setBorder(BorderFactory.createLineBorder(new Color(30, 90, 83)));
        textField.setEditable(false); // Make the text fields read-only
        return textField;
    }

    private void populateFields(String id, String name, String phone, String address,
                                String accountType, String age, String balance, String regDate)
    {
        idField.setText(id);
        nameField.setText(name);
        phoneField.setText(phone);
        addressField.setText(address);
        typeField.setText(accountType);
        ageField.setText(age);
        balanceField.setText(balance);
        regDateField.setText(regDate);
    }
}
