import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the ATMMenu window
            }
        });
        add(back);
    }
}
