import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm_db";  // Use your database URL
    private static final String USER = "root";  // MySQL username
    private static final String PASS = "Aster@2405";  // MySQL password

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Database connection established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
    public boolean authenticateUser(String cardNumber, String pin) {
        String query = "SELECT * FROM users WHERE card_number = ? AND pin = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            pstmt.setString(2, pin);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Return true if user exists

        } catch (SQLException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
        return false;
    }
    // Authentication methods...

    public double getBalance(String cardNumber) {
        String query = "SELECT balance FROM users WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching balance: " + e.getMessage());
        }
        return 0;
    }

    public void updateBalance(String cardNumber, double newBalance) {
        String query = "UPDATE users SET balance = ? WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    // 1. Loan Points Management
    public int getLoanPoints(String cardNumber) {
        String query = "SELECT loan_points FROM users WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("loan_points");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching loan points: " + e.getMessage());
        }
        return 0;
    }

    // Method to update loan points for a user
    public void updateLoanPoints(String cardNumber, int newPoints) {
        String query = "UPDATE users SET loan_points = ? WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newPoints);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating loan points: " + e.getMessage());
        }
    }

    // Log loan point accumulation after each transaction
    public void addLoanPoints(String cardNumber, int pointsToAdd) {
        int currentPoints = getLoanPoints(cardNumber);
        updateLoanPoints(cardNumber, currentPoints + pointsToAdd);
    }

    // 2. Transaction History Management
    // Function to log transactions in the database
    public void logTransaction(String cardNumber, String transactionType, double amount) {
        String query = "INSERT INTO transaction_history (card_number, transaction_type, amount) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            pstmt.setString(2, transactionType);
            pstmt.setDouble(3, amount);

            pstmt.executeUpdate();
            System.out.println("Transaction logged successfully.");
        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    // Function to fetch transaction history for a specific card number
    public ResultSet getTransactionHistory(String cardNumber) {
        String query = "SELECT transaction_type, amount, transaction_date FROM transaction_history WHERE card_number = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            return pstmt.executeQuery();  // Return the ResultSet for further processing
        } catch (SQLException e) {
            System.out.println("Error fetching transaction history: " + e.getMessage());
        }
        return null;  // Return null in case of an error
    }
    

    // Get cryptocurrency balance for Bitcoin
    public double getBitcoinBalance(String cardNumber) {
        String query = "SELECT bitcoin_balance FROM users WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("bitcoin_balance");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Bitcoin balance: " + e.getMessage());
        }
        return 0;
    }

    // Get cryptocurrency balance for Ethereum
    public double getEthereumBalance(String cardNumber) {
        String query = "SELECT ethereum_balance FROM users WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("ethereum_balance");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Ethereum balance: " + e.getMessage());
        }
        return 0;
    }

    // Update Bitcoin balance
    public void updateBitcoinBalance(String cardNumber, double newBalance) {
        String query = "UPDATE users SET bitcoin_balance = ? WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Bitcoin balance: " + e.getMessage());
        }
    }

    // Update Ethereum balance
    public void updateEthereumBalance(String cardNumber, double newBalance) {
        String query = "UPDATE users SET ethereum_balance = ? WHERE card_number = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating Ethereum balance: " + e.getMessage());
        }
    }
    public boolean verifyUserCredentials(String userId, String password) {
        String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, userId);      // Verifying user_id
            pstmt.setString(2, password);    // Verifying password
    
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Return true if credentials match
    
        } catch (SQLException e) {
            System.out.println("User credentials verification failed: " + e.getMessage());
        }
        return false;
    }
    public void updatePin(String username, String newPin) {
        String query = "UPDATE users SET pin = ? WHERE user_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, newPin);
            pstmt.setString(2, username); // Assuming user_id is the username
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating PIN: " + e.getMessage());
        }
    }
    public boolean verifyUserForOtp(String cardNumber, String userId, String password) {
        String query = "SELECT * FROM users WHERE card_number = ? AND user_id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, userId);      // Verifying user_id
            pstmt.setString(3, password);    // Verifying password
    
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Return true if credentials match
    
        } catch (SQLException e) {
            System.out.println("OTP verification failed: " + e.getMessage());
        }
        return false;
    }
}
