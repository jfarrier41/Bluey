
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class DartMonkey extends Tower {
    private long lastFireTime; // Time of the last fire event
    private static final double COLLISION_AREA = 20*20;

    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"DartMonkey.png");

        // Set default values or SuperMonkey (can be overridden if needed)
        this.setRotatable(true);
        this.setFireRate(500);
        this.setRange(200);
        this.setProjectileSpeed(7);
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
            double angle = angleRadians -89.8;

            // Offset distance from monkey's center to right hand (tweak as needed)
            double spawnDistance = 27;

            // Calculate spawn position based on monkey rotation
            double offsetX = Math.cos(angleRadians) * spawnDistance;
            double offsetY = Math.sin(angleRadians) * spawnDistance;

            // Set projectile's starting position relative to monkey
            double x = this.xPosition + (this.towerImage.getWidth(null) / 2) + offsetX;
            double y = this.yPosition + (this.towerImage.getHeight(null) / 2) + offsetY;

            Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed, angle, diameter,  currentTarget,false,false, 0);
            projectiles.add(p);
            setFireTimer();
        }
    }
}
