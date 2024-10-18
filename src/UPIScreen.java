import javax.swing.*;
import java.awt.*;

public class UPIScreen extends JFrame {

    public UPIScreen() {
        setTitle("UPI Image");
        setSize(400, 700); // Set the size of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Path to the image
        String imagePath = "C:\\Users\\Asus\\Documents\\APP\\Demo\\lib\\GooglePay_QR.jpg";

        // Load the image
        ImageIcon icon = new ImageIcon(imagePath);

        // Resize the image
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(400, 500, Image.SCALE_SMOOTH); // Adjust size here
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        // Display the image in a JLabel
        JLabel imageLabel = new JLabel(resizedIcon);
        add(imageLabel, BorderLayout.CENTER);

        setVisible(true); // Show the frame
    }
}