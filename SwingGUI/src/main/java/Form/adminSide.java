package Form;

import javax.swing.*;
import java.awt.*;

public class adminSide extends JFrame {

    public adminSide() {
        SwingUtilities.invokeLater(() ->{
            setTitle("Bank Management System");
            setSize(650, 380);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(null);

            JPanel leftPanel = new JPanel();
            leftPanel.setBounds(0, 0, 250, 450);
            leftPanel.setBackground(new Color(30, 90, 83)); // Background color
            leftPanel.setLayout(null);
            add(leftPanel);

            JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
            horizonLabel.setForeground(Color.WHITE);
            horizonLabel.setFont(new Font("Arial", Font.BOLD, 24));
            horizonLabel.setBounds(50, 140, 150, 30);
            leftPanel.add(horizonLabel);

            JLabel adminLabel = new JLabel("Admin", SwingConstants.CENTER);
            adminLabel.setForeground(Color.WHITE);
            adminLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            adminLabel.setBounds(50, 180, 150, 20);
            leftPanel.add(adminLabel);

            JPanel rightPanel = new JPanel();
            rightPanel.setBounds(250, 0, 400, 450);
            rightPanel.setLayout(null);
            add(rightPanel);

            JButton customerButton = createButton("Customer Account Create", new Color(30, 90, 83), 50);
            customerButton.addActionListener(_ -> {
                new UserAccCreate();
                dispose();
            });
            rightPanel.add(customerButton);

            JButton btnLoan = createButton("Loan Balance", new Color(30,90,83), 120);
            btnLoan.addActionListener(_ -> {
                new LoanEstimateForm();
            });
            rightPanel.add(btnLoan);

            JButton holdButton = createButton("Hold All Accounts", new Color(30, 90, 83), 190);
            holdButton.addActionListener(_ -> {
                new MainFrame();
                dispose();
            });
            rightPanel.add(holdButton);

            JButton backButton = createButton("Back to Main Menu", new Color(30, 90, 83), 260);
            backButton.addActionListener(_ -> {
                new HomePage();
                dispose();
            });
            rightPanel.add(backButton);

            setVisible(true);
        });
    }

    private JButton createButton(String text, Color backgroundColor, int y) {
        return getJButton(text, backgroundColor, y);
    }

    static JButton getJButton(String text, Color backgroundColor, int y) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBounds(50, y, 300, 40); // Set size and position
        return button;
    }
}