import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class Ninja extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private static String[] projectilePaths;
    private static final double COLLISION_AREA = 16;
    public Ninja(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"Ninja.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.isRotatable = true;
        this.setFireSpeed(450);
        this.setRange(200);
        this.setProjectileSpeed(7);
        this.setProjectileDamage(10);
        projectilePaths = new String[] {
                "src/ProjectileImages/ninjaStar.png"
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
        return false;
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

            Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed, angle, diameter,  currentTarget,false,true, getProjectileImage(0),ProjectileType.NINJASTAR);
            projectiles.add(p);
            setFireTimer();
        }
    }
}
