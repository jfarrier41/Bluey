import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private final JLayeredPane layeredPane;
    private BufferedImage trashImage;
    private List<Tower> placedTowers;

    int x;
    int y;

    public TowerPanel(JLayeredPane pane, List<Tower> placedTowers, GameRunningGUI gameRunningGUI) {
        this.layeredPane = pane;
        this.placedTowers = placedTowers;

        setOpaque(false);

        try {
            trashImage = ImageIO.read(new File("src/DesignImg/trash.png")); // Update with your correct path
        } catch (IOException e) {
            System.err.println("Failed to load trash image.");
            trashImage = null;
        }

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // check to see if tower has been assigned and is placeable
                if ((tower != null) && tower.isPlaceable()) {
                    gameRunningGUI.setCurrentCash(tower.getCost());
                    new SoundEffect("NewTowerIntro.wav", false, .8f);
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
                    x = e.getX() - (tower.getImgWidth() / 2);
                    y = e.getY() - (tower.getImgHeight() / 2);
                    if(x > 865 && y < 210 && y > 170){
                        tower.isSelected = false;
                        tower = null;
                        setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    // Check to see if it is in a placeable area
                    tower.isPlaceable(x + (tower.getImgWidth()/2), y + (tower.getImgHeight()/2));
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //  Check to see if tower has been assigned
        if(tower == null){
            return;
        }

        isTowerThere();
        int diameter = tower.getDiameter();

        if (tower.isSelected) {
            g.drawImage(trashImage, 883, 190, 45, 45, this);

            this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(0, 0),
                    "InvisibleCursor"));
            // Check to see if tower is placeable
            Color color;
            if (tower.isPlaceable()) {
                // If so, make the range color a transparent gray
                color = new Color(128, 128, 128, 128);
                // ChatGPT formula for drawing circle
            } else {
                // If not placeable make the range a transparent red
                color = new Color(225, 0, 0, 128);
            }
            g.setColor(color);
            int xOffset = (diameter / 2) - (tower.getImgWidth() / 2);
            int yOffset = (diameter / 2) - (tower.getImgHeight() / 2);
            g.fillOval(x - xOffset, y - yOffset, diameter, diameter);
            // Always draw the tower and center it in the range
            Image towerImage = tower.towerImage;
            g.drawImage(towerImage, x, y,tower.getImgWidth(),tower.getImgHeight(), this);

        }
    }

    private void isTowerThere() {
        for (Tower placedTower : placedTowers) {
            Point point = placedTower.getPosition();
            System.out.println(point);
            int xDif = Math.abs(x - point.x);
            int yDif = Math.abs(y - point.y);
            if(xDif < 20 && yDif < 20){
                tower.placeable = false;
                break;
            }
        }
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public boolean isTowerSelected(){
        if(tower!=null){
            return true;
        }
        return false;
    }
}