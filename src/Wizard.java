import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Implements the Wizard tower that fires orb projectiles at balloons.
 * Authors: Joseph Farrier and Jace Claassen
 */
public class Wizard extends Tower {

    /** Collision radius for the Wizard's projectile. */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructs a Wizard tower with preset stats and image.
     *
     * @param runGame    the game window
     * @param currentMap the current game map
     */
    public Wizard(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "Wizard.png");
        this.isRotatable = true;
        this.setFireSpeed(700);
        this.setRange(250);
        this.setProjectileSpeed(8);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.WIZARD);
        this.setCost(470);
        towerType = "Wizard";
        loadProjectileImages();
    }

    /**
     * Fires an orb projectile toward a targeted balloon.
     *
     * @param currentTarget the balloon being targeted
     * @param projectiles   the list to add the projectile to
     */
    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        double targetX = currentTarget.getX() + 27 / 2.0;
        double targetY = currentTarget.getY() + 33 / 2.0;

        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        double angleRadians = Math.atan2(targetY - y, targetX - x);

        Projectile p = new Projectile(
                x, y, COLLISION_AREA, projectileSpeed, angleRadians,
                diameter, currentTarget, 4, false,
                getProjectileImage(3), ProjectileImageSize.ORB, getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }
}
