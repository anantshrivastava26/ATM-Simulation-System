import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPinScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField newPinField1;
    private JTextField newPinField2;
    private DatabaseConnection db;

    public ForgotPinScreen() {
        db = new DatabaseConnection(); // Initialize the database connection
        setTitle("Forgot PIN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Components
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("New PIN:"));
        newPinField1 = new JTextField();
        add(newPinField1);

        add(new JLabel("Confirm New PIN:"));
        newPinField2 = new JTextField();
        add(newPinField2);

        JButton updatePinButton = new JButton("Update PIN");
        add(updatePinButton);

        // Action listener for update PIN button
        updatePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePin();
            }
        });
    }

    private void updatePin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String newPin1 = newPinField1.getText();
        String newPin2 = newPinField2.getText();
    
        // Check if new PINs match
        if (!newPin1.equals(newPin2)) {
            JOptionPane.showMessageDialog(this, "New PINs do not match.");
            return;
        }
    
        // Verify user credentials
        if (db.verifyUserCredentials(username, password)) {
            // Update PIN in the database
            db.updatePin(username, newPin1);
            JOptionPane.showMessageDialog(this, "PIN updated successfully.");
            dispose(); // Close the forgot PIN screen
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
    
}
