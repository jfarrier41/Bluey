import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class Wizard extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private static final double COLLISION_AREA = 25;
    public Wizard(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"Wizard.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireSpeed(800);
        this.setRange(400);
        this.setProjectileSpeed(8);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.WIZARD);
        towerType = "Wizard";
        String[] projectilePaths = new String[]{
                "src/ProjectileImages/energyBall.png",
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
        return false;
    }

    @Override
    public void setProjectileActive(boolean projectileActive) {

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
                diameter, currentTarget, 4, false,
                getProjectileImage(0), ProjectileImageSize.ORB
        );

        // Add projectile to active projectile list
        projectiles.add(p);

        // Begin fire cooldown
        setFireTimer();
    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets) {

    }
}
