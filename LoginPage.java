import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField cardNumberField;
    private JPasswordField pinField;
    private DatabaseConnection db;
    private JButton loginButton;
    private JButton forgotPinButton; // Declare the login button

    public LoginPage() {
        db = new DatabaseConnection();  // Initialize the database connection
        setTitle("ATM Login");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Theme color and font
        Color themeColor = new Color(0, 51, 102);
        Font themeFont = ThemeColor.getSelectedFont();

        // Title label
        JLabel titleLabel = new JLabel("ATM Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);// Use font color based on theme
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(mainPanel, BorderLayout.CENTER);

        // Components
        mainPanel.add(new JLabel("Card Number:", SwingConstants.CENTER));
        cardNumberField = new JTextField();
        cardNumberField.setFont(themeFont);
        cardNumberField.setForeground(Color.BLACK); // Set font color
        mainPanel.add(cardNumberField);

        mainPanel.add(new JLabel("PIN:", SwingConstants.CENTER));
        pinField = new JPasswordField();
        pinField.setFont(themeFont);
        pinField.setForeground(Color.BLACK); // Set font color
        mainPanel.add(pinField);

        // Add this after initializing loginButton
// Initialize and set the login button
loginButton = new JButton("LOGIN");
loginButton.setFont(themeFont);
loginButton.setBackground(themeColor);
loginButton.setForeground(Color.WHITE);
loginButton.setFont(loginButton.getFont().deriveFont(14f)); // Change 14f to your desired font size
mainPanel.add(loginButton);

// Action listener for login button
loginButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        login();
    }
});

// Add the Forgot PIN button below the LOGIN button
forgotPinButton = new JButton("Forgot PIN");
forgotPinButton.setFont(themeFont);
forgotPinButton.setBackground(themeColor);
forgotPinButton.setForeground(Color.WHITE);
forgotPinButton.setFont(forgotPinButton.getFont().deriveFont(12f));
forgotPinButton.setPreferredSize(new Dimension(120, 30)); // Set preferred size for smaller button
mainPanel.add(forgotPinButton);

// Action listener for forgot PIN button
forgotPinButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ForgotPinScreen().setVisible(true); // Open Forgot PIN screen
    }
});



        // Theme selection combo box
        String[] themes = {"Default", "Light Blue", "Dark Green", "Soft Gray", "Coral", "Mint Green"};
        JComboBox<String> themeSelector = new JComboBox<>(themes);
        themeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the theme selection logic
                switch (themeSelector.getSelectedItem().toString()) {
                    case "Light Blue":
                        ThemeColor.setSelectedColor(new Color(173, 216, 230)); // Light Blue
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16));
                        break;
                    case "Dark Green":
                        ThemeColor.setSelectedColor(new Color(0, 100, 0)); // Dark Green
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16));
                        break;
                    case "Soft Gray":
                        ThemeColor.setSelectedColor(new Color(211, 211, 211)); // Soft Gray
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16));
                        break;
                    case "Coral":
                        ThemeColor.setSelectedColor(new Color(189, 252, 221)); // Coral
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16)); 
                        break;
                    case "Mint Green":
                        ThemeColor.setSelectedColor(new Color(55, 127, 80)); // Mint Green
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16));
                        break;
                    default:
                        ThemeColor.setSelectedColor(new Color(0, 51, 102)); // Default Navy Blue
                        ThemeColor.setSelectedFont(new Font("Arial", Font.BOLD, 16));
                }
                updateTheme(); // Update theme for all components
            }
        });
        mainPanel.add(new JLabel("Select Theme:", SwingConstants.CENTER));
        mainPanel.add(themeSelector);
    }
        // Initialize and set the login button
        

    private void login() {
        String cardNumber = cardNumberField.getText();
        String pin = new String(pinField.getPassword());

        // Authenticate user from database
        if (db.authenticateUser(cardNumber, pin)) {
            dispose(); // Close login page
            ATMMenu atm = new ATMMenu(cardNumber);  // Pass the card number to ATM screen
            atm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid card number or PIN.");
        }
    }

    private void updateTheme() {
        // Update theme for the login page
        Color themeColor = ThemeColor.getSelectedColor();
        Font themeFont = ThemeColor.getSelectedFont();
        Color fontColor = ThemeColor.getFontColor(); // Get the font color based on the theme
        loginButton.setBackground(themeColor);
        loginButton.setForeground(fontColor);
        forgotPinButton.setBackground(themeColor);
        forgotPinButton.setForeground(fontColor);

        getContentPane().setBackground(themeColor);
        for (Component comp : getContentPane().getComponents()) {
            comp.setFont(themeFont);
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(fontColor); // Set label font color
            } else if (comp instanceof JButton) {
                ((JButton) comp).setBackground(themeColor); // Set button background color
                ((JButton) comp).setForeground(Color.WHITE); // Set button text color
            } else if (comp instanceof JTextField) {
                ((JTextField) comp).setForeground(fontColor); // Set text field font color
            } else if (comp instanceof JPasswordField) {
                ((JPasswordField) comp).setForeground(fontColor); // Set password field font color
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        // Launch the login page
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
