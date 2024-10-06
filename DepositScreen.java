import javax.swing.*;
import java.awt.*;
public class DepositScreen extends JFrame {
    private JTextField depositAmountField;
    private String cardNumber;
    private DatabaseConnection db;

    public DepositScreen(String cardNumber) {
        this.cardNumber = cardNumber;
        db = new DatabaseConnection();
        setTitle("Deposit Money");
        setSize(300, 200);
        setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Enter Amount:");
        add(amountLabel);

        depositAmountField = new JTextField();
        add(depositAmountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> performDeposit());
        add(depositButton);
    }

    private void performDeposit() {
        double amount = Double.parseDouble(depositAmountField.getText());
        double balance = db.getBalance(cardNumber);
        balance += amount;
        db.updateBalance(cardNumber, balance);
        JOptionPane.showMessageDialog(this, "Deposit successful. New balance: ₹" + balance);
    }
}
