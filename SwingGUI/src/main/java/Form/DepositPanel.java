package Form;

import Controller.userController;
import Model.TransactionModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class DepositPanel extends JFrame{

    private JTextField txtPhoneNo;
    private JTextField txtAmount;
    private JPasswordField Password;

    public DepositPanel(String PhoneNo) {

        String adminPassword = "myAdmin";
        final BigDecimal[][] userBalance = {{BigDecimal.valueOf(0.0)}};

        SwingUtilities.invokeLater(() ->{
            setTitle("Deposit");
            setLayout(null);
            setBackground(Color.WHITE);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(800, 450);
            setLayout(null);
            setResizable(false);

            // Left panel for the company information
            JPanel leftPanel = new JPanel();
            leftPanel.setBounds(0, 0, 300, 450); // Increased width
            leftPanel.setBackground(new Color(30, 90, 83)); // Horizon background color
            leftPanel.setLayout(null);
            add(leftPanel);

            // Horizon label centered
            JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
            horizonLabel.setForeground(Color.WHITE);
            horizonLabel.setFont(new Font("Arial", Font.BOLD, 24));
            horizonLabel.setBounds(75, 140, 150, 30); // Adjusted position
            leftPanel.add(horizonLabel);

            // Deposit label centered below Horizon
            JLabel depositLabel = new JLabel("Deposit", SwingConstants.CENTER);
            depositLabel.setForeground(Color.WHITE);
            depositLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            depositLabel.setBounds(75, 180, 150, 20); // Adjusted position
            leftPanel.add(depositLabel);

            // Right panel for the deposit form
            JPanel rightPanel = new JPanel();
            rightPanel.setBounds(300, 0, 600, 450); // Increased width
            rightPanel.setLayout(null);
            add(rightPanel);

            // Deposit title
            JLabel depositTitleLabel = new JLabel("DEPOSIT");
            depositTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            depositTitleLabel.setBounds(180, 30, 150, 30); // Adjusted position
            rightPanel.add(depositTitleLabel);

            // Phone Number label and text field
            JLabel phoneNumberLabel = new JLabel("Phone Number:");
            phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            phoneNumberLabel.setForeground(Color.DARK_GRAY);
            phoneNumberLabel.setBounds(50, 80, 150, 25);
            rightPanel.add(phoneNumberLabel);

            txtPhoneNo = new JTextField(PhoneNo);
            txtPhoneNo.setEditable(false);
            txtPhoneNo.setFont(new Font("Arial", Font.PLAIN, 16));
            txtPhoneNo.setBounds(50, 110, 250, 25);
            rightPanel.add(txtPhoneNo);

            // Deposit Amount label and text field
            JLabel depositAmountLabel = new JLabel("Deposit Amount:");
            depositAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            depositAmountLabel.setForeground(Color.DARK_GRAY);
            depositAmountLabel.setBounds(50, 150, 150, 25);
            rightPanel.add(depositAmountLabel);

            txtAmount = new JTextField();
            txtAmount.setFont(new Font("Arial", Font.PLAIN, 16));
            txtAmount.setBounds(50, 180, 250, 25);
            rightPanel.add(txtAmount);

            // Password label and password field
            JLabel passwordLabel = new JLabel("Password(Admin):");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            passwordLabel.setForeground(Color.DARK_GRAY);
            passwordLabel.setBounds(50, 220, 150, 25);
            rightPanel.add(passwordLabel);

            Password = new JPasswordField();
            Password.setFont(new Font("Arial", Font.PLAIN, 16));
            Password.setBounds(50, 250, 250, 25);
            rightPanel.add(Password);

            // Deposit button
            JButton btnDeposit = new JButton("Deposit");
            btnDeposit.setBackground(new Color(30, 90, 83)); // Match Horizon background color
            btnDeposit.setForeground(Color.WHITE);
            btnDeposit.setFont(new Font("Arial", Font.BOLD, 16));
            btnDeposit.setFocusPainted(false);
            btnDeposit.setBounds(200, 320, 100, 30); // Adjusted position
            rightPanel.add(btnDeposit);

            // Cancel button
            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBackground(Color.GRAY);
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
            btnCancel.setFocusPainted(false);
            btnCancel.setBounds(50, 320, 100, 30);
            rightPanel.add(btnCancel);

            btnCancel.addActionListener(_ -> {
                new customerSide(PhoneNo);
                dispose();
            });

            ActionListener submit = (_ ->{
                boolean result = false;
                String amt = txtAmount.getText();
                BigDecimal amount = new BigDecimal(amt);
                UserModel user = userController.getCustomer(PhoneNo);
                assert user != null;
                userBalance[0] = new BigDecimal[]{user.getAmount()};

                if (txtAmount.getText().isEmpty() || String.valueOf(Password.getPassword()).isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "All fields must be filled",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }else if(Objects.equals(user.getStatus(), "Restricted"))
                {
                    JOptionPane.showMessageDialog(DepositPanel.this,
                            "Your account is restricted! You can't do this feature right now.",
                            "Restrict", JOptionPane.ERROR_MESSAGE);
                    ClearControls();
                }
                try{
                    if(!Objects.equals(String.valueOf(Password.getPassword()), adminPassword))
                    {
                        JOptionPane.showMessageDialog(null,"Wrong password!","Error",
                                JOptionPane.ERROR_MESSAGE);
                        Password.setText("");
                        Password.requestFocus();
                    }
                    else if(Objects.equals(String.valueOf(Password.getPassword()), adminPassword))
                    {
                        userBalance[0][0] = userBalance[0][0].add(amount);
                        result = userController.updateAmount(txtPhoneNo.getText(), userBalance[0][0]);
                        String message = result ? "Deposited" : "Something went wrong!";
                        JOptionPane.showMessageDialog(null,message,
                                "Message",JOptionPane.INFORMATION_MESSAGE);
                        ClearControls();
                    }
                    if(result)
                    {
                        long transactionID = ThreadLocalRandom.current().nextLong(1000000L, 999999999L);
                        Date currentDate = new Date(System.currentTimeMillis());
                        TransactionModel transaction = new TransactionModel();

                        transaction.setTransaction_ID(transactionID);
                        transaction.setUser_ID(user.getId());
                        transaction.setPhone_No(user.getPhoneNo());
                        transaction.setTransaction_date(currentDate);
                        transaction.setTransaction_type("Deposit");
                        transaction.setAmount(amount);
                        transaction.setTotal_amount(userBalance[0][0]);

                        boolean res = userController.setTransaction(transaction);
                        String message = res ? "Transaction saved!" : "An error occurred when saving transaction!";
                        JOptionPane.showMessageDialog(DepositPanel.this, message,
                                "Message",JOptionPane.INFORMATION_MESSAGE);
                        new customerSide(txtPhoneNo.getText());
                        dispose();
                    }
                }catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Invalid number format",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnDeposit.addActionListener(submit);
            txtAmount.addActionListener(submit);
            Password.addActionListener(submit);

            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void ClearControls()
    {
        txtAmount.setText("");
        Password.setText("");
        txtAmount.requestFocus();
    }
}
