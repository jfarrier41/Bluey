import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Defines the Ninja tower, which throws fast, rotating projectiles at targets.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class Ninja extends Tower {

    /**
     * Radius of the projectile's collision area
     */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructs a Ninja tower with default properties and image.
     *
     * @param runGame    the main game window
     * @param currentMap the current map image
     */
    public Ninja(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "Ninja.png");
        this.isRotatable = true;
        this.setFireRate(650);
        this.setRange(250);
        this.setProjectileSpeed(17);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.NINJA);
        this.setCost(425);
        towerType = "Ninja";
        loadProjectileImages();
    }

    /**
     * Fires a high-speed projectile toward a specific target.
     *
     * @param currentTarget the balloon being targeted
     * @param projectiles   the list of active projectiles
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
                diameter, currentTarget, 3, true,
                getProjectileImage(2), ProjectileImageSize.NINJASTAR, getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }
}
