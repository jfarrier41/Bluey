import javax.swing.*;
import java.awt.image.BufferedImage;

/*
Filename: SuperMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Super Monkey.
 */

public class SuperMonkey extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private long lastFireTime;
    private static final double COLLISION_AREA = 16;

    public SuperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "SuperMonkey.png");

        // Time of the last fire event

        // Set default values for SuperMonkey (can be overridden if needed)
        this.setRotatable(true);
        this.setFireRate(450);
        this.setRange(500);
        this.setProjectileSpeed(15);
        this.setProjectileDamage(10);
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
    public void fire(Balloon currentTarget) {
        if (isReadyToFire()) {
            //System.out.println("What the actual fuck");
            int targetX = currentTarget.getX();
            int targetY = currentTarget.getY();

            double angleRadians = Math.toRadians(getAngle(targetX, targetY));
            double angle = angleRadians -89.63;

            // Offset distance from monkey's center to right hand (tweak as needed)
            double spawnDistance = 27;

            // Calculate spawn position based on monkey rotation
            double offsetX = Math.cos(angleRadians) * spawnDistance;
            double offsetY = Math.sin(angleRadians) * spawnDistance;

            // Set projectile's starting position relative to monkey
            double x = this.xPosition + (this.towerImage.getWidth(null) / 2) + offsetX;
            double y = this.yPosition + (this.towerImage.getHeight(null) / 2) + offsetY;

            Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed, angle, diameter,  currentTarget,false,false, getProjectileImage(0), ProjectileType.DART);
            projectiles.add(p);
            setFireTimer();
        }
    }
}
