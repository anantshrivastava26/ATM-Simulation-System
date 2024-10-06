import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class OtpVerificationScreen extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JTextField otpField;
    private String otpCode;
    private String cardNumber;
    private String recipientCard;
    private double amount;
    private DatabaseConnection db;

    public OtpVerificationScreen(String cardNumber, String recipientCard, double amount) {
        this.cardNumber = cardNumber;
        this.recipientCard = recipientCard;
        this.amount = amount;
        db = new DatabaseConnection();

        setTitle("OTP Verification");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        // User ID and password fields
        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton generateOtpButton = new JButton("Generate OTP");
        generateOtpButton.addActionListener(e -> generateOtp());
        add(generateOtpButton);

        add(new JLabel("Enter OTP:"));
        otpField = new JTextField();
        add(otpField);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> performTransfer());
        add(transferButton);
    }

    private void generateOtp() {
        // Fetch user credentials from the input fields
        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());
    
        // Call the database to verify these credentials for the logged-in card number
        if (db.verifyUserForOtp(cardNumber, userId, password)) {
            otpCode = String.valueOf(new Random().nextInt(999999));  // Generate random 6-digit OTP
            JOptionPane.showMessageDialog(this, "OTP Generated: " + otpCode);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid User ID or Password.");
        }
    }
    

    private void performTransfer() {
        String enteredOtp = otpField.getText();

        if (otpCode != null && otpCode.equals(enteredOtp)) {
            double senderBalance = db.getBalance(cardNumber);
            double recipientBalance = db.getBalance(recipientCard);

            if (amount <= senderBalance) {
                senderBalance -= amount;
                recipientBalance += amount;

                db.updateBalance(cardNumber, senderBalance);  // Update sender balance
                db.updateBalance(recipientCard, recipientBalance);  // Update recipient balance

                JOptionPane.showMessageDialog(this, "Transfer successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid OTP.");
        }
    }
}
