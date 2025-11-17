package Form;

import Controller.userController;
import Model.UserModel;
import Model.TransactionModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static CheckPassword.passwordCheck.verifyPassword;

public class TransferPanel extends JFrame {

    private boolean flag;
    private String phoneNo;
    UserModel receiveUser;

    public TransferPanel(String PhoneNo) {

        SwingUtilities.invokeLater(() ->{
            setTitle("Transfer");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(700, 450);
            setResizable(false);
            setLocationRelativeTo(null);
            setLayout(null);
            setBackground(Color.WHITE);

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

            JLabel transferLabel = new JLabel("Transfer", SwingConstants.CENTER);
            transferLabel.setForeground(Color.WHITE);
            transferLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            transferLabel.setBounds(50, 180, 150, 20);
            leftPanel.add(transferLabel);

            JPanel rightPanel = new JPanel();
            rightPanel.setBounds(250, 0, 420, 450);
            rightPanel.setLayout(null);
            add(rightPanel);

            JLabel transferTitleLabel = new JLabel("TRANSFER");
            transferTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            transferTitleLabel.setBounds(130, 30, 150, 30);
            rightPanel.add(transferTitleLabel);

            JLabel phoneNumberLabel = new JLabel("Phone Number:");
            phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            phoneNumberLabel.setForeground(Color.DARK_GRAY);
            phoneNumberLabel.setBounds(50, 80, 150, 25);
            rightPanel.add(phoneNumberLabel);

            JTextField txtPhoneNo = new JTextField();
            txtPhoneNo.setFont(new Font("Arial", Font.PLAIN, 16));
            txtPhoneNo.setBounds(50, 110, 250, 25);
            rightPanel.add(txtPhoneNo);

            JButton btnSearch = new JButton("Search");
            btnSearch.setBackground(new Color(30, 90, 83));
            btnSearch.setForeground(Color.WHITE);
            btnSearch.setFont(new Font("Arial", Font.BOLD, 16));
            btnSearch.setFocusPainted(false);
            btnSearch.setBounds(320, 110, 100, 25);
            rightPanel.add(btnSearch);
            btnSearch.addActionListener(_ -> {
                phoneNo = txtPhoneNo.getText();
                receiveUser = userController.getCustomer(phoneNo);
                if(userController.CheckUser(phoneNo))
                {
                    assert receiveUser != null;
                    JOptionPane.showMessageDialog(null, receiveUser.getName());
                    flag = true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "No User Found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    txtPhoneNo.setText("");
                    txtPhoneNo.requestFocus();
                }
            });

            JLabel availableAmountLabel = new JLabel("Available Amount:");
            availableAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            availableAmountLabel.setForeground(Color.DARK_GRAY);
            availableAmountLabel.setBounds(50, 150, 150, 25);
            rightPanel.add(availableAmountLabel);

            JTextField txtAmount = new JTextField();
            txtAmount.setFont(new Font("Arial", Font.PLAIN, 16));
            txtAmount.setEditable(false);
            txtAmount.setBounds(50, 180, 250, 25);
            rightPanel.add(txtAmount);
            UserModel transferUser = userController.getCustomer(PhoneNo);
            assert transferUser != null;
            txtAmount.setText(String.valueOf(transferUser.getAmount()));

            JLabel transferAmountLabel = new JLabel("Transfer Amount:");
            transferAmountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            transferAmountLabel.setForeground(Color.DARK_GRAY);
            transferAmountLabel.setBounds(50, 220, 150, 25);
            rightPanel.add(transferAmountLabel);

            JTextField txtTransferAmount = new JTextField();
            txtTransferAmount.setFont(new Font("Arial", Font.PLAIN, 16));
            txtTransferAmount.setBounds(50, 250, 250, 25);
            rightPanel.add(txtTransferAmount);

            JLabel passwordLabel = new JLabel("Password(User):");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            passwordLabel.setForeground(Color.DARK_GRAY);
            passwordLabel.setBounds(50, 290, 150, 25);
            rightPanel.add(passwordLabel);

            JPasswordField Password = new JPasswordField();
            Password.setFont(new Font("Arial", Font.PLAIN, 16));
            Password.setBounds(50, 320, 250, 25);
            rightPanel.add(Password);

            JButton btnTransfer = new JButton("Transfer");
            btnTransfer.setBackground(new Color(30, 90, 83)); // Matching green background color
            btnTransfer.setForeground(Color.WHITE);
            btnTransfer.setFont(new Font("Arial", Font.BOLD, 16));
            btnTransfer.setFocusPainted(false);
            btnTransfer.setBounds(200, 370, 100, 30); // Positioned and sized appropriately
            rightPanel.add(btnTransfer);
            ActionListener submit = (_ ->{
                String availableBalance = txtAmount.getText();
                String transferAmount = txtTransferAmount.getText();
                double available = Double.parseDouble(availableBalance);
                double amountToTransfer = Double.parseDouble(transferAmount);
                double transferFee = amountToTransfer * 0.01;
                receiveUser.setAmount(receiveUser.getAmount().add(BigDecimal.valueOf(amountToTransfer)));
                boolean result = false;
                byte[] salt = Base64.getDecoder().decode(transferUser.getSalt());
                if(txtPhoneNo.getText().isEmpty() || txtTransferAmount.getText().isEmpty() ||
                        String.valueOf(Password.getPassword()).isEmpty() || !flag)
                {
                    JOptionPane.showMessageDialog(null, "Please fills required fields!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    txtPhoneNo.requestFocus();
                }
                else if(Objects.equals(transferUser.getStatus(), "Restricted"))
                {
                    JOptionPane.showMessageDialog(TransferPanel.this,
                            "Your account is restricted! You can't do this feature right now.",
                            "Restrict", JOptionPane.ERROR_MESSAGE);
                    txtPhoneNo.setText("");
                    txtTransferAmount.setText("");
                    Password.setText("");
                    txtPhoneNo.requestFocus();
                }
                else {
                    try{
                        double remainingAmount = available - amountToTransfer - transferFee;
                        boolean isValid = verifyPassword(String.valueOf(Password.getPassword()),
                                transferUser.getPassword(), salt);
                        if (amountToTransfer > available) {
                            JOptionPane.showMessageDialog(null, "Insufficient balance",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else if(!isValid)
                        {
                            JOptionPane.showMessageDialog(null, "Invalid Password!",
                                    "Wrong password", JOptionPane.ERROR_MESSAGE);
                            Password.setText("");
                            Password.requestFocus();
                        }
                        else {
                            result = userController.updateAmount(phoneNo,
                                    receiveUser.getAmount()) && userController
                                    .updateAmount(PhoneNo, BigDecimal.valueOf(remainingAmount));
                            String message = result ? "Transfer successful!" : "Transfer failed!";
                            JOptionPane.showMessageDialog(null, message,
                                    "Message", JOptionPane.INFORMATION_MESSAGE);
                            txtAmount.setText(String.valueOf(remainingAmount));

                            txtTransferAmount.setText("");
                            Password.setText("");
                        }

                        if(result)
                        {
                            long transactionID = ThreadLocalRandom.current().nextLong(1000000L, 999999999L);
                            Date currentDate = new Date(System.currentTimeMillis());
                            TransactionModel transaction = new TransactionModel();

                            transaction.setTransaction_ID(transactionID);
                            transaction.setUser_ID(transferUser.getId());
                            transaction.setPhone_No(transferUser.getPhoneNo());
                            transaction.setTransaction_date(currentDate);
                            transaction.setTransaction_type("Transfer");
                            transaction.setAmount(BigDecimal.valueOf(amountToTransfer));
                            transaction.setTotal_amount(transferUser.getAmount());
                            transaction.setTransfer_fee(transferFee);

                            boolean res = userController.transferTransaction(transaction, receiveUser);
                            String message = res ? "Transaction saved!" : "An error occurred when saving transaction!";
                            JOptionPane.showMessageDialog(TransferPanel.this, message,
                                    "Message",JOptionPane.INFORMATION_MESSAGE);
                            new customerSide(PhoneNo);
                            dispose();
                        }
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid numbers for balance and transfer amount",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            btnTransfer.addActionListener(submit);
            txtPhoneNo.addActionListener(submit);
            txtTransferAmount.addActionListener(submit);
            Password.addActionListener(submit);

            // Cancel button
            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBackground(Color.GRAY);
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
            btnCancel.setFocusPainted(false);
            btnCancel.setBounds(50, 370, 100, 30);
            rightPanel.add(btnCancel);
            btnCancel.addActionListener(_ -> {
                new customerSide(PhoneNo);
                dispose();
            });

            setVisible(true);
        });
    }
}