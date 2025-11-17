package Form;
import Controller.userController;
import Model.TransactionModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;

import static Form.TransferForm.createDetailLabel;

public class DepositForm extends JFrame {

    public DepositForm(String PhoneNo, Long ID) {
        SwingUtilities.invokeLater(() ->{
            setTitle("Payment Confirmation");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 600);
            setResizable(false);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(8, 90, 81));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the panel

            ImageIcon logoIcon = new ImageIcon("Logo.jpg");
            Image logoImage = logoIcon.getImage();
            Image scaledLogo = logoImage.getScaledInstance(100, 70, Image.SCALE_SMOOTH); // Scale it
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(logoLabel);

            JLabel paymentCompletedLabel = new JLabel("Payment completed");
            paymentCompletedLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for emphasis
            paymentCompletedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            paymentCompletedLabel.setForeground(new Color(223, 225, 229));
            panel.add(paymentCompletedLabel);

            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            TransactionModel model = userController.getTransaction(PhoneNo, ID);
            UserModel user = userController.getCustomer(PhoneNo);
            userController.updateFixedDeposit(model.getTransaction_ID());

            assert user != null;
            panel.add(createDetailLabel("Customer Name:", user.getName()));
            addSeparator(panel);
            panel.add(createDetailLabel("TransactionID:", String.valueOf(model.getTransaction_ID())));
            addSeparator(panel);
            panel.add(createDetailLabel("Phone No:", model.getPhone_No()));
            addSeparator(panel);
            panel.add(createDetailLabel("UserID:", String.valueOf(model.getUser_ID())));
            addSeparator(panel);
            panel.add(createDetailLabel("TransactionType:", model.getTransaction_type()));
            addSeparator(panel);
            panel.add(createDetailLabel("Date:", String.valueOf(model.getTransaction_date())));
            addSeparator(panel);
            panel.add(createDetailLabel("Amount:", model.getAmount() +" MMK"));
            addSeparator(panel);

            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            JButton btnOK = new JButton("OK");
            btnOK.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(btnOK);

            btnOK.setBackground(new Color(127, 135, 136));
            btnOK.setForeground(Color.WHITE);
            btnOK.setFocusPainted(false);
            btnOK.addActionListener(_ -> dispose());

            add(panel);
            setVisible(true);
        });
    }

    private static void addSeparator(JPanel panel) {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // Extend it horizontally
        panel.add(separator);
    }
}
