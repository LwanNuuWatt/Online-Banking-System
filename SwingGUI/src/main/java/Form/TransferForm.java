package Form;
import Controller.userController;
import Model.TransactionModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;

public class TransferForm extends JFrame {

    public TransferForm(String PhoneNo, Long ID) {
        SwingUtilities.invokeLater(() ->{
            setTitle("Payment Confirmation");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 600);
            setResizable(false);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(8, 90, 81)); // Light blue background color
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the panel

            // Add logo (resize it to fit the form)
            ImageIcon logoIcon = new ImageIcon("Logo.jpg");
            Image logoImage = logoIcon.getImage(); // Transform it
            Image scaledLogo = logoImage.getScaledInstance(100, 70, java.awt.Image.SCALE_SMOOTH); // Scale it
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo)); // Add resized logo
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(logoLabel);

            // Add "Payment completed" label
            JLabel paymentCompletedLabel = new JLabel("Payment completed");
            paymentCompletedLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for emphasis
            paymentCompletedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            paymentCompletedLabel.setForeground(new Color(223, 225, 229));
            panel.add(paymentCompletedLabel);

            // Create a separator for spacing
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Add "From" and "To" fields

            TransactionModel model = userController.getTransaction(PhoneNo, ID);
            UserModel receiver = userController.getTransferTransaction(PhoneNo, model.getTransaction_ID());
            UserModel sender = userController.getCustomer(PhoneNo);

            assert sender != null;
            panel.add(createDetailLabel("From:", sender.getName() + " ID:" + sender.getId()));
            addSeparator(panel);
            panel.add(createDetailLabel("To:", receiver.getName() + " ID:" + receiver.getId()));
            addSeparator(panel);

            // Add dynamic payment details
            panel.add(createDetailLabel("ID:", String.valueOf(model.getTransaction_ID())));
            addSeparator(panel);
            panel.add(createDetailLabel("Sender Phone No:", sender.getPhoneNo()));
            addSeparator(panel);
            panel.add(createDetailLabel("Receiver Phone No:", receiver.getPhoneNo()));
            addSeparator(panel);
            panel.add(createDetailLabel("Transaction Type:", model.getTransaction_type()));
            addSeparator(panel);
            panel.add(createDetailLabel("Date:", String.valueOf(model.getTransaction_date())));
            addSeparator(panel);
            panel.add(createDetailLabel("Amount:", model.getAmount() + " MMK"));
            addSeparator(panel);

            // Add spacing before the button
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Add the "OK" button
            JButton btnOK = new JButton("OK");
            btnOK.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(btnOK);

            // Set a background color for the button
            btnOK.setBackground(new Color(127, 135, 136)); // Cornflower blue
            btnOK.setForeground(Color.WHITE); // White text
            btnOK.setFocusPainted(false);
            btnOK.addActionListener(_ -> dispose());

            // Add everything to the frame
            add(panel);
            setVisible(true);
        });
    }

    // Method to create labels for the payment details
    static JPanel createDetailLabel(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(159, 211, 204)); // Light blue background to match the overall panel

        // Set padding inside the panel for spacing between label/value and the border
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding around the labels

        // Label component
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelComponent, BorderLayout.WEST);

        // Value component
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));
        valueComponent.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Padding between the label and value
        panel.add(valueComponent, BorderLayout.EAST);

        return panel;
    }
    // Method to add an underline (separator)
    private static void addSeparator(JPanel panel) {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Extend it horizontally
        panel.add(separator);
    }
}
