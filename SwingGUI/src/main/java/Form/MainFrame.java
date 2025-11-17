package Form;

import UserLists.UserLst;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public MainFrame() {
        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Home Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Create a navigation bar panel
            JPanel navBar = new JPanel(new BorderLayout());
            navBar.setBackground(new Color(0x509A92));
            navBar.setPreferredSize(new Dimension(600, 130)); // Height for the navbar

            // Horizon logo image for the navigation bar
            JLabel logoLabel = new JLabel();
            ImageIcon logoIcon = new ImageIcon("Logo.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Adjust logo size
            logoLabel.setIcon(new ImageIcon(scaledLogo));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            navBar.add(logoLabel, BorderLayout.CENTER);

            // Add the navigation bar to the top
            frame.add(navBar, BorderLayout.NORTH);

            // Create a panel to hold the saving and loan sides
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new GridLayout(1, 2, 2, 0)); // Two columns, 2px gap for the dividing line

            // Saving side panel (formerly Admin)
            JPanel savingPanel = new JPanel();
            savingPanel.setBackground(new Color(0xDCECEC)); // Set new custom color for saving background
            savingPanel.setLayout(new BoxLayout(savingPanel, BoxLayout.Y_AXIS)); // Arrange components vertically

            // Add glue to center content vertically
            savingPanel.add(Box.createVerticalGlue());

            // Load and scale saving image
            JLabel savingImage = new JLabel();
            ImageIcon savingIcon = new ImageIcon("saving.png");
            Image scaledSavingImage = savingIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Scale to 250x250
            savingImage.setIcon(new ImageIcon(scaledSavingImage));
            savingImage.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the image horizontally
            savingPanel.add(savingImage);

            // Add saving button
            JButton btnCustomer = new JButton("Customer lists");
            btnCustomer.setFocusable(false); // Disable focus for the saving button
            btnCustomer.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
            btnCustomer.setPreferredSize(new Dimension(300, 70)); // Larger button size
            btnCustomer.setFont(new Font("Arial", Font.BOLD, 20)); // Custom font style and size
            savingPanel.add(btnCustomer);
            btnCustomer.addActionListener(_ ->{
                new UserLst();
                frame.dispose();
            });

            // Add glue to center content vertically
            savingPanel.add(Box.createVerticalGlue());

            // Loan side panel (formerly Customer)
            JPanel loanPanel = new JPanel();
            loanPanel.setBackground(new Color(0xDCECEC)); // Set new custom color for loan background
            loanPanel.setLayout(new BoxLayout(loanPanel, BoxLayout.Y_AXIS)); // Arrange components vertically

            // Add glue to center content vertically
            loanPanel.add(Box.createVerticalGlue());

            // Load and scale loan image
            JLabel loanImage = new JLabel();
            ImageIcon loanIcon = new ImageIcon("loan.png");
            Image scaledLoanImage = loanIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Scale to 250x250
            loanImage.setIcon(new ImageIcon(scaledLoanImage));
            loanImage.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the image horizontally
            loanPanel.add(loanImage);

            // Add loan button
            JButton btnLoan = new JButton("Loan Customer Lists");
            btnLoan.setFocusable(false); // Disable focus for the loan button
            btnLoan.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
            btnLoan.setPreferredSize(new Dimension(300, 70)); // Larger button size
            btnLoan.setFont(new Font("Arial", Font.BOLD, 20)); // Custom font style and size
            loanPanel.add(btnLoan);
            btnLoan.addActionListener(_ ->{
                new LoanTable();
                frame.dispose();
            });

            // Add glue to center content vertically
            loanPanel.add(Box.createVerticalGlue());

            // Add saving and loan panels to content panel
            contentPanel.add(savingPanel);
            contentPanel.add(loanPanel);

            // Add the content panel to the center of the frame
            frame.add(contentPanel, BorderLayout.CENTER);

            // Create a back button and add it to the bottom right
            JButton backButton = new JButton("Back");
            backButton.addActionListener(_ -> {
                new adminSide();
                frame.dispose();
            }); // Action to close the application
            JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            backButton.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font style and size// Align button to the right
            backButton.setFocusable(false);
            backPanel.add(backButton);

            btnCustomer.setPreferredSize(new Dimension(200, 50));
            btnLoan.setPreferredSize(new Dimension(200, 50));
            backButton.setPreferredSize(new Dimension(120, 40));

            btnCustomer.setBackground(new Color(0x4FEDDB));
            btnLoan.setBackground(new Color(0x54E8D6));
            backButton.setBackground(new Color(0xFF4C4C));

            // Add the exit panel to the bottom
            frame.add(backPanel, BorderLayout.SOUTH);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}

