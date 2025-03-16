import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
                if ((tower != null) && tower.isPlaceable(x, y)) {
                    tower.setPosition(x,y);
                    placedTowers.add(tower);
                    tower = null;
                    layeredPane.setLayer(TowerPanel.this, JLayeredPane.PALETTE_LAYER);
                    repaint();

                }
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (tower != null && tower.isSelected) {
                    x = e.getX();
                    y = e.getY();
                    tower.isPlaceable(x, y);
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Tower tower :placedTowers){
            g.drawImage(tower.towerImage, tower.xPosition,tower.yPosition, 50, 50, this);
        }
        if(tower == null){
            return;
        }
        int diameter = tower.getDiameter();
        //g.drawImage(currentMap, 0, 0, getWidth(), getHeight(), this);
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
            g.drawImage(towerImage, x-24, y-18,50,50, this);
//            g.setColor(Color.BLUE);
//            g.drawLine(x - 5, y, x + 5, y); // Horizontal line
//            g.drawLine(x, y - 5, x, y + 5);
        }
    }
    public void setTower(Tower newTower) {
        this.tower = newTower;
    }

}