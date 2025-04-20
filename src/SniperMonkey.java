import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Defines the Sniper Monkey tower, which shoots long-range, high-speed projectiles at balloons.
 * Authors: Jace Claassen and Joseph Farrier
 */
public class SniperMonkey extends Tower {

    /** Radius of the projectile's collision area */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructs a Sniper Monkey with default attributes.
     *
     * @param runGame    the main game window
     * @param currentMap the current map image
     */
    public SniperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "SniperMonkey.png");

        this.setRotatable(true);
        this.setFireRate(1600);
        this.setRange(800);
        this.setProjectileSpeed(70);
        this.setProjectileDamage(4);
        this.setCost(300);
        this.setTowerImageSize(TowerImageSize.SNIPERMONKEY);
        towerType = "Sniper";

        loadProjectileImages();
    }

    /**
     * Fires a high-speed projectile at the targeted balloon.
     *
     * @param currentTarget the balloon to attack
     * @param projectiles   the list of active projectiles
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
                diameter, currentTarget, 1, true,
                getProjectileImage(0), ProjectileImageSize.SNIPERBULLET, getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }
}
