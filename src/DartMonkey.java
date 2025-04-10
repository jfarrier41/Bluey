import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Filename: DartMonkey.java
 * Authors: Jace Claassen and Joseph Farrier
 * Description:
 * This class represents a Dart Monkey tower in the tower defense game.
 * It extends the abstract Tower class and implements behavior specific to the Dart Monkey,
 * including projectile behavior, image settings, and attack logic.
 */
public class DartMonkey extends Tower {

    /** Collision area of the dart projectile (radius in pixels). */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor for DartMonkey.
     *
     * @param runGame    The JFrame that runs the game, used for context or parent references.
     * @param currentMap The BufferedImage representing the current game map.
     */
    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "DartMonkey.png");

        // Set default values for DartMonkey (can be adjusted in subclass or later)
        this.setRotatable(true);                // Tower can rotate toward targets
        this.setFireRate(500);                  // Milliseconds between shots
        this.setRange(200);                     // Radius within which the tower can target balloons
        this.setProjectileSpeed(23);            // Speed of the projectile
        this.setProjectileDamage(10);           // Damage dealt per hit
        this.setTowerImageSize(TowerImageSize.DARTMONKEY); // Size enum specific to DartMonkey
        towerType = "DartMonkey";

        // Load dart and explosion projectile images
        String[] projectilePaths = new String[]{
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/explosion.png",
        };
        loadProjectileImages(projectilePaths);
    }

    /**
     * Triggers an attack by the DartMonkey.
     *
     * @return Always returns true when attack is initiated.
     */
    @Override
    public boolean attack() {
        projectileActive = true;
        return true;
    }

    /**
     * Returns whether the DartMonkey currently has an active projectile in play.
     *
     * @return true if the projectile is currently active; otherwise, false.
     */
    @Override
    public boolean isProjectileActive() {
        return projectileActive;
    }

    /**
     * Sets the projectile active state.
     *
     * @param projectileActive Boolean value indicating whether a projectile is active.
     *                         (Note: Method intentionally left empty; may be implemented later.)
     */
    @Override
    public void setProjectileActive(boolean projectileActive) {
        // No internal logic provided — may be managed elsewhere
    }

    /**
     * Fires a dart projectile toward the specified target.
     * Calculates trajectory and creates a new projectile, adding it to the projectile list.
     *
     * @param currentTarget The Balloon object currently targeted by the tower.
     * @param projectiles   The list to which the new projectile will be added.
     */
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
                getProjectileImage(0), ProjectileImageSize.DART
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
