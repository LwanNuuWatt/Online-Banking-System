package Form;

import Controller.userController;
import Model.TransactionModel;
import Button.Renderer;
import Button.ButtonEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Transaction extends JFrame {
    public Transaction(String PhoneNo) {
        SwingUtilities.invokeLater(() -> {
            setTitle("User Data Display");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 400);
            setLocationRelativeTo(null);

            DefaultTableModel model = getDefaultTableModel();

            JTable table = new JTable(model);

            // Set custom cell renderer and editor for the "Type" column
            table.getColumnModel().getColumn(3).setCellRenderer(new Renderer());
            table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(table, PhoneNo));

            // Prevent focus from being gained by any cell
            table.setFocusable(false);

            JScrollPane scrollPane = new JScrollPane(table);

            List<TransactionModel> transactionLists = userController.transactionLists(PhoneNo);

            for (TransactionModel transaction : transactionLists) {
                fetchDataAndPopulateTable(model, transaction);
            }

            JPanel buttonPanel = getjPanel(PhoneNo);

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            setVisible(true);
        });
    }

    private static DefaultTableModel getDefaultTableModel() {
        String[] columnNames = {"TransactionID", "UserID", "Phone No", "Type", "Date", "Amount"};
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
    }

    private JPanel getjPanel(String Phone_no) {
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to user details");

        backButton.addActionListener(_ -> {
            new Details(Phone_no);
            dispose();
        });

        buttonPanel.add(backButton);
        return buttonPanel;
    }

    // Method to fetch data and populate the table (replace this logic with dynamic fetching later)
    public static void fetchDataAndPopulateTable(DefaultTableModel model, TransactionModel transactionModel) {
        model.addRow(new Object[]{
                transactionModel.getTransaction_ID(),
                transactionModel.getUser_ID(),
                transactionModel.getPhone_No(),
                transactionModel.getTransaction_type(), // Clickable button
                transactionModel.getTransaction_date(),
                transactionModel.getAmount()
        });
    }
}
