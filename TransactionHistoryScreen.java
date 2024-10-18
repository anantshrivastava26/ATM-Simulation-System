import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TransactionHistoryScreen extends JFrame {

    public TransactionHistoryScreen(String cardNumber, DatabaseConnection db) {
        setTitle("Transaction History");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch transaction history
        ResultSet rs = db.getTransactionHistory(cardNumber);

        // Prepare data for the table
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Transaction Type");
        columnNames.add("Amount");
        columnNames.add("Date");

        try {
            while (rs != null && rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("transaction_type"));
                row.add(rs.getDouble("amount"));
                row.add(rs.getTimestamp("transaction_date"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create table with data
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
