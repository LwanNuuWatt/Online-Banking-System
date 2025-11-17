package UserLists;

import Controller.userController;
import Form.MainFrame;
import Model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.Objects;

// Import custom button classes
import Button.Renderer;
import Button.Editor;
import Button.PickColor;

public class UserLst {

    private static DefaultTableModel tableModel;
    private static Controller.userController userController;
    private JTable table;
    private static int No = 0;

    public UserLst() {
        SwingUtilities.invokeLater(() -> {
            userController = new userController();
            List<UserModel> userModelList = userController.userModelList();
            quickSort(userModelList, 0, userModelList.size() - 1);

            // Custom table model to control which cells are editable
            tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make columns 9 ("Restrict/UnRestrict"), 10 ("Edit"), and 11 ("Delete") clickable
                    return column == 9 || column == 10 || column == 11;
                }
            };

            // Define column headers
            tableModel.addColumn("No.");
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Age");
            tableModel.addColumn("Address");
            tableModel.addColumn("Phone number");
            tableModel.addColumn("Amount");
            tableModel.addColumn("AccountType");
            tableModel.addColumn("Reg Date");
            tableModel.addColumn("");
            tableModel.addColumn(" ");
            tableModel.addColumn("  ");

            No = 0;
            Object[] row = new Object[12];
            for (UserModel userModel : userModelList) {
                row[0] = ++No;
                row[1] = userModel.getId();
                row[2] = userModel.getName();
                row[3] = userModel.getAge();
                row[4] = userModel.getAddress();
                row[5] = userModel.getPhoneNo();
                row[6] = userModel.getAmount();
                row[7] = userModel.getStatus();
                row[8] = userModel.getDate();
                if (Objects.equals(row[7], "Restricted")) {
                    row[9] = "UnRestrict";
                } else {
                    row[9] = "Restrict";
                }
                row[10] = "Edit";
                row[11] = "Delete";
                tableModel.addRow(row);
            }

            table = new JTable(tableModel);
            JFrame frame = new JFrame("User Data Display");
            table.getTableHeader().setReorderingAllowed(false);

            Color[] redColor = {new Color(232, 11, 18)};
            Color[] blueColor = {new Color(48, 52, 222)};
            Color[] greyColor = {new Color(81, 85, 88)};

            // Set custom cell renderers and editors for columns 9, 10, and 11
            table.getColumn("").setCellRenderer(new Renderer());
            table.getColumn("").setCellEditor(new Editor(table));
            table.getColumn("").setCellRenderer(new PickColor(greyColor));

            table.getColumn(" ").setCellRenderer(new Renderer());
            table.getColumn(" ").setCellEditor(new Editor(table));
            table.getColumn(" ").setCellRenderer(new PickColor(blueColor));

            table.getColumn("  ").setCellRenderer(new Renderer());
            table.getColumn("  ").setCellEditor(new Editor(table));
            table.getColumn("  ").setCellRenderer(new PickColor(redColor));

            JScrollPane scrollPane = new JScrollPane(table);

            // Add search field
            JTextField searchField = new JTextField(20);
            TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(rowSorter);
            searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    searchTable();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    searchTable();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    searchTable();
                }

                private void searchTable() {
                    String text = searchField.getText().trim();
                    if (text.isEmpty()) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }
            });

            JButton backButton = new JButton("Back to admin page");
            backButton.addActionListener(_ -> {
                new MainFrame();
                frame.dispose();
            });


            // Set up the frame layout and add components
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1250, 600);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // Create a panel for the search bar and button
            JPanel topPanel = new JPanel(new BorderLayout());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(backButton);

            topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
            topPanel.add(searchField, BorderLayout.CENTER);

            // Add search bar and table to the frame
            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    // Method to refresh the table data
    public static void refreshTableData() {
        List<UserModel> userModelList = userController.userModelList();
        tableModel.setRowCount(0);
        quickSort(userModelList, 0, userModelList.size() - 1);

        No = 0;
        Object[] row = new Object[12];
        for (UserModel userModel : userModelList) {
            row[0] = ++No;
            row[1] = userModel.getId();
            row[2] = userModel.getName();
            row[3] = userModel.getAge();
            row[4] = userModel.getAddress();
            row[5] = userModel.getPhoneNo();
            row[6] = userModel.getAmount();
            row[7] = userModel.getStatus();
            row[8] = userModel.getDate();
            if (Objects.equals(row[7], "Restricted"))
            {
                row[9] = "UnRestrict";
            } else {
                row[9] = "Restrict";
            }
            row[10] = "Edit";
            row[11] = "Delete";
            tableModel.addRow(row);
        }
    }

    public static void quickSort(List<UserModel> userModelList, int low, int high) {
        if (low < high) {
            // Partition the list and get the pivot index
            int pi = partition(userModelList, low, high);

            // Recursively sort the elements before and after the partition
            quickSort(userModelList, low, pi - 1);
            quickSort(userModelList, pi + 1, high);
        }
    }

    private static int partition(List<UserModel> userModelList, int low, int high) {
        // Pivot (using the last element)
        String pivot = userModelList.get(high).getName();

        int i = (low - 1); // Index of the smaller element
        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (userModelList.get(j).getName().compareToIgnoreCase(pivot) <= 0) {
                i++;

                // Swap userModelList[i] and userModelList[j]
                UserModel temp = userModelList.get(i);
                userModelList.set(i, userModelList.get(j));
                userModelList.set(j, temp);
            }
        }

        // Swap userModelList[i+1] and userModelList[high] (or the pivot)
        UserModel temp = userModelList.get(i + 1);
        userModelList.set(i + 1, userModelList.get(high));
        userModelList.set(high, temp);

        return i + 1;
    }

}