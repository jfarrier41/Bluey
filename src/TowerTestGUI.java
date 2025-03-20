import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/*
public class TowerTestGUI extends JFrame {
    private BufferedImage currentMap;
    private TowerPanel towerPanel;
    private JLayeredPane layeredPane;


    public TowerTestGUI() {
        setTitle("Tower Placement Test");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Load the map image
        BufferedImage orginalMap = loadImage("src/MapImg/MonkeyLane.png");
        int guiWidth = orginalMap.getWidth();
        int guiHeight = orginalMap.getHeight();

        currentMap = new BufferedImage(guiWidth,guiHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = currentMap.createGraphics();
        g.drawImage(orginalMap, 0, 0, guiWidth, guiHeight, null);
        g.dispose();

        // Create layered pane for layering elements
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);
        add(layeredPane);

        // Create and add map panel (background layer)
        JPanel mapPanel = new MapPanel(currentMap);
        mapPanel.setBounds(0, 0, 700, 510);
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        // Create the Tower Panel (overlay for tower placement)
        towerPanel = new TowerPanel(layeredPane, currentMap);
        towerPanel.setBounds(0, 0, 700, 510);
        towerPanel.setOpaque(false); // Ensure transparency
        layeredPane.add(towerPanel, JLayeredPane.PALETTE_LAYER);

        // Buttons Panel (doesn't change)
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        buttonsPanel.setBounds(700, 0, 200, 600);
        buttonsPanel.setBackground(Color.BLACK);
        layeredPane.add(buttonsPanel,JLayeredPane.POPUP_LAYER);

        // Add Tower selection button
        ImageIcon dartMonkeyIcon = new ImageIcon("src/TowerImages/DartMonkey.png");
        JButton dartMonkeyButton = new JButton(dartMonkeyIcon);
        dartMonkeyButton.setBounds(50, 50, 100, 100);

        dartMonkeyButton.addActionListener(e -> {
            Tower tower = new DartMonkey(this, 5, 100, 10, 20, "src/TowerImages/DartMonkey.png");
            tower.currentMap = currentMap;
            tower.isSelected = true;
            towerPanel.setTower(tower);
            layeredPane.setLayer(towerPanel, JLayeredPane.DRAG_LAYER);
            // Update TowerPanel with new tower
           // towerPanel.repaint();
        });

        buttonsPanel.add(dartMonkeyButton);

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
            }
        }
    }

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
}*/
