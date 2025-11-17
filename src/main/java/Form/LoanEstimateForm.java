package Form;

import Controller.userController;
import Model.LoanModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class LoanEstimateForm extends JFrame {

    // Form fields
    private JTextField firstNameField, emailField, phoneField, birthField, addressField1, loanAmountField,
            nrcNumberField, jobField, assetsField, collateralField, tenureYears;
    private JComboBox<String> loanTypeBox, incomeBox, nrcComboBox, maritalStatusBox, nrcPrefixBox;
    private JButton submitButton;
    private JButton btnCancel;

    public LoanEstimateForm() {
        SwingUtilities.invokeLater(() -> {
            setupForm();
            createFormFields();
            JPanel formPanel = createFormPanel();
            addFormFieldsToPanel(formPanel);
            addSubmitButtonAction();

            JPanel buttonPanel = createButtonPanel();
            formPanel.add(Box.createVerticalStrut(15));  // Add some space before buttons
            formPanel.add(buttonPanel);

            // Add form panel to JScrollPane to make it scrollable
            JScrollPane scrollPane = new JScrollPane(formPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollPane);

            setVisible(true);
        });
    }

    private void setupForm() {
        setTitle("Loan Estimate Form");
        setSize(580, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createFormFields() {
        firstNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        birthField = new JTextField();
        addressField1 = new JTextField();
        loanAmountField = new JTextField();
        nrcNumberField = new JTextField();
        jobField = new JTextField();  // New Job field
        assetsField = new JTextField();  // Changed to a JTextField for Assets
        collateralField = new JTextField();  // New Collateral field
        tenureYears = new JTextField();

        loanTypeBox = createCustomComboBox(new String[]{"",
                "Personal Loan - Unsecured", "Personal Loan - Secured", "Mortgage Loan",
                "Auto Loan - New Car", "Auto Loan", "Student Loan",
                "Student Loan - Private", "Home Equity Loan", "Home Equity Line of Credit (HELoC)",
                "Business Loan - Term Loan", "Business Loan"});

        incomeBox = createCustomComboBox(new String[]{"", "Less than 200k", "200k - 500k", "500k - 1M", "More than 1M"});

        nrcComboBox = createCustomComboBox(new String[]{"", "MaYaKa", "LaKhaNa", "TaUkKa", "MyaUkKa", "LaThaNa", "PaBaTa", "ThaKaTa", "AhLoNa", "AhPaNa", "KhaAhTa", "MaHaAhMa", "PaThaKa", "MaLaNa", "BaKaNa", "TaNyiNa", "HtaTaPa", "LaThaYa", "MaAuPa", "MaKaNa", "NgePaLa", "SaKaNa", "KaLaNa", "YaBaNa", "MaYaNa", "SaTaNa", "MaPyaNa", "MaTaNa", "TaKyaNa", "LaYaNa", "KyaTaNa"});

        maritalStatusBox = createCustomComboBox(new String[]{"", "Married", "Widowed", "Divorced", "Single"});

        // Create a dropdown for NRC prefix (1 to 14)
        nrcPrefixBox = createCustomComboBox(new String[]{
                "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"
        });

        submitButton = new JButton("Approve");
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(_ -> dispose());
    }

    private JComboBox<String> createCustomComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setRenderer(new CustomComboBoxRenderer());
        return comboBox;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));  // Set layout to FlowLayout for side-by-side alignment
        buttonPanel.setBackground(Color.WHITE);  // Match background color

        // Add buttons to the panel
        buttonPanel.add(btnCancel);
        buttonPanel.add(submitButton);

        return buttonPanel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));  // Increase space around the edges

        // Set background color to white
        panel.setBackground(Color.WHITE);

        return panel;
    }

    private void addFormFieldsToPanel(JPanel panel) {
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Full Name:", firstNameField));
        panel.add(Box.createVerticalStrut(15));

        // NRC Section
        JPanel nrcPanel = new JPanel();
        nrcPanel.setLayout(new BoxLayout(nrcPanel, BoxLayout.X_AXIS));
        nrcPanel.setBackground(Color.WHITE);  // Match background color

        // Add dropdown for NRC prefix (1 to 14)
        JLabel nrcPrefixLabel = new JLabel("Prefix:");
        nrcPrefixLabel.setPreferredSize(new Dimension(60, 25)); // Adjust width of "Prefix" label
        nrcPrefixLabel.setForeground(new Color(0, 100, 0));  // Set text color to dark green
        nrcPanel.add(nrcPrefixLabel);

        nrcPrefixBox.setPreferredSize(new Dimension(70, 25)); // Adjust size as needed
        nrcPrefixBox.setMaximumSize(new Dimension(70, 25));
        nrcPrefixBox.setMinimumSize(new Dimension(70, 25));
        nrcPanel.add(nrcPrefixBox);

        JLabel nrcComboLabel = new JLabel("NRC:");
        nrcComboLabel.setPreferredSize(new Dimension(60, 25)); // Adjust width of "NRC" label
        nrcComboLabel.setForeground(new Color(0, 100, 0));  // Set text color to dark green
        nrcPanel.add(nrcComboLabel);

        // Set uniform size for NRC ComboBox and NRC Number Field
        Dimension uniformSize = new Dimension(110, 25); // Adjust width as needed

        nrcComboBox.setPreferredSize(uniformSize);
        nrcComboBox.setMaximumSize(uniformSize); // Ensure it doesn't expand
        nrcComboBox.setMinimumSize(uniformSize);
        nrcPanel.add(nrcComboBox);

        JLabel nrcNumberLabel = new JLabel("N:");
        nrcNumberLabel.setPreferredSize(new Dimension(30, 25)); // Adjust width of "N" label
        nrcNumberLabel.setForeground(new Color(0, 100, 0));  // Set text color to dark green
        nrcPanel.add(nrcNumberLabel);

        nrcNumberField.setPreferredSize(uniformSize);
        nrcNumberField.setMaximumSize(uniformSize);
        nrcNumberField.setMinimumSize(uniformSize);
        nrcPanel.add(nrcNumberField);

        // Set the overall size of the NRC panel to align with other fields
        nrcPanel.setPreferredSize(new Dimension(300, 25));
        nrcPanel.setMaximumSize(new Dimension(300, 25));
        nrcPanel.setMinimumSize(new Dimension(300, 25));

        // Add NRC panel to the form
        panel.add(createLabeledComponent("NRC:", nrcPanel));
        panel.add(Box.createVerticalStrut(15));

        // Remaining fields
        panel.add(createLabeledComponent("Email:", emailField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Phone:", phoneField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Date of Birth:", birthField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Address Line:", addressField1));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Job:", jobField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Marital Status:", maritalStatusBox));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Loan Type:", loanTypeBox));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Assets:", assetsField));  // Updated Assets field to a JTextField
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Collateral:", collateralField));  // New Collateral field
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Loan Amount:", loanAmountField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Annual Income:", incomeBox));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Tenure year:", tenureYears));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("", btnCancel));
        panel.add(Box.createVerticalStrut(15));
        panel.add(submitButton);
    }

    private JPanel createLabeledComponent(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);  // Match background color

        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(130, 25)); // Adjust label width
        jLabel.setForeground(new Color(0, 100, 0));  // Set label text color to dark green
        panel.add(jLabel);

        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25)); // Ensure components shrink if necessary
        panel.add(component);

        return panel;
    }

    private void addSubmitButtonAction() {
        submitButton.addActionListener(_ -> {
            if (validateForm()) {
                long loanID = ThreadLocalRandom.current().nextLong(1000000L, 999999999L);
                LoanModel loan = new LoanModel();
                loan.setLoanID(loanID);
                loan.setName(firstNameField.getText());
                loan.setEmail(emailField.getText());
                loan.setPhone(phoneField.getText());
                loan.setDOB(Date.valueOf(birthField.getText()));
                loan.setAddress(addressField1.getText());
                loan.setLoanAmount(BigDecimal.valueOf(Long.parseLong(loanAmountField.getText())));
                loan.setInterestRate(10.0);
                String nrcCombo = Objects.requireNonNull(nrcComboBox.getSelectedItem()).toString();
                String nrcPrefix = Objects.requireNonNull(nrcPrefixBox.getSelectedItem()).toString();
                String NRC = nrcPrefix+"/"+nrcCombo+"(N)"+nrcNumberField.getText();
                loan.setNRC(NRC);
                loan.setJob(jobField.getText());
                loan.setAsset(assetsField.getText());
                loan.setCollateral(collateralField.getText());
                String loanType = Objects.requireNonNull(loanTypeBox.getSelectedItem()).toString();
                loan.setLoanType(String.valueOf(loanType));
                loan.setTimeFrame(String.valueOf(1));
                String income = Objects.requireNonNull(incomeBox.getSelectedItem()).toString();
                loan.setIncome(String.valueOf(income));
                String maritalStatus = Objects.requireNonNull(maritalStatusBox.getSelectedItem()).toString();
                loan.setMaritalStatus(String.valueOf(maritalStatus));
                loan.setStatus("Approved");
                double EMI = calculateEMI(Double.parseDouble(loanAmountField.getText()), loan.getInterestRate(),
                        Integer.parseInt(tenureYears.getText()));
                loan.setTenureYears(Integer.parseInt(tenureYears.getText()));
                loan.setEMI(BigDecimal.valueOf(EMI));
                boolean result = userController.setLoan(loan);
                String message = result ? "Form submitted successfully" : "From saving failed.";
                JOptionPane.showMessageDialog(null, message);
                if(result)
                {
                    dispose();
                }
            }
        });
    }

    private boolean validateForm() {
        if (firstNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Full Name is required.");
            return false;
        }
        // Validate Phone Number
        String phone = phoneField.getText();
        if (phone.length() < 10) {
            JOptionPane.showMessageDialog(null, "Please enter a valid phone number with at least 10 digits.");
            return false;
        }

        // Validate Date of Birth
        if (birthField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Date of Birth is required.");
            return false;
        }

        // Validate Address Line
        if (addressField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address Line is required.");
            return false;
        }

        // Validate Job
        if (jobField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Job is required.");
            return false;
        }

        // Validate Assets
        if (assetsField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Assets field is required.");
            return false;
        }

        // Validate Collateral
        if (collateralField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Collateral field is required.");
            return false;
        }

        // Validate NRC Prefix ComboBox
        if (nrcPrefixBox.getSelectedIndex() == 0) {  // Check if the first item (empty string) is selected
            JOptionPane.showMessageDialog(null, "NRC Prefix is required.");
            return false;
        }

        // Validate NRC ComboBox
        if (nrcComboBox.getSelectedIndex() == 0) {  // Check if the first item (empty string) is selected
            JOptionPane.showMessageDialog(null, "NRC is required.");
            return false;
        }

        // Validate NRC Number
        if (nrcNumberField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NRC Number is required.");
            return false;
        }

        // Validate Loan Amount
        if (loanAmountField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Loan Amount is required.");
            return false;
        }

        // Validate Loan Type
        if (loanTypeBox.getSelectedIndex() == 0) {  // Check if the first item (empty string) is selected
            JOptionPane.showMessageDialog(null, "Loan Type is required.");
            return false;
        }

        // Validate Annual Income
        if (incomeBox.getSelectedIndex() == 0) {  // Check if the first item (empty string) is selected
            JOptionPane.showMessageDialog(null, "Annual Income is required.");
            return false;
        }

        // Validate Marital Status
        if (maritalStatusBox.getSelectedIndex() == 0) {  // Check if the first item (empty string) is selected
            JOptionPane.showMessageDialog(null, "Marital Status is required.");
            return false;
        }

        if(tenureYears.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Tenure year is required.");
            return false;
        }

        return true;
    }

    // Custom renderer to set text color for combo box items
    private static class CustomComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            c.setForeground(new Color(0, 100, 0));  // Set text color to dark green
            return c;
        }
    }

    public static double calculateEMI(double principal, double annualInterestRate, int tenureInYears) {
        // Convert annual interest rate to a monthly rate in decimal form
        double monthlyInterestRate = (annualInterestRate / 100) / 12;

        // Calculate the number of monthly installments
        int tenureInMonths = tenureInYears * 12;

        // Calculate the EMI using the formula

        return (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureInMonths))
                / (Math.pow(1 + monthlyInterestRate, tenureInMonths) - 1);
    }
}
