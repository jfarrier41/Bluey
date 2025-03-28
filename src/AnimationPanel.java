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
    Boolean gameRunning = true;


    public AnimationPanel() {
        setOpaque(false);
        startAnimationLoop();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // If tower is assigned and selected
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

                AffineTransform orginalTransform = g2d.getTransform();

                g2d.rotate(Math.toRadians(angle), tower.xPosition + 25, tower.yPosition + 25);
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 50, 50, this);
                g2d.setTransform(orginalTransform);

                tower.projectileActive = true;

                if(tower.isProjectileActive()){
                    double projectileAngle = tower.projectileAngle(x, y);

                    tower.projX += (int) (Math.cos(projectileAngle) * tower.projectileSpeed);
                    tower.projY += (int) (Math.sin(projectileAngle) * tower.projectileSpeed);

                    g2d.drawImage(tower.projectileImage, (int) tower.projX, (int) tower.projY, 10, 10, this);

                    // Check if it reached the target
                    if (Math.abs(tower.projX - x) < 5 && Math.abs(tower.projY - y) < 5) {
                        tower.projectileActive = false; // Reset for next shot
                    }
                }

            }else{
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 50, 50, this);
            }

        }

        g2d.dispose();
    }

    private void startAnimationLoop() {
        new Timer(30, e -> repaint()).start();
    }

    public List<Tower> getTowerList() {
        return placedTowers;
    }
}
