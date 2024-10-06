import javax.swing.*;
import java.awt.*;
public class TransferScreen extends JFrame {
    private JTextField transferAmountField;
    private JTextField recipientCardField;

    public TransferScreen(String cardNumber) {

        setTitle("Transfer Money");
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Recipient Card Number:"));
        recipientCardField = new JTextField();
        add(recipientCardField);

        add(new JLabel("Amount to Transfer:"));
        transferAmountField = new JTextField();
        add(transferAmountField);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> new OtpVerificationScreen(cardNumber, recipientCardField.getText(), Double.parseDouble(transferAmountField.getText())).setVisible(true));
        add(nextButton);
    }
}
