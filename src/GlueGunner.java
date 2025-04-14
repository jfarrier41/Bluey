import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class GlueGunner extends Tower {
    // Constant for the collision area radius of the projectile
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor that initializes a GlueGunner tower.
     *
     * @param runGame The JFrame for the running game, used for GUI updates.
     * @param currentMap The current game map, used for rendering the tower.
     */
    public GlueGunner(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "GlueGunner.png");

        // Set default values for the GlueGunner tower (can be overridden if needed)
        this.setRotatable(true);                // The tower can rotate toward targets
        this.setFireRate(500);                  // Milliseconds between shots
        this.setRange(255);                     // Radius within which the tower can target balloons
        this.setProjectileSpeed(23);            // Speed of the projectile
        this.setProjectileDamage(1);           // Damage dealt per hit
        this.setTowerImageSize(TowerImageSize.GLUEGUNNER); // Specific size for the GlueGunner tower image
        this.setCost(365);
        towerType = "GlueGunner";

        loadProjectileImages();
    }

    /**
     * Initiates the tower's attack sequence.
     *
     * @return true if the attack is successfully initiated, otherwise false.
     */
    @Override
    public boolean attack() {
        projectileActive = true; // Mark the projectile as active
        return true;
    }

    /**
     * Checks if the projectile is active.
     *
     * @return false, as the GlueGunner does not currently manage projectile activity in this implementation.
     */
    @Override
    public boolean isProjectileActive() {
        return false; // Currently, no projectile activity is tracked in this method
    }

    /**
     * Sets the projectile's active state.
     *
     * @param projectileActive The active state of the projectile (not used in this class).
     */
    @Override
    public void setProjectileActive(boolean projectileActive) {
        // Method body left empty intentionally as projectile activity is not managed in this class
    }

    /**
     * Fires a projectile toward the specified balloon target.
     *
     * @param currentTarget The balloon that the GlueGunner will target.
     * @param projectiles   The list of active projectiles to which the new projectile will be added.
     */
    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        double targetX = currentTarget.getX() + 27 / 2.0; // Approximate center X of the target balloon
        double targetY = currentTarget.getY() + 33 / 2.0; // Approximate center Y of the target balloon

        // Get the center of the GlueGunner tower image
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        // Calculate the angle (in radians) from the tower to the target balloon
        double angleRadians = Math.atan2(targetY - y, targetX - x);

        // Create a new projectile with the calculated parameters
        Projectile p = new Projectile(
                x, y, COLLISION_AREA, projectileSpeed, angleRadians,
                diameter, currentTarget, 1, false,
                getProjectileImage(1), ProjectileImageSize.GOO,getProjectileDamage()
        );

        // Add the created projectile to the active projectiles list
        projectiles.add(p);

        // Start the fire cooldown timer
        setFireTimer();
    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets) {

    }
}
