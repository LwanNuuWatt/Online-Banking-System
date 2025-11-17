package Form;
import Controller.userController;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static Form.adminSide.getJButton;

public class customerSide extends JFrame {

    public customerSide(String PhoneNo) {
        SwingUtilities.invokeLater(() ->{
            setTitle("Bank Management System");
            setSize(650, 450);
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

            JLabel withdrawLabel = new JLabel("Customer", SwingConstants.CENTER);
            withdrawLabel.setForeground(Color.WHITE);
            withdrawLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            withdrawLabel.setBounds(50, 180, 150, 20);
            leftPanel.add(withdrawLabel);

            JPanel rightPanel = new JPanel();
            rightPanel.setBounds(250, 0, 400, 450);
            rightPanel.setLayout(null);
            add(rightPanel);

            UserModel user = userController.getCustomer(PhoneNo);
            assert user != null;
            int year = userController.getYears(PhoneNo);
            int tenureYear = userController.getTenureYears(PhoneNo);
            int calculateYear = tenureYear - year;

            JButton btnWithdraw = createButton("Withdraw Balance", new Color(30, 90, 83), 50);
            rightPanel.add(btnWithdraw);
            btnWithdraw.addActionListener(_ -> {
                if(Objects.equals(user.getStatus(), "Fixed") && calculateYear > 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You can't make this feature " +
                                    "until the tenure years is completed.");
                }else {
                    new WithdrawPanel(PhoneNo);
                    dispose();
                }
            });


            JButton btnDeposit = createButton("Deposit Balance", new Color(30, 90, 83), 120);
            rightPanel.add(btnDeposit);

            btnDeposit.addActionListener(_ -> {
                if(Objects.equals(user.getStatus(), "Fixed") && calculateYear >= 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You can't make this feature " +
                                    "until the tenure years is completed.");
                }
                else{
                    new DepositPanel(PhoneNo);
                    dispose();
                }
            });

            JButton btnTransfer = createButton("Transfer Balance", new Color(30, 90, 83), 190);
            rightPanel.add(btnTransfer);
            btnTransfer.addActionListener(_ ->{
                if(Objects.equals(user.getStatus(), "Fixed") && calculateYear >= 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You are already deposited with fixed account. You can't make this feature " +
                                    "until the tenure years is completed.");
                }
                else {
                    new TransferPanel(PhoneNo);
                    dispose();
                }
            });

            JButton btnShowDetails = createButton("Show Account and Balance Details",
                    new Color(30, 90, 83), 260);
            rightPanel.add(btnShowDetails);
            btnShowDetails.addActionListener(_ -> {
                new Details(PhoneNo);
                dispose();
            });

            JButton btnBack = createButton("Log Out", new Color(30, 90, 83), 330);
            rightPanel.add(btnBack);
            btnBack.addActionListener(_ -> {
                new HomePage();
                dispose();
            });

            setVisible(true);
        });
    }

    private JButton createButton(String text, Color backgroundColor, int y) {
        return getJButton(text, backgroundColor, y);
    }
}