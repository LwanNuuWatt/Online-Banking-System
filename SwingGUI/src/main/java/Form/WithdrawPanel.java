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

public class WithdrawPanel extends JFrame {

    private JTextField txtPhoneNo;
    private JTextField txtAmount;
    private JTextField txtWithdrawAmount;
    private JTextField password;
    private JTextField txtName;
    private final String adminPassword;
    private JPanel rightPanel;

    public WithdrawPanel(String PhoneNo) {
        adminPassword = "myAdmin";

        SwingUtilities.invokeLater(() ->{
            setTitle("Withdraw Application");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(650, 600);
            setLayout(null);
            setResizable(false);

            setLayout(null);
            setBackground(Color.WHITE);
            setBounds(0, 0, 650, 550);

            JPanel leftPanel = new JPanel();
            leftPanel.setBounds(0, 0, 250, 450);
            leftPanel.setBackground(new Color(30, 90, 83));
            leftPanel.setLayout(null);
            add(leftPanel);

            JLabel horizonLabel = new JLabel("Horizon", SwingConstants.CENTER);
            horizonLabel.setForeground(Color.WHITE);
            horizonLabel.setFont(new Font("Arial", Font.BOLD, 24));
            horizonLabel.setBounds(50, 140, 150, 30);
            leftPanel.add(horizonLabel);

            JLabel withdrawLabel = new JLabel("Withdraw", SwingConstants.CENTER);
            withdrawLabel.setForeground(Color.WHITE);
            withdrawLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            withdrawLabel.setBounds(50, 180, 150, 20);
            leftPanel.add(withdrawLabel);

            rightPanel = new JPanel();
            rightPanel.setBounds(250, 0, 400, 450);
            rightPanel.setLayout(null);
            add(rightPanel);

            JLabel withdrawTitleLabel = new JLabel("WITHDRAW");
            withdrawTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            withdrawTitleLabel.setBounds(130, 30, 150, 30);
            rightPanel.add(withdrawTitleLabel);

            JLabel phoneLabel = new JLabel("Phone Number:");
            phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            phoneLabel.setForeground(Color.DARK_GRAY);
            phoneLabel.setBounds(50, 80, 150, 25);
            rightPanel.add(phoneLabel);

            txtPhoneNo = new JTextField();
            txtPhoneNo.setFont(new Font("Arial", Font.PLAIN, 16));
            txtPhoneNo.setEditable(false);
            txtPhoneNo.setBounds(50, 110, 250, 25);
            rightPanel.add(txtPhoneNo);

            JLabel customerName = new JLabel("Customer Name:");
            customerName.setFont(new Font("Arial", Font.PLAIN, 16));
            customerName.setForeground(Color.DARK_GRAY);
            customerName.setBounds(50, 150, 150, 25);
            rightPanel.add(customerName);

            txtName = new JTextField();
            txtName.setFont(new Font("Arial", Font.PLAIN, 16));
            txtName.setEditable(false);
            txtName.setBounds(50, 180, 250, 25);
            rightPanel.add(txtName);

            JLabel availableLabel = new JLabel("Available Amount:");
            availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            availableLabel.setForeground(Color.DARK_GRAY);
            availableLabel.setBounds(50, 220, 150, 25);
            rightPanel.add(availableLabel);

            txtAmount = new JTextField();
            txtAmount.setFont(new Font("Arial", Font.PLAIN, 16));
            txtAmount.setEditable(false);
            txtAmount.setBounds(50, 250, 250, 25);
            rightPanel.add(txtAmount);

            JLabel withdrawAmountLabel = new JLabel("Withdraw Amount:");
            withdrawAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            withdrawAmountLabel.setForeground(Color.DARK_GRAY);
            withdrawAmountLabel.setBounds(50, 290, 150, 25);
            rightPanel.add(withdrawAmountLabel);

            txtWithdrawAmount = new JTextField();
            txtWithdrawAmount.setFont(new Font("Arial", Font.PLAIN, 16));
            txtWithdrawAmount.setBounds(50, 320, 250, 25);
            rightPanel.add(txtWithdrawAmount);

            JLabel passwordLabel = new JLabel("Password(Admin):");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            passwordLabel.setForeground(Color.DARK_GRAY);
            passwordLabel.setBounds(50, 360, 100, 25);
            rightPanel.add(passwordLabel);

            password = new JPasswordField();
            password.setFont(new Font("Arial", Font.PLAIN, 16));
            password.setBounds(50, 390, 250, 25);
            rightPanel.add(password);

            JButton btnWithdraw = new JButton("Withdraw");
            btnWithdraw.setBackground(new Color(30, 90, 83));
            btnWithdraw.setForeground(Color.WHITE);
            btnWithdraw.setFont(new Font("Arial", Font.BOLD, 16));
            btnWithdraw.setFocusPainted(false);
            btnWithdraw.setBounds(190, 420, 110, 30);
            rightPanel.add(btnWithdraw);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBackground(Color.GRAY);
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
            btnCancel.setFocusPainted(false);
            btnCancel.setBounds(50, 420, 130, 30);
            rightPanel.add(btnCancel);

            UserModel user = userController.getCustomer(PhoneNo);
            assert user != null;
            txtPhoneNo.setText(user.getPhoneNo());
            txtName.setText(user.getName());
            txtAmount.setText(String.valueOf(user.getAmount()));

            btnCancel.addActionListener(_ -> {
                new customerSide(PhoneNo);
                dispose();
            });

            ActionListener submit = (_ ->handleWithdrawal(PhoneNo));
            btnWithdraw.addActionListener(submit);
            txtWithdrawAmount.addActionListener(submit);
            password.addActionListener(submit);

            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void handleWithdrawal(String Phone_No) {
        String phoneNumber = txtPhoneNo.getText();
        String amount = txtAmount.getText();
        String withdrawAmount = txtWithdrawAmount.getText();
        String password = this.password.getText();
        UserModel user = userController.getCustomer(Phone_No);
        assert user != null;

        if (withdrawAmount.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(Objects.equals(user.getStatus(), "Restricted"))
        {
            JOptionPane.showMessageDialog(WithdrawPanel.this,
                    "Your account is restricted! You can't do this feature right now.",
                    "Restrict", JOptionPane.ERROR_MESSAGE);
            clearControls();
        }
        else {
            try {
                double availableBalance = Double.parseDouble(amount);
                double amountToWithdraw = Double.parseDouble(withdrawAmount);
                boolean result = false;

                if (amountToWithdraw > availableBalance) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance", "Error", JOptionPane.ERROR_MESSAGE);
                    txtWithdrawAmount.setText("");
                    txtWithdrawAmount.requestFocus();
                } else if (!password.equals(adminPassword)) {
                    JOptionPane.showMessageDialog(null, "Invalid Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    this.password.setText("");
                    this.password.requestFocus();
                } else {
                    BigDecimal amt = BigDecimal.valueOf(availableBalance - amountToWithdraw);
                    result = userController.updateAmount(phoneNumber,amt);
                    String message = result ? "Withdrawal successful!" : "Withdrawal failed!";
                    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
                    txtAmount.setText(String.valueOf(amt));
                    clearControls();
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
                    transaction.setTransaction_type("Withdraw");
                    transaction.setAmount(BigDecimal.valueOf(amountToWithdraw));
                    transaction.setTotal_amount(user.getAmount());

                    boolean res = userController.setTransaction(transaction);
                    String message = res ? "Transaction saved!" : "An error occurred when saving transaction!";
                    JOptionPane.showMessageDialog(WithdrawPanel.this, message,
                            "Message",JOptionPane.INFORMATION_MESSAGE);

                    new customerSide(Phone_No);
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number format",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearControls() {
        txtWithdrawAmount.setText("");
        password.setText("");
        txtWithdrawAmount.requestFocus();
    }
}
