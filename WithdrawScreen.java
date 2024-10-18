import javax.swing.*;
import java.awt.*;
public class WithdrawScreen extends JFrame {
    private JTextField withdrawAmountField;
    private String cardNumber;
    private DatabaseConnection db;

    public WithdrawScreen(String cardNumber) {
        this.cardNumber = cardNumber;
        db = new DatabaseConnection();
        setTitle("Withdraw Money");
        setSize(300, 200);
        setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Enter Amount:");
        add(amountLabel);

        withdrawAmountField = new JTextField();
        add(withdrawAmountField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> performWithdraw());
        add(withdrawButton);
    }

    private void performWithdraw() {
        double amount = Double.parseDouble(withdrawAmountField.getText());
        double balance = db.getBalance(cardNumber);
        if (amount <= balance) {
            balance -= amount;
            db.updateBalance(cardNumber, balance);
            db.addLoanPoints(cardNumber, (int) amount / 100);  // Award 1 point for every ₹100 withdrawn
            db.logTransaction(cardNumber, "Withdrawal", amount);
            JOptionPane.showMessageDialog(this, "Withdrawal successful. New balance: ₹" + balance);
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient funds.");
        }
        dispose();
    }
}
