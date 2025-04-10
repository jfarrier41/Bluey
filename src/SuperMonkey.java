import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Filename: SuperMonkey.java
 * Authors: Jace Claassen and Joseph Farrier
 *
 * Description: Implements the Super Monkey tower and defines its behavior.
 * Inherits from the Tower class and sets projectile, range, and visual parameters.
 */
public class SuperMonkey extends Tower {

    /** Time of the last fire event, used to control fire rate. */
    private long lastFireTime;

    /** Collision area radius for the projectile. */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor for the SuperMonkey tower.
     *
     * @param runGame    The parent JFrame running the game.
     * @param currentMap The map image used for collision/placement reference.
     */
    public SuperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "SuperMonkey.png");

        /** Set whether this tower should rotate to face targets. */
        this.setRotatable(true);

        /** Set the fire rate in milliseconds. */
        this.setFireRate(50);

        /** Set the detection range for targeting balloons. */
        this.setRange(500);

        /** Set the speed of fired projectiles. */
        this.setProjectileSpeed(20);

        /** Set the damage dealt by projectiles. */
        this.setProjectileDamage(10);

        /** Set the visual size used for the SuperMonkey tower image. */
        this.setTowerImageSize(TowerImageSize.SUPERMONKEY);

        /** Paths for dart and explosion images used in projectile animation. */
        projectilePaths = new String[] {
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/explosion.png",
        };
        loadProjectileImages(projectilePaths);
    }

    /**
     * Initiates the attack behavior for the SuperMonkey.
     *
     * @return Always returns true once attack is activated.
     */
    @Override
    public boolean attack() {
        projectileActive = true;
        return true;
    }

    /**
     * Checks if a projectile is currently active.
     *
     * @return true if active, false otherwise.
     */
    @Override
    public boolean isProjectileActive() {
        return projectileActive;
    }

    /**
     * Sets the projectile active state.
     *
     * @param projectileActive Whether the projectile is active or not.
     *                         (Note: no logic currently defined)
     */
    @Override
    public void setProjectileActive(boolean projectileActive) {
        // Intentionally left empty
    }

    /**
     * Fires a dart projectile toward a given balloon.
     * Calculates the angle and creates a projectile aimed at the balloon.
     *
     * @param currentTarget The Balloon to target.
     * @param projectiles   The list of projectiles in play (to add the new one).
     */
    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        /** Compute target center coordinates (adjusted). */
        double targetX = currentTarget.getX() + 13;
        double targetY = currentTarget.getY();

        /** Compute tower center coordinates for firing origin. */
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        /** Calculate direction angle from tower to target. */
        double angleRadians = Math.atan2(targetY - y, targetX - x);

        /** Create new projectile instance and add to list. */
        Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed,
                angleRadians, diameter, currentTarget, 1, false,
                getProjectileImage(0), ProjectileImageSize.DART);
        projectiles.add(p);

        /** Start cooldown before next fire. */
        setFireTimer();
    }
}
