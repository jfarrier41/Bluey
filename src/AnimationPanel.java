import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationPanel extends JPanel {
    protected List<Tower> placedTowers = new ArrayList<>();
    protected ArrayList<Balloon> balloons = new ArrayList<>();
    protected ArrayList<Projectile> curProjectiles = new ArrayList<>();
    int targetX;
    int targetY;
    Balloon currentTarget;

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
            tower.targets.removeIf(balloon -> !tower.inRange(balloon));
            Iterator<Balloon> it = balloons.iterator();
            while (it.hasNext()) {
                Balloon balloon = it.next();
                if (tower.inRange(balloon)) {
                    tower.addTarget(balloon);
                }
            }
            if (!tower.targets.isEmpty()) {
                Balloon currentTarget = tower.target();
                this.targetX = currentTarget.getX();
                this.targetY = currentTarget.getY();


                Double angle = tower.getAngle(targetX, targetY);

                AffineTransform originalTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), tower.xPosition + 25, tower.yPosition + 25);
                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
                g2d.setTransform(originalTransform);

                if (tower.isReadyToFire()) {
                    tower.fire(currentTarget); // Fires if the tower is ready
                }
                curProjectiles = tower.projectiles;
                Iterator<Projectile> iterator = curProjectiles.iterator();
                while (iterator.hasNext()) {
                    Projectile p = iterator.next();
                    p.update();

                    AffineTransform projectileTransform = g2d.getTransform();
                    g2d.rotate(Math.toRadians(angle - 90), p.projX + 5, p.projY + 5);
                    g2d.drawImage(p.getImage(), (int) p.projX, (int) p.projY, 30, 15, this);
                    g2d.setTransform(projectileTransform);

                    // Check if it reached the target
                    if (Math.abs(p.projX - targetX) < 5 && Math.abs(p.projY - targetY) < 5) {
                        balloons.remove(currentTarget);
                        tower.removeTarget(currentTarget);
                        iterator.remove();
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
