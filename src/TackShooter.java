import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Joseph Farrier
 * @author Jace Classen
 * Defines the TackShooter tower, which fires multiple projectiles in a radial spread.
 * Authors: Jace Claassen and Joseph Farrier
 */
public class TackShooter extends Tower {

    /** Radius of the projectile's collision area */
    private static final int COLLISION_AREA = 25;

    /** Number of projectiles fired in one shot */
    private int darts = 8;

    /**
     * Constructs a TackShooter with default properties and image.
     *
     * @param runGame    the main game window
     * @param currentMap the current map image
     */
    public TackShooter(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "TackShooter.png");
        this.setFireRate(700);
        this.setRange(150);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.TACSHOOTER);
        this.setCost(425);
        towerType = "TackShooter";
        loadProjectileImages();
    }

    /**
     * Sets the number of darts (projectiles) fired per attack.
     *
     * @param num number of darts to fire
     */
    public void setDarts(int num) {
        darts = num;
    }

    /**
     * Fires multiple projectiles in a circular spread pattern.
     *
     * @param balloon     the target balloon (not specifically tracked)
     * @param projectiles the list of active projectiles
     */
    @Override
    public void fire(Balloon balloon, ArrayList<Projectile> projectiles) {
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        for (int i = 0; i < darts - 1; i++) {
            double angle = Math.toRadians((360 / darts) * i);
            Projectile p = new Projectile(
                    x, y, COLLISION_AREA, projectileSpeed, angle,
                    diameter, balloon, 1, false,
                    getProjectileImage(0), ProjectileImageSize.TAC, getProjectileDamage()
            );
            projectiles.add(p);
        }

        setFireTimer();
    }
}
