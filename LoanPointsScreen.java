import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanPointsScreen extends JFrame {

    private DatabaseConnection db;
    private String cardNumber;
    private int availablePoints;

    public LoanPointsScreen(String cardNumber) {
        this.db = new DatabaseConnection();
        this.cardNumber = cardNumber;

        setTitle("Redeem Loan Points");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch available loan points
        availablePoints = db.getLoanPoints(cardNumber);

        // Title Label
        JLabel titleLabel = new JLabel("Redeem Loan Points", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Show available points
        JLabel pointsLabel = new JLabel("Available Points: " + availablePoints, SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(pointsLabel);

        // Input field for redeeming points
        JTextField redeemField = new JTextField();
        redeemField.setFont(new Font("Arial", Font.PLAIN, 18));
        redeemField.setHorizontalAlignment(JTextField.CENTER);
        mainPanel.add(redeemField);

        // Redeem Button
        JButton redeemButton = new JButton("Redeem Points");
        redeemButton.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(redeemButton);

        // Action for redeeming points
        redeemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int pointsToRedeem = Integer.parseInt(redeemField.getText());
                    if (pointsToRedeem <= availablePoints) {
                        redeemLoanPoints(pointsToRedeem);
                        JOptionPane.showMessageDialog(null, "Successfully redeemed " + pointsToRedeem + " points.");
                        availablePoints -= pointsToRedeem;
                        pointsLabel.setText("Available Points: " + availablePoints);
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient points.");
                    }
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input.");
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the screen
            }
        });
    }

    private void redeemLoanPoints(int pointsToRedeem) {
        int newBalance = pointsToRedeem;  // Assume 1 point = ₹1
        double currentBalance = db.getBalance(cardNumber);
        db.updateBalance(cardNumber, currentBalance + newBalance);
        db.updateLoanPoints(cardNumber, availablePoints - pointsToRedeem);
    }
}
