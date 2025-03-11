import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class TowerPanel extends JPanel {
    private Tower tower;
    private BufferedImage currentMap;

    int x;
    int y;

    public TowerPanel(Tower tower, BufferedImage map) {
        this.tower = tower;
        this.currentMap = map;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tower.isSelected = false;
                tower.setPosition(x, y);
                repaint();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (tower.isSelected) {
                    x = e.getX() - 4;
                    y = e.getY() - 23;
                    tower.isPlaceable(x, y);
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = tower.getDiameter();
        g.drawImage(currentMap,0,0, null);
        if (tower.isSelected) {
            this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(0, 0),
                    "InvisibleCursor"));

            if (tower.getValid()) {
                Color color = new Color(128, 128, 128, 128);
                g.setColor(color);
                g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            } else {
                Color color = new Color(255, 0, 0, 128);
                g.setColor(color);
                g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            }
            Image towerImage = tower.towerImage;
            g.drawImage(towerImage, x - diameter / 2, y - diameter / 2, diameter, diameter, this);
        }
    }

}