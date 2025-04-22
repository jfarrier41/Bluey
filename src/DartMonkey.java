import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * @author Joseph Farrier
 * @author Jace Claassen
 * This class represents a Dart Monkey tower in the tower defense game.
 * It extends the abstract Tower class and implements behavior specific to the Dart Monkey,
 * including projectile behavior, image settings, and attack logic.
 */
public class DartMonkey extends Tower {

    /**
     * Collision area of the dart projectile (radius in pixels).
     */
    private static final double COLLISION_AREA = 25;

    /**
     * Constructor for DartMonkey.
     *
     * @param runGame    The JFrame that runs the game, used for context or parent references.
     * @param currentMap The BufferedImage representing the current game map.
     */
    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "DartMonkey.png");

        /**Set default values for DartMonkey (can be adjusted in subclass or later)*/
        this.setRotatable(true);
        this.setFireRate(500);
        this.setRange(200);
        this.setProjectileSpeed(23);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.DARTMONKEY);
        this.setCost(170);
        towerType = "DartMonkey";

        loadProjectileImages();
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

        /**Code to find center and angle to balloon provided by CHATGPT and tweaked */
        double targetX = currentTarget.getX() + 27 / 2.0;
        double targetY = currentTarget.getY() + 33 / 2.0;

        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        double angleRadians = Math.atan2(targetY - y, targetX - x);


        Projectile p = new Projectile(
                x, y, COLLISION_AREA, projectileSpeed, angleRadians,
                diameter, currentTarget, 1, false,
                getProjectileImage(0), ProjectileImageSize.DART,
                getProjectileDamage()
        );

        projectiles.add(p);
        setFireTimer();
    }

}
