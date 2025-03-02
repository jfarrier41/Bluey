import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TowerTestGUI extends JFrame {
    private Tower tower;
    private BufferedImage currentMap;
    private int xPosition;
    private int yPosition;

    public TowerTestGUI() {
        // Setup JFrame properties
        setTitle("Tower Placement Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Create a sample map (Green and Brown areas for testing)
        currentMap = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = currentMap.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, 400, 600);  // Left half green (valid placement)
        g2d.setColor(new Color(139, 69, 19)); // Brown color
        g2d.fillRect(400, 0, 400, 600);  // Right half brown (invalid placement)
        g2d.dispose();

        // Create and add TowerPanel to JFrame
        tower = new DartMonkey(this, 5, 50, 10, 20, "src/TowerImages/dart monkey.png"); // Example DartMonkey tower
        System.out.println("Loading image from: " + new File("src/TowerImages/dart monkey.png").getAbsolutePath());
        TowerPanel towerPanel = new TowerPanel(tower, currentMap);
        add(towerPanel);
        tower.currentMap = currentMap;

        // Make the JFrame visible
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TowerTestGUI());
    }
}
