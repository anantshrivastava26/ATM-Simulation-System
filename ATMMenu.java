import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMMenu extends JFrame {
    private DatabaseConnection db;

    public ATMMenu(String cardNumber) {
        db = new DatabaseConnection();

        setTitle("ATM Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Theme color
        Color themeColor = new Color(0, 51, 102);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(buttonPanel, BorderLayout.CENTER);

        // Buttons for each operation
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton logoutButton = new JButton("Logout");  // New Logout Button

        // Button styles
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 16));
        depositButton.setFont(new Font("Arial", Font.BOLD, 16));
        transferButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkBalanceButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));  // Style for Logout Button

        withdrawButton.setBackground(themeColor);
        depositButton.setBackground(themeColor);
        transferButton.setBackground(themeColor);
        checkBalanceButton.setBackground(themeColor);
        logoutButton.setBackground(themeColor);  // Style for Logout Button

        withdrawButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        transferButton.setForeground(Color.WHITE);
        checkBalanceButton.setForeground(Color.WHITE);
        logoutButton.setForeground(Color.WHITE);  // Text color for Logout Button

        // Add buttons to panel
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(logoutButton);  // Add Logout Button to the panel

        // Action listeners for each button
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawScreen(cardNumber).setVisible(true);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositScreen(cardNumber).setVisible(true);
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferScreen(cardNumber).setVisible(true);
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = db.getBalance(cardNumber);
                JOptionPane.showMessageDialog(null, "Your current balance is ₹" + balance);
            }
        });

        // Action listener for the Logout Button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the ATMMenu window
                LoginPage loginPage = new LoginPage();  // Reopen the login page
                loginPage.setVisible(true);
            }
        });
    }
}
