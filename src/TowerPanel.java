import javax.swing.*;
import java.awt.*;
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


        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                x = e.getX() - 4;
                y = e.getY() - 23;
                tower.isPlaceable(x, y);
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image towerImage = new ImageIcon().getImage();
        int diameter = tower.getDiameter();

        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(0, 0),
                "InvisibleCursor"));



        if(tower.getValid()) {
            Color color = new Color(128, 128, 128, 128);
            g.setColor(color);
            g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            g.drawImage(towerImage,x,y,50,50,this);
        } else {
            Color color = new Color(255, 0, 0, 128);
            g.setColor(color);
            g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            g.drawImage(towerImage,x,y,50,50,this);
        }

    }

}
