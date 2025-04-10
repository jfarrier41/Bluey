import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: SuperMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Super Monkey.
 */

public class SuperMonkey extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private long lastFireTime;
    private static final double COLLISION_AREA = 25;

    public SuperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "SuperMonkey.png");

        // Time of the last fire event

        // Set default values for SuperMonkey (can be overridden if needed)
        this.setRotatable(true);
        this.setFireRate(50);
        this.setRange(500);
        this.setProjectileSpeed(20);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.SUPERMONKEY);
        projectilePaths = new String[] {
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/explosion.png",
        };
        loadProjectileImages(projectilePaths);
    }

    @Override
    public boolean attack() {
        projectileActive = true;
        return true;
    }

    @Override
    public boolean isProjectileActive() {
        return projectileActive;
    }

    @Override
    public void setProjectileActive(boolean projectileActive) {

    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        // Get center of the target
        double targetX = currentTarget.getX() + 13;  // adjust for balloon center if needed
        double targetY = currentTarget.getY();

        // Get center of the tower
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        // Correct angle from tower to target
        double angleRadians = Math.atan2(targetY - y, targetX - x);

        Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed,
                angleRadians, diameter,  currentTarget,1,false
                , getProjectileImage(0), ProjectileImageSize.DART);
        projectiles.add(p);
        setFireTimer();

    }
}
