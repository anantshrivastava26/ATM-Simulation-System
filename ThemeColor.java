import java.awt.Color;
import java.awt.Font;

public class ThemeColor {
    private static Color selectedColor = new Color(0, 51, 102); // Default theme color
    private static Font selectedFont = new Font("Arial", Font.PLAIN, 16); // Default font

    public static Color getSelectedColor() {
        return selectedColor;
    }

    public static void setSelectedColor(Color color) {
        selectedColor = color;
    }

    public static Font getSelectedFont() {
        return selectedFont;
    }

    public static void setSelectedFont(Font font) {
        selectedFont = font;
    }

    // Add a method to get the font color based on the theme color
    public static Color getFontColor() {
        if (selectedColor.equals(new Color(211, 211, 211)) || selectedColor.equals(new Color(173, 216, 230)) || selectedColor.equals(new Color(189, 252, 221))) {
            return Color.BLACK; // Dark text for light background
        } else {
            return Color.WHITE; // Light text for dark background
        }
    }
}
