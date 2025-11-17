package Form;
import javax.swing.*;
import java.awt.*;
public class HomePage extends JFrame {
    private JLabel logoLabel;
    private JLabel imageLabel;

    public HomePage() {
        // Frame Setup
        SwingUtilities.invokeLater(() ->{
            setTitle("Banking");
            setSize(1400, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);


            setLayout(new BorderLayout());

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new BorderLayout());
            navPanel.setBackground(new Color(0x509A92));

            logoLabel = new JLabel();
            logoLabel.setHorizontalAlignment(JLabel.LEFT);
//            setLogoImage();
            navPanel.add(logoLabel, BorderLayout.WEST);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(new Color(0x509A92));

            JButton adminButton = new JButton("Admin");
            JButton customerButton = new JButton("Customer");
            JButton exitButton = new JButton("Exit");

            Dimension buttonSize = new Dimension(120, 40);
            adminButton.setPreferredSize(buttonSize);
            customerButton.setPreferredSize(buttonSize);
            exitButton.setPreferredSize(buttonSize);

            adminButton.setBackground(new Color(0x4FEDDB));
            customerButton.setBackground(new Color(0x54E8D6));
            exitButton.setBackground(new Color(0xFF4C4C));

            buttonPanel.add(adminButton);
            buttonPanel.add(customerButton);
            buttonPanel.add(exitButton);

            navPanel.add(buttonPanel, BorderLayout.EAST);

            add(navPanel, BorderLayout.NORTH);

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new GridBagLayout());
            leftPanel.setBackground(new Color(0xDCECEC));

            JLabel welcomeLabel = new JLabel("Welcome to Horizon");
            welcomeLabel.setFont(new Font("sans", Font.BOLD, 24));
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            welcomeLabel.setVerticalAlignment(JLabel.BOTTOM);

            JLabel taglineLabel = new JLabel("              Success Secure Scalability              ");
            taglineLabel.setFont(new Font("sans", Font.ITALIC, 18));
            taglineLabel.setForeground(Color.GRAY);
            taglineLabel.setHorizontalAlignment(JLabel.CENTER);
            taglineLabel.setVerticalAlignment(JLabel.TOP);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0.5;
            gbc.insets = new Insets(20, 20, 20, 20); // Space between labels

            leftPanel.add(welcomeLabel, gbc);

            gbc.gridy = 1;
            gbc.weighty = 0.5;
            leftPanel.add(taglineLabel, gbc);

            add(leftPanel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BorderLayout());
            rightPanel.setBackground(new Color(0x09433C));

            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            setImageIcon();

            rightPanel.add(imageLabel, BorderLayout.CENTER);

            add(rightPanel, BorderLayout.EAST);

            exitButton.addActionListener(_ -> System.exit(0));

            adminButton.addActionListener(_ -> {
                new AdminLogin();
                dispose();
            });

            customerButton.addActionListener(_ -> {
                new UserAccLogin();
                dispose();
            });

            setVisible(true);
        });
    }

    private void setImageIcon()
    {
        try {
            ImageIcon imageIcon = new ImageIcon("Logo.png");
            imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}