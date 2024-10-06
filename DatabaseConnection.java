import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/atm_system";  // Use your database URL
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
