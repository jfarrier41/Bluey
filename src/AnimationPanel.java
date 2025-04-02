import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class AnimationPanel extends JPanel {
    protected List<Tower> placedTowers = new ArrayList<>();
    protected ArrayList<Balloon> balloons = new ArrayList<>();
    int targetX;
    int targetY;

    public AnimationPanel(ArrayList<Balloon> attackers) {
        setOpaque(false);
        balloons = attackers;
    }

    public void addTower(Tower tower) {
        placedTowers.add(tower);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();


        for (Tower tower : placedTowers) {
            for (Balloon balloon : balloons) {
                if(tower.inRange(balloon)){
                    tower.addTarget(balloon);
                }
            }

            if(!tower.targets.isEmpty()){
                Balloon target = tower.target();
                this.targetX = target.getX()-202;
                this.targetY = target.getY();


                Double angle = tower.getAngle(targetX, targetY);

                AffineTransform originalTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), tower.xPosition + 25, tower.yPosition + 25);
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
                g2d.setTransform(originalTransform);

                tower.fire(targetX, targetY);

                if (tower.isProjectileActive()) {
                    double projectileAngle = tower.projectileAngle(targetX, targetY);

                    tower.projX += (int) (Math.cos(projectileAngle) * tower.projectileSpeed);
                    tower.projY += (int) (Math.sin(projectileAngle) * tower.projectileSpeed);

                    AffineTransform projectileTransform = g2d.getTransform();
                    g2d.rotate(Math.toRadians(angle), tower.projX + 5, tower.projY + 5);
                    g2d.drawImage(tower.projectileImage, (int) tower.projX, (int) tower.projY, 15, 30, this);
                    g2d.setTransform(projectileTransform);

                    // Check if it reached the target
                    if (Math.abs(tower.projX - targetX) < 5 && Math.abs(tower.projY - targetY) < 5) {
                        balloons.remove(target);
                        tower.removeTarget(target);
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
