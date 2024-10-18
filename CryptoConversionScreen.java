import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CryptoConversionScreen extends JFrame {


    // Static conversion rates
    private final double BITCOIN_CONVERSION_RATE = 250000.0;  // 1 Bitcoin = ₹2,50,000
    private final double ETHEREUM_CONVERSION_RATE = 150000.0;   // 1 Ethereum = ₹1,50,000

    public CryptoConversionScreen(String cardNumber, DatabaseConnection db) {

        setTitle("CryptoCurrency Wallet");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel for balance display
        JPanel balancePanel = new JPanel(new GridLayout(2, 2));
        balancePanel.setBorder(BorderFactory.createTitledBorder("Current Crypto Balances"));

        JLabel bitcoinLabel = new JLabel("Bitcoin Balance: ");
        JLabel bitcoinBalanceLabel = new JLabel(String.valueOf(db.getBitcoinBalance(cardNumber)));
        JLabel ethereumLabel = new JLabel("Ethereum Balance: ");
        JLabel ethereumBalanceLabel = new JLabel(String.valueOf(db.getEthereumBalance(cardNumber)));

        balancePanel.add(bitcoinLabel);
        balancePanel.add(bitcoinBalanceLabel);
        balancePanel.add(ethereumLabel);
        balancePanel.add(ethereumBalanceLabel);

        add(balancePanel, BorderLayout.NORTH);

        // Conversion panel
        JPanel conversionPanel = new JPanel(new GridLayout(3, 2));
        conversionPanel.setBorder(BorderFactory.createTitledBorder("Convert to Cryptocurrency"));

        JLabel balanceLabel = new JLabel("Current Balance: ₹" + db.getBalance(cardNumber));
        conversionPanel.add(balanceLabel);

        JTextField amountField = new JTextField();
        conversionPanel.add(new JLabel("Amount to Convert:"));
        conversionPanel.add(amountField);

        JComboBox<String> cryptoType = new JComboBox<>(new String[]{"Bitcoin", "Ethereum"});
        conversionPanel.add(new JLabel("Select Cryptocurrency:"));
        conversionPanel.add(cryptoType);

        JButton convertButton = new JButton("Convert");
        conversionPanel.add(convertButton);

        add(conversionPanel, BorderLayout.CENTER);

        // Convert action
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amountToConvert = Double.parseDouble(amountField.getText());
                    String selectedCrypto = (String) cryptoType.getSelectedItem();
                    double currentBalance = db.getBalance(cardNumber);

                    if (amountToConvert > currentBalance) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance to convert.");
                        return;
                    }

                    // Calculate the equivalent cryptocurrency based on conversion rate
                    if (selectedCrypto.equals("Bitcoin")) {
                        double bitcoinEquivalent = amountToConvert / BITCOIN_CONVERSION_RATE;
                        double newBitcoinBalance = db.getBitcoinBalance(cardNumber) + bitcoinEquivalent;
                        db.updateBitcoinBalance(cardNumber, newBitcoinBalance);
                    } else if (selectedCrypto.equals("Ethereum")) {
                        double ethereumEquivalent = amountToConvert / ETHEREUM_CONVERSION_RATE;
                        double newEthereumBalance = db.getEthereumBalance(cardNumber) + ethereumEquivalent;
                        db.updateEthereumBalance(cardNumber, newEthereumBalance);
                    }

                    // Update main balance
                    db.updateBalance(cardNumber, currentBalance - amountToConvert);
                    JOptionPane.showMessageDialog(null, "Conversion successful!");

                    // Update displayed balances
                    bitcoinBalanceLabel.setText(String.valueOf(db.getBitcoinBalance(cardNumber)));
                    ethereumBalanceLabel.setText(String.valueOf(db.getEthereumBalance(cardNumber)));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount entered.");
                }
            }
        });
    }
}
