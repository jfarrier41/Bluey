import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TowerTestGUI extends JFrame {
    private Tower tower;
    private BufferedImage currentMap;
    private TowerPanel towerPanel;
    private MapPanel mapPanel;

    public TowerTestGUI() {
        // Setup JFrame properties
        setTitle("Tower Placement Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(null);

        // Load the map image FIRST
        currentMap = loadImage("src/MapImg/Maze.png");

        // Create map panel AFTER the image is loaded
        mapPanel = new MapPanel(currentMap);
        mapPanel.setBounds(0, 0, 600, 600);
        add(mapPanel);

        // Create button panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout()); // Ensures button placement
        buttonsPanel.setBounds(600, 0, 200, 600);
        buttonsPanel.setBackground(Color.BLACK);
        add(buttonsPanel);

        // Create button for placing a Dart Monkey
        ImageIcon dartMonkeyIcon = new ImageIcon("src/TowerImages/DartMonkey.png");
        JButton dartMonkeyButton = new JButton(dartMonkeyIcon);
        dartMonkeyButton.setPreferredSize(new Dimension(100, 100)); // Explicitly set button size

        dartMonkeyButton.addActionListener(e -> {
            Tower tower = new DartMonkey(this, 5, 50, 10, 20, "src/TowerImages/DartMonkey.png");
            tower.currentMap = currentMap;
            tower.isSelected = true;

            System.out.println("Tower Selected: " + tower.isSelected);

            if (towerPanel != null) {
                remove(towerPanel);
            }

            // Create and add TowerPanel OVER the map
            towerPanel = new TowerPanel(tower, currentMap);
            towerPanel.setBounds(0, 0, 600, 600);
            add(towerPanel);
            towerPanel.repaint();

            revalidate();
            repaint();
        });

        buttonsPanel.add(dartMonkeyButton); // ADD BUTTON TO PANEL

        setVisible(true);
    }

    private static class MapPanel extends JPanel {
        private BufferedImage mapImage;

        public MapPanel(BufferedImage mapImage) {
            this.mapImage = mapImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mapImage != null) {
                g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, getWidth(), getHeight()); // Fallback if image fails to load
            }
        }
    }

    // Method to load an image
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TowerTestGUI::new);
    }
}