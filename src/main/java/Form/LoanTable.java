package Form;
import Controller.userController;
import Model.LoanModel;
import Button.btnRenderer;
import Button.btnEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class LoanTable extends JFrame {
    private static DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public LoanTable() {
        SwingUtilities.invokeLater(() ->{
            setTitle("Loan Table");
            setSize(1200, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true); // Allow window resizing

            // Define table columns (now only with "Details" button)
            String[] columns = {"No", "ID", "Full Name", "Phone Number", "Email", "NRC", "Job",
                    "Date of birth", "Address", " ", " "};

            // Table model
            tableModel = new DefaultTableModel(columns, 0);
            JTable table = new JTable(tableModel);

            // Create TableRowSorter for filtering
            rowSorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(rowSorter);

            // Set the table to resize dynamically to fit window size
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            // Add button renderer and editor for the "Details" column
            table.getColumnModel().getColumn(9).setCellRenderer(new btnRenderer());
            table.getColumnModel().getColumn(9).setCellEditor(new btnEditor(new JCheckBox(), table));

            table.getColumnModel().getColumn(10).setCellRenderer(new btnRenderer());
            table.getColumnModel().getColumn(10).setCellEditor(new btnEditor(new JCheckBox(), table));

            // Scroll Pane for Table (no need for horizontal scrolling)
            JScrollPane scrollPane = new JScrollPane(table);

            // Create a panel for the search bar
            JPanel searchPanel = new JPanel(new BorderLayout());
            JTextField searchField = new JTextField(20);
            searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
            searchPanel.add(searchField, BorderLayout.CENTER);

            // Add listener for search field to filter table
            searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    filterTable();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    filterTable();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    filterTable();
                }

                private void filterTable() {
                    String text = searchField.getText().trim();
                    if (text.isEmpty()) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }
            });

            // Back button (placed on the left side)
            JButton backButton = new JButton("Back");
            backButton.addActionListener(_ -> {
                new MainFrame();
                dispose();
            });

            // Layout and adding components
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(searchPanel, BorderLayout.NORTH); // Add search panel at the top
            panel.add(scrollPane, BorderLayout.CENTER);

            // Place back button on the left side
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(backButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            // Adding panel to frame
            add(panel);

            // Populate sample data (replace with dynamic database data later)
            populateTable();

            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    // Method to populate sample data (replace this with your dynamic data fetching logic)
    private void populateTable() {
        List<LoanModel> loanLists = userController.getLoans();
        int No = 0;
        for (LoanModel loan : loanLists) {
            tableModel.addRow(new Object[]{
                    ++No,
                    loan.getLoanID(),
                    loan.getName(),
                    loan.getPhone(),
                    loan.getEmail(),
                    loan.getNRC(),
                    loan.getJob(),
                    loan.getDOB(),
                    loan.getAddress(),
                    "Details",
                    "Delete"
            });
        }
    }

    public static void refreshData()
    {
        List<LoanModel> loanLists = userController.getLoans();
        tableModel.setRowCount(0);
        int No = 0;
        for (LoanModel loan : loanLists) {
            tableModel.addRow(new Object[]{
                    ++No,
                    loan.getLoanID(),
                    loan.getName(),
                    loan.getPhone(),
                    loan.getEmail(),
                    loan.getNRC(),
                    loan.getJob(),
                    loan.getDOB(),
                    loan.getAddress(),
                    "Details",
                    "Delete"
            });
        }
    }
}


// Create ButtonRenderer to display buttons in the "Details" column


// Create ButtonEditor to handle the button's functionality
