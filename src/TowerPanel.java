import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Joseph Farrier
 * @author Jace Claassen
 * TowerPanel handles the visual representation and user interaction
 * for placing towers within the game.
 * It listens for mouse movement and clicks to determine if a tower is being placed
 * and draws the tower image and its range circle accordingly. It also shows a
 * trash icon if the player decides to cancel placement.
 */
public class TowerPanel extends JPanel {

    /**
     * Reference to the layered pane used for drawing towers on top of other UI elements
     */
    private final JLayeredPane layeredPane;
    /**
     * Mouse x and y coordinates used to position the tower image
     */
    int x;
    int y;
    /**
     * The current tower being placed by the player
     */
    private Tower tower;
    /**
     * Image used to cancel tower placement
     */
    private BufferedImage trashImage;
    private List<Tower> placedTowers;

    /**
     * Constructs the TowerPanel.
     *
     * @param pane           the layered pane that manages z-order of components
     * @param placedTowers   the list of already placed towers
     * @param gameRunningGUI the main game GUI for cash updates and state changes
     */
    public TowerPanel(JLayeredPane pane, List<Tower> placedTowers, GameRunningGUI gameRunningGUI) {
        this.layeredPane = pane;
        this.placedTowers = placedTowers;
        setOpaque(false);

        try {
            trashImage = ImageIO.read(new File("src/DesignImg/trash.png"));
        } catch (IOException e) {
            System.err.println("Failed to load trash image.");
            trashImage = null;
        }

        /** Handless mouse clicks during placement*/
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if ((tower != null) && tower.isPlaceable()) {
                    gameRunningGUI.setCurrentCash(tower.getCost());
                    new SoundEffect("NewTowerIntro.wav", false, 0.8f);

                    tower.setPosition(x, y);
                    placedTowers.add(tower);
                    tower = null;

                    layeredPane.setLayer(TowerPanel.this, JLayeredPane.PALETTE_LAYER);
                    repaint();
                }
            }
        });

        /** Tracks Mouse movement when tower selected */
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (tower != null && tower.isSelected) {
                    x = e.getX() - (tower.getImgWidth() / 2);
                    y = e.getY() - (tower.getImgHeight() / 2);

                    // Trash can area detection
                    if (x > 865 && y < 210 && y > 170) {
                        tower.isSelected = false;
                        tower = null;
                        setCursor(Cursor.getDefaultCursor());
                        return;
                    }

                    tower.isPlaceable(x + (tower.getImgWidth() / 2), y + (tower.getImgHeight() / 2));
                    repaint();
                }
            }
        });
    }

    /**
     * Draws the tower being placed, its range, and the trash icon if selected.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tower == null) return;
        /** Check for tower overlap*/
        isTowerThere();

        int diameter = tower.getRange();
        if (tower.isSelected) {
            g.drawImage(trashImage, 883, 190, 45, 45, this);
            /** Syntax for removing mouse visual provided by CHATGPT*/
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(0, 0),
                    "InvisibleCursor"));

            Color color = tower.isPlaceable() ?
                    /** If Tower placeable show transparent gray */
                    new Color(128, 128, 128, 128) :
                    /** If Tower not placeable show transparent red */
                    new Color(225, 0, 0, 128);

            g.setColor(color);
            /** Math to draw proper circle around tower provided by CHATGPT*/
            int xOffset = (diameter / 2) - (tower.getImgWidth() / 2);
            int yOffset = (diameter / 2) - (tower.getImgHeight() / 2);
            g.fillOval(x - xOffset, y - yOffset, diameter, diameter);

            g.drawImage(tower.towerImage, x, y, tower.getImgWidth(), tower.getImgHeight(), this);
        }
    }

    /**
     * Prevents overlapping towers by checking nearby tower positions.
     */
    private void isTowerThere() {
        for (Tower placedTower : placedTowers) {
            Point point = placedTower.getPosition();
            int xDif = Math.abs(x - point.x);
            int yDif = Math.abs(y - point.y);
            if (xDif < 20 && yDif < 20) {
                tower.placeable = false;
                break;
            }
        }
    }

    /**
     * Assigns a new tower to be placed by the player.
     *
     * @param tower the tower being placed
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    /**
     * Returns whether the player is currently placing a tower.
     *
     * @return true if a tower is selected for placement
     */
    public boolean isTowerSelected() {
        return tower != null;
    }
}
