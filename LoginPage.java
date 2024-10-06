import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField cardNumberField;
    private JPasswordField pinField;
    private DatabaseConnection db;

    public LoginPage() {
        db = new DatabaseConnection();  // Initialize the database connection
        setTitle("ATM Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Theme color
        Color themeColor = new Color(0, 51, 102);  // Navy blue

        // Title label
        JLabel titleLabel = new JLabel("ATM Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(themeColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(mainPanel, BorderLayout.CENTER);

        // Components
        mainPanel.add(new JLabel("Card Number:", SwingConstants.CENTER));
        cardNumberField = new JTextField();
        cardNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(cardNumberField);

        mainPanel.add(new JLabel("PIN:", SwingConstants.CENTER));
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(themeColor);
        loginButton.setForeground(Color.WHITE);
        mainPanel.add(loginButton);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

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

    public static void main(String[] args) {
        // Launch the login page
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
