import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class SniperMonkey extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private static final double COLLISION_AREA = 25;
    public SniperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"SniperMonkey.png");

        this.setRotatable(true);                // Tower can rotate toward targets
        this.setFireRate(1000);                  // Milliseconds between shots
        this.setRange(800);                     // Radius within which the tower can target balloons
        this.setProjectileSpeed(70);            // Speed of the projectile
        this.setProjectileDamage(4);           // Damage dealt per hit
        this.setCost(300);
        towerType = "Sniper";
        this.setTowerImageSize(TowerImageSize.SNIPERMONKEY); // Size enum specific to DartMonkey

        loadProjectileImages();
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
                diameter, currentTarget, 1, true,
                getProjectileImage(0), ProjectileImageSize.SNIPERBULLET,
                getProjectileDamage()
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
