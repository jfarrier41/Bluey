import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Defines the Bomb Tower, a tower that fires explosive projectiles at balloons.
 * Authors: Jace Claassen and Joseph Farrier
 */
public class BombTower extends Tower {

    /** Radius of the projectile's collision area */
    private static final double COLLISION_AREA = 30;

    /**
     * Constructs a BombTower with default properties and image.
     *
     * @param runGame    the main game window
     * @param currentMap the current map image
     */
    public BombTower(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "BombTower.png");
        this.isRotatable = true;
        this.setFireSpeed(900);
        this.setRange(250);
        this.setProjectileSpeed(15);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.BOMBTOWER);
        this.setCost(555);
        towerType = "Bomb";
        loadProjectileImages();
    }

    /**
     * Fires a bomb projectile toward the target balloon.
     *
     * @param currentTarget the balloon being targeted
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
                diameter, currentTarget, 1, false,
                getProjectileImage(4), ProjectileImageSize.BOMB, getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }

    /**
     * Returns the list of targetable balloons currently in range.
     *
     * @return list of balloons in range
     */
    public ArrayList<Balloon> getTargets() {
        return targets;
    }
}
