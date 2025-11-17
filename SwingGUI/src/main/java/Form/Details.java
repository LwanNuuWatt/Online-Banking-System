package Form;

import Controller.userController;
import Model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Details extends JFrame {

    private DefaultTableModel model;
    public Details(String PhoneNo) {
        SwingUtilities.invokeLater(() -> {
            setTitle("User Account Details");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);

            getContentPane().setBackground(new Color(252, 253, 253));
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            JLabel titleLabel = new JLabel("Personal Details");
            titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
            titleLabel.setForeground(new Color(223, 225, 229));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(titleLabel, BorderLayout.NORTH);

            UserModel user = userController.getCustomer(PhoneNo);
            assert user != null;

            JPanel buttonPanel = getjPanel(PhoneNo);
            add(buttonPanel, BorderLayout.SOUTH);

            String[] columnNames = {" Field ", "User Details"};
            String[][] data = {
                    {"ID", ""},
                    {"Name", ""},
                    {"Phone Number", ""},
                    {"Address", ""},
                    {"Account Type", ""},
                    {"Age", ""},
                    {"Balance", ""},
                    {"Registration Date", ""}
            };

            model = new DefaultTableModel(data, columnNames);
            JScrollPane scrollPane = getjScrollPane();
            add(scrollPane, BorderLayout.CENTER);

            populateFields(user);

            setVisible(true);
        });
    }

    private JPanel getjPanel(String PhoneNo) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("Back to customer page");
        btnBack.setFont(new Font("Roboto", Font.BOLD, 18));
        btnBack.setForeground(Color.BLACK);
        btnBack.setBackground(new Color(94, 97, 97, 255));
        btnBack.setHorizontalAlignment(SwingConstants.CENTER);
        btnBack.addActionListener(_ ->{
            new customerSide(PhoneNo);
            dispose();
        });
        buttonPanel.add(btnBack, BorderLayout.WEST);

        JButton btnTransaction = new JButton("Go to transaction page");
        btnTransaction.setFont(new Font("Roboto", Font.BOLD, 18));
        btnTransaction.setForeground(Color.WHITE);
        btnTransaction.setBackground(new Color(204, 51, 0));
        btnTransaction.setHorizontalAlignment(SwingConstants.CENTER);
        btnTransaction.addActionListener(_ ->{
            new Transaction(PhoneNo);
            dispose();
        });
        buttonPanel.add(btnTransaction, BorderLayout.EAST);
        return buttonPanel;
    }

    private JScrollPane getjScrollPane() {
        JTable table = new JTable(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(30, 90, 83));
        table.setForeground(new Color(12, 7, 10));
        table.setBackground(new Color(252, 253, 253));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Roboto", Font.BOLD, 16));
        header.setForeground(new Color(255, 255, 255));
        header.setBackground(new Color(30, 90, 83));
        return new JScrollPane(table);
    }

    public void populateFields(UserModel user) {
        model.setValueAt(user.getId(), 0, 1);
        model.setValueAt(user.getName(), 1, 1);
        model.setValueAt(user.getPhoneNo(), 2, 1);
        model.setValueAt(user.getAddress(), 3, 1);
        model.setValueAt(user.getStatus(), 4, 1);
        model.setValueAt(user.getAge(), 5, 1);
        model.setValueAt(user.getAmount(), 6, 1);
        model.setValueAt(user.getDate(), 7, 1);
    }
}