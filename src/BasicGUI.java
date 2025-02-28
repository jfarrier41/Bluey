import javax.swing.*;
import java.awt.*;

public class BasicGUI extends JFrame {

    public BasicGUI() {
        setTitle("Basic GUI");
        setSize(500, 500); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        add(new ColorPanel()); // Add custom panel
    }

    // Custom panel to draw the green/brown split
    private static class ColorPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            // Left half - Green
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, width / 2, height);

            // Right half - Brown
            g.setColor(new Color(139, 69, 19)); // Brown color
            g.fillRect(width / 2, 0, width / 2, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BasicGUI().setVisible(true));
    }
}