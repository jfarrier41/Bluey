import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/*
* Joseph Farrier
* 3/16/2025
* Description: This class is used to handle the drawing and animations for the Tower Class
* This class works in connection with both the GUI and the Tower Class. Using mouse listerns
* to determine if the user is ready to place a tower, and track where the user is tracing.
* It also uses a list to keep track of all towers place and draw them to the screen
* */
public class TowerPanel extends JPanel {
    private Tower tower;
    private BufferedImage currentMap;
    private JLayeredPane layeredPane;

    private List<Tower> placedTowers = new ArrayList<Tower>();

    int x;
    int y;

    public TowerPanel(JLayeredPane pane, BufferedImage map) {
        this.currentMap = map;
        this.layeredPane = pane;
        setOpaque(false);


        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               // check to see if tower has been assigned and is placeable
                if ((tower != null) && tower.getValid()) {
                    // Set tower postion
                    tower.setPosition(x,y);
                    // Add it to list of placed towers
                    placedTowers.add(tower);
                    // remove current tower from Panel
                    tower = null;
                    // Set the layer back to the Pallete Layer
                    layeredPane.setLayer(TowerPanel.this, JLayeredPane.PALETTE_LAYER);
                    repaint();

                }
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // If tower is assigned and selected
                if (tower != null && tower.isSelected) {
                    // Get where the mouse is currently at
                    x = e.getX();
                    y = e.getY();
                    // Check to see if it is in a placeable area
                    tower.isPlaceable(x, y);
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw all Placed Towers first
        for(Tower tower :placedTowers){
            g.drawImage(tower.towerImage, tower.xPosition,tower.yPosition, 50, 50, this);
        }
        //  Check to see if tower has been assigned
        if(tower == null){
            return;
        }
        int diameter = tower.getDiameter();

        if (tower.isSelected) {
            // Remove the cursor from screen, Credit to ChatGPT for this Code
            this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(0, 0),
                    "InvisibleCursor"));
            // Check to see if tower is placeable
            if (tower.getValid()) {
                // If so, make the range color a transparent gray
                Color color = new Color(128, 128, 128, 128);
                g.setColor(color);
                // ChatGPT formula for drawing circle
                g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            } else {
                // If not placeable make the range a transparent red
                Color color = new Color(255, 0, 0, 128);
                g.setColor(color);
                g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            }
            // Always draw the tower and center it in the range
            Image towerImage = tower.towerImage;
            g.drawImage(towerImage, x-24, y-18,50,50, this);
        }
    }
    // Assign tower to JPanel
    public void setTower(Tower newTower) {
        this.tower = newTower;
    }

}