import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Joseph Farrier
 * @author Jace Classen
 * Description: Implements tower and defines the Dart Monkey.
 */

public class GlueGunner extends Tower {

    /** Collision radius for the Wizard's projectile. */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor that initializes a GlueGunner tower.
     *
     * @param runGame    The JFrame for the running game, used for GUI updates.
     * @param currentMap The current game map, used for rendering the tower.
     */
    public GlueGunner(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "GlueGunner.png");

        // Set default values for the GlueGunner tower (can be overridden if needed)
        this.setRotatable(true);                // The tower can rotate toward targets
        this.setFireRate(800);                  // Milliseconds between shots
        this.setRange(255);                     // Radius within which the tower can target balloons
        this.setProjectileSpeed(23);            // Speed of the projectile
        this.setProjectileDamage(1);           // Damage dealt per hit
        this.setTowerImageSize(TowerImageSize.GLUEGUNNER); // Specific size for the GlueGunner tower image
        this.setCost(365);
        towerType = "GlueGunner";

        loadProjectileImages();
    }

    /**
     * Fires a projectile toward the specified balloon target.
     *
     * @param currentTarget The balloon that the GlueGunner will target.
     * @param projectiles   The list of active projectiles to which the new projectile will be added.
     */
    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {

        /**Code to find center and angle to balloon provided by CHATGPT and tweaked */
        double targetX = currentTarget.getX() + 27 / 2.0;
        double targetY = currentTarget.getY() + 33 / 2.0;

        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        double angleRadians = Math.atan2(targetY - y, targetX - x);

        Projectile p = new Projectile(
                x, y, COLLISION_AREA, projectileSpeed, angleRadians,
                diameter, currentTarget, 1, false,
                getProjectileImage(1), ProjectileImageSize.GOO, getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }

}
