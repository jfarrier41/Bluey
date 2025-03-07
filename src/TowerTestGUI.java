import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TowerTestGUI extends JFrame {
    private Tower tower;
    private BufferedImage currentMap;
    private TowerPanel towerPanel;
    private int xPosition;
    private int yPosition;

    public TowerTestGUI() {
        // Setup JFrame properties
        setTitle("Tower Placement Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(null);

        // Create a sample map (Green and Brown areas for testing)

       JPanel buttonsPanel = new JPanel();
       buttonsPanel.setLayout(null);
       buttonsPanel.setBounds(600, 0, 200, 600);
       buttonsPanel.setBackground(Color.BLACK);
       add(buttonsPanel);


        currentMap = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = currentMap.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, 400, 600);  // Left half green (valid placement)
        g2d.setColor(new Color(139, 69, 19)); // Brown color
        g2d.fillRect(400, 0, 400, 600);  // Right half brown (invalid placement)
        g2d.dispose();

        ImageIcon dartMonkeyIcon = new ImageIcon("src/TowerImages/dart monkey.png");
        JButton dartMonkeyButton = new JButton(dartMonkeyIcon);
        dartMonkeyButton.setIcon(dartMonkeyIcon);
        dartMonkeyButton.setBounds(600, 50, 100, 100);

        dartMonkeyButton.addActionListener(e -> {
            Tower tower = new DartMonkey(this, 5, 50, 10, 20, "src/TowerImages/dart monkey.png"); // Example DartMonkey tower
            tower.currentMap = currentMap;
            tower.isSelected = true;
            System.out.println(tower.isSelected);
            if(towerPanel != null){
                remove(towerPanel);
            }
            TowerPanel towerPanel = new TowerPanel(tower, currentMap);
            towerPanel.setBounds(0, 0, 600, 800);
            add(towerPanel);

            revalidate();
            repaint();



        });


        add(dartMonkeyButton);
        // Create and add TowerPanel to JFrame



        // Make the JFrame visible
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TowerTestGUI());
    }
}
