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

    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"DartMonkey.png");

        // Set default values for SuperMonkey (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(200);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
        this.setProjectileImage("TowerImages/dart.png");
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
    public void fire(int targetX, int targetY) {
        if (!projectileActive) { // Only fire if no active projectile
            double angleRadians = Math.toRadians(getAngle(targetX, targetY));

            // Offset distance from monkey's center to right hand (tweak as needed)
            double spawnDistance = 26;

            // Calculate spawn position based on monkey rotation
            double offsetX = Math.cos(angleRadians) * spawnDistance;
            double offsetY = Math.sin(angleRadians) * spawnDistance;

            // Set projectile's starting position relative to monkey
            this.projX = this.xPosition + (this.towerImage.getWidth(null) / 2) + offsetX;
            this.projY = this.yPosition + (this.towerImage.getHeight(null) / 2) + offsetY;

            // Activate the projectile
            this.projectileActive = true;
            System.out.println("Projectile activated");
        }
    }
}
