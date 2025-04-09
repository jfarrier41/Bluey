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
            Iterator<Balloon> iterator0 = balloons.iterator();
            while (iterator0.hasNext()) {
                Balloon balloon = iterator0.next();
                if (tower.inRange(balloon)) {
                    tower.addTarget(balloon);
                }
            }
            if (!tower.targets.isEmpty()) {
                Balloon currentTarget = tower.target();
                this.targetX = currentTarget.getX();
                this.targetY = currentTarget.getY();

                if(tower.isRotatable) {
                    Double angle = tower.getAngle(targetX, targetY);

                    AffineTransform originalTransform = g2d.getTransform();
                    g2d.rotate(Math.toRadians(angle), tower.xPosition + 25, tower.yPosition + 25);
                    g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
                    g2d.setTransform(originalTransform);
                } else {

                g2d.drawImage(tower.towerImage, tower.xPosition, tower.yPosition, 45, 45, this);
            }

                if (tower.isReadyToFire()) {
                    tower.fire(currentTarget); // Fires if the tower is ready
                }
                curProjectiles = tower.projectiles;
                Iterator<Projectile> iterator1 = curProjectiles.iterator();
                while (iterator1.hasNext()) {
                    Projectile p = iterator1.next();
                    p.update();

                    AffineTransform projectileTransform = g2d.getTransform();
                    g2d.rotate(p.getAngle(), p.projX + 5, p.projY + 5);
                    g2d.drawImage(p.getImage(), (int) p.projX, (int) p.projY, 30, 15, this);
                    g2d.setTransform(projectileTransform);

                    if(p.missed()){
                        System.out.println("Missed!");
                        iterator1.remove();
                        continue;
                    }

                    Iterator<Balloon> iterator2 = tower.targets.iterator();
                    while (iterator2.hasNext()) {
                        Balloon target = iterator2.next();
                        if (p.didHit(target)) {
                            iterator1.remove();
                            balloons.remove(target);
                            iterator2.remove();

                            break;
                        }
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