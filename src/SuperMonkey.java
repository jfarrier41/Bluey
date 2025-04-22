import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Joseph Farrier
 * @author Jace Claassen
 * Implements the Super Monkey tower and defines its behavior.
 * Inherits from the Tower class and sets projectile, range, and visual parameters.
 */
public class SuperMonkey extends Tower {

    /** Collision radius for the projectile. */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor for the SuperMonkey tower.
     *
     * @param runGame    The parent JFrame running the game.
     * @param currentMap The map image used for collision/placement reference.
     */
    public SuperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "SuperMonkey.png");

        this.setRotatable(true);
        this.setFireRate(65);
        this.setRange(350);
        this.setProjectileSpeed(20);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.SUPERMONKEY);
        this.setCost(2650);
        towerType = "SuperMonkey";

        loadProjectileImages();
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

        /**Code to find center and angle to balloon provided by CHATGPT and tweaked */
        double targetX = currentTarget.getX() + 13;
        double targetY = currentTarget.getY();

        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        double angleRadians = Math.atan2(targetY - y, targetX - x);

        Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed,
                angleRadians, diameter, currentTarget, 1, false,
                getProjectileImage(0), ProjectileImageSize.DART, getProjectileDamage()
        );
        projectiles.add(p);
        setFireTimer();
    }

}
