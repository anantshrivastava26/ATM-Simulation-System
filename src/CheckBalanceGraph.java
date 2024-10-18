import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Second;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CheckBalanceGraph extends JFrame {

    public CheckBalanceGraph(String cardNumber, DatabaseConnection db) {
        setTitle("Balance Over Time");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Fetch transaction history
        ResultSet rs = db.getTransactionHistory(cardNumber);
        
        // Create a time series for the balance
        TimeSeries balanceSeries = new TimeSeries("Balance");

        double currentBalance = 0;

        try {
            while (rs != null && rs.next()) {
                String transactionType = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                Date transactionDate = rs.getTimestamp("transaction_date");

                // Update the current balance based on the transaction type
                if ("deposit".equalsIgnoreCase(transactionType)) {
                    currentBalance += amount;
                } else if ("withdrawal".equalsIgnoreCase(transactionType)) {
                    currentBalance -= amount;
                }

                // Add the current balance to the time series
                balanceSeries.add(new Second(transactionDate), currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a dataset from the time series
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(balanceSeries);

        // Create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Balance Over Time",
                "Time",
                "Balance",
                dataset, // Use the dataset here
                true, // legend
                true, // tooltips
                false // URLs
        );

        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
    }
}
