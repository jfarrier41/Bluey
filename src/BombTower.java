import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: BombTower.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Bomb Tower.
 */
public class BombTower extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private static final double COLLISION_AREA = 30;
    public BombTower(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"BombTower.png");

        // Set default values for BombTower (can be overridden if needed)
        this.isRotatable = true;
        this.setFireSpeed(900);
        this.setRange(250);
        this.setProjectileSpeed(15);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.BOMBTOWER);
        this.setCost(555);
        towerType = "Bomb";
        loadProjectileImages();
    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {

        // Get center of the target balloon
        double targetX = currentTarget.getX() + 27 / 2.0; // Approximate center X
        double targetY = currentTarget.getY() + 33 / 2.0; // Approximate center Y

        // Get center of the DartMonkey tower image
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        // Calculate angle (in radians) from tower to balloon
        double angleRadians = Math.atan2(targetY - y, targetX - x);

        // Create a new projectile with computed parameters
        Projectile p = new Projectile(
                x, y, COLLISION_AREA, projectileSpeed, angleRadians,
                diameter, currentTarget, 1, false,
                getProjectileImage(4), ProjectileImageSize.BOMB,getProjectileDamage(),
                targets
        );

        // Add projectile to active projectile list
        projectiles.add(p);

        // Begin fire cooldown
        setFireTimer();
    }

    public ArrayList<Balloon> getTargets(){
        return targets;
    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets) {

    }
}
