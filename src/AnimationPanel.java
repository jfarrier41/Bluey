import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class AnimationPanel extends JPanel {
    protected List<Tower> placedTowers = new ArrayList<>();
    int x;
    int y;

    public AnimationPanel() {
        setOpaque(false);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Update the target location (x, y)
                x = e.getX();
                y = e.getY();
            }
        });
    }

    public void addTower(Tower tower) {
        placedTowers.add(tower);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        for (Tower tower : placedTowers) {
            if (tower.inRange(x, y)) {
                Double angle = tower.getAngle(x, y);

                AffineTransform originalTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), tower.xPosition + 25, tower.yPosition + 25);
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
                g2d.setTransform(originalTransform);

                tower.fire(x, y);

                if (tower.isProjectileActive()) {
                    double projectileAngle = tower.projectileAngle(x, y);

                    tower.projX += (int) (Math.cos(projectileAngle) * tower.projectileSpeed);
                    tower.projY += (int) (Math.sin(projectileAngle) * tower.projectileSpeed);

                    AffineTransform projectileTransform = g2d.getTransform();
                    g2d.rotate(Math.toRadians(angle), tower.projX + 5, tower.projY + 5);
                    g2d.drawImage(tower.projectileImage, (int) tower.projX, (int) tower.projY, 15, 30, this);
                    g2d.setTransform(projectileTransform);

                    // Check if it reached the target
                    if (Math.abs(tower.projX - x) < 5 && Math.abs(tower.projY - y) < 5) {
                        System.out.println("Second Tower Has Been Hit");
                        tower.projectileActive = false; // Reset for next shot
                    }
                }
            } else {
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
            }
        }

        g2d.dispose();
    }

    public List<Tower> getTowerList() {
        return placedTowers;
    }
}
