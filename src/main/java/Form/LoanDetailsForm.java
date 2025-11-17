package Form;

import Controller.userController;
import Model.LoanModel;

import javax.swing.*;
import java.awt.*;

import static Form.TransferForm.createDetailLabel;

public class LoanDetailsForm {

    public LoanDetailsForm(Long ID){
        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Loan Details Confirmation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(450, 680); // Increased width to fit the design
            frame.setResizable(false);

            // Create a panel to hold all components
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(121, 211, 187)); // Light blue background color
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the panel

            // Add logo (resize it to fit the form)
            ImageIcon logoIcon = new ImageIcon("Logo.jpg");
            Image logoImage = logoIcon.getImage(); // Transform it
            Image scaledLogo = logoImage.getScaledInstance(100, 70, java.awt.Image.SCALE_SMOOTH); // Scale it
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo)); // Add resized logo
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(logoLabel);

            // Add "Loan Details" label
            JLabel loanDetailsLabel = new JLabel("Loan Details");
            loanDetailsLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for emphasis
            loanDetailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            loanDetailsLabel.setForeground(new Color(7, 58, 53));
            panel.add(loanDetailsLabel);

            // Create a separator for spacing
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            LoanModel loan = userController.getLoan(ID);

            // Add Loan Details fields
            panel.add(createDetailLabel("ID:", String.valueOf(loan.getLoanID())));
            addSeparator(panel);
            panel.add(createDetailLabel("Full Name:", loan.getName()));
            addSeparator(panel);
            panel.add(createDetailLabel("Address Line:", loan.getAddress()));
            addSeparator(panel);
            panel.add(createDetailLabel("Phone Number:", loan.getPhone()));
            addSeparator(panel);
            panel.add(createDetailLabel("Email:", loan.getEmail()));
            addSeparator(panel);
            panel.add(createDetailLabel("Date of Birth:", String.valueOf(loan.getDOB())));
            addSeparator(panel);
            panel.add(createDetailLabel("NRC:", loan.getNRC()));
            addSeparator(panel);
            panel.add(createDetailLabel("Marital Status:", loan.getMaritalStatus()));
            addSeparator(panel);
            panel.add(createDetailLabel("Job:", loan.getJob()));
            addSeparator(panel);
            panel.add(createDetailLabel("Annual Income:", loan.getIncome()));
            addSeparator(panel);
            panel.add(createDetailLabel("Assets:", loan.getAsset()));
            addSeparator(panel);
            panel.add(createDetailLabel("Collateral:", loan.getCollateral()));
            addSeparator(panel);
            panel.add(createDetailLabel("Loan Type:", loan.getLoanType()));
            addSeparator(panel);
            panel.add(createDetailLabel("Time frame to close:", loan.getTimeFrame()));
            addSeparator(panel);
            panel.add(createDetailLabel("Tenure years:", String.valueOf(loan.getTenureYears())));
            addSeparator(panel);
            panel.add(createDetailLabel("Interest Rate:", String.valueOf(loan.getInterestRate())));
            addSeparator(panel);
            panel.add(createDetailLabel("Amount:", String.valueOf(loan.getLoanAmount())));
            addSeparator(panel);
            panel.add(createDetailLabel("EMI:", String.valueOf(loan.getEMI())));
            addSeparator(panel);
            panel.add(createDetailLabel("Status:", loan.getStatus()));
            addSeparator(panel);

            // Add spacing before the button
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Add the "OK" button
            JButton btnApprove = new JButton("OK");
            btnApprove.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(btnApprove);
            btnApprove.addActionListener(_ ->{
               frame.dispose();
            });

            btnApprove.setBackground(new Color(24, 103, 204)); // Cornflower blue
            btnApprove.setForeground(Color.WHITE); // White text
            btnApprove.setFocusPainted(false);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Method to add an underline (separator)
    private static void addSeparator(JPanel panel) {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Extend it horizontally
        panel.add(separator);
    }
}
