import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMMenu extends JFrame {
    private DatabaseConnection db; // Database connection object

    public ATMMenu(String cardNumber) {
        db = new DatabaseConnection(); // Initialize the database connection
        setTitle("ATM Menu");
        setSize(400, 550); // Adjust size to accommodate new options
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Theme color and font
        Color themeColor = ThemeColor.getSelectedColor();
        Font themeFont = ThemeColor.getSelectedFont();

        // Title label
        JLabel titleLabel = new JLabel("ATM Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK); 
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 1, 10, 10)); // Updated layout for additional buttons
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(mainPanel, BorderLayout.CENTER);

        // Withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(themeFont);
        withdrawButton.setBackground(themeColor);
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawScreen(cardNumber).setVisible(true);
            }
        });
        mainPanel.add(withdrawButton);

        // Deposit button
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(themeFont);
        depositButton.setBackground(themeColor);
        depositButton.setForeground(Color.WHITE);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositScreen(cardNumber).setVisible(true);
            }
        });
        mainPanel.add(depositButton);

        // Transfer button
        JButton transferButton = new JButton("Transfer");
        transferButton.setFont(themeFont);
        transferButton.setBackground(themeColor);
        transferButton.setForeground(Color.WHITE);
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferScreen(cardNumber).setVisible(true);
            }
        });
        mainPanel.add(transferButton);

        // Check balance button
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setFont(themeFont);
        balanceButton.setBackground(themeColor);
        balanceButton.setForeground(Color.WHITE);
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = db.getBalance(cardNumber);
                JOptionPane.showMessageDialog(null, "Your current balance is ₹" + balance);
            }
        });
        mainPanel.add(balanceButton);

        // Balance Graph button
        JButton balanceGraphButton = new JButton("Balance Graph");
        balanceGraphButton.setFont(themeFont);
        balanceGraphButton.setBackground(themeColor);
        balanceGraphButton.setForeground(Color.WHITE);
        balanceGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the balance graph using transaction history
                new CheckBalanceGraph(cardNumber, db).setVisible(true);
            }
        });
        mainPanel.add(balanceGraphButton);

        // UPI button (Show image)
        JButton upiButton = new JButton("UPI");
        upiButton.setFont(themeFont);
        upiButton.setBackground(themeColor);
        upiButton.setForeground(Color.WHITE);
        upiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show UPI image
                new UPIScreen().setVisible(true);
            }
        });
        mainPanel.add(upiButton);

        // Transaction History button
        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setFont(themeFont);
        transactionHistoryButton.setBackground(themeColor);
        transactionHistoryButton.setForeground(Color.WHITE);
        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionHistoryScreen(cardNumber, db).setVisible(true);
            }
        });
        mainPanel.add(transactionHistoryButton);

        // CryptoCurrency Wallet button
        JButton cryptoButton = new JButton("CryptoCurrency Wallet");
        cryptoButton.setFont(themeFont);
        cryptoButton.setBackground(themeColor);
        cryptoButton.setForeground(Color.WHITE);
        cryptoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CryptoConversionScreen(cardNumber, db).setVisible(true);
            }
        });
        mainPanel.add(cryptoButton);

        // Loan Points button
        JButton loanPointsButton = new JButton("Redeem Loan Points");
        loanPointsButton.setFont(themeFont);
        loanPointsButton.setBackground(themeColor);
        loanPointsButton.setForeground(Color.WHITE);
        loanPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoanPointsScreen(cardNumber).setVisible(true);
            }
        });
        mainPanel.add(loanPointsButton);

        // Exit button
        JButton exitButton = new JButton("Logout");
        exitButton.setFont(themeFont);
        exitButton.setBackground(themeColor);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the ATMMenu window
                LoginPage loginPage = new LoginPage();  // Reopen the login page
                loginPage.setVisible(true);
            }
        });
        mainPanel.add(exitButton);
    }
}
