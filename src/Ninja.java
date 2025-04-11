import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class Ninja extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private static String[] projectilePaths;
    private static final double COLLISION_AREA = 25;
    public Ninja(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"Ninja.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.isRotatable = true;
        this.setFireSpeed(450);
        this.setRange(200);
        this.setProjectileSpeed(17);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.NINJA);
        towerType = "Ninja";
        projectilePaths = new String[] {
                "src/ProjectileImages/ninjaStar.png"
        };
        loadProjectileImages(projectilePaths);
    }


    @Override
    public boolean attack() {
        projectileActive = true;
        return true;
    }

    @Override
    public boolean isProjectileActive() {
        return false;
    }

    @Override
    public void setProjectileActive(boolean projectileActive) {

    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        // Get center of the target
        double targetX = currentTarget.getX() + 27 / 2.0;  // adjust for balloon center if needed
        double targetY = currentTarget.getY() + 33 / 2.0;

        // Get center of the tower
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        // Correct angle from tower to target
        double angleRadians = Math.atan2(targetY - y, targetX - x);

        Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed,angleRadians, diameter,  currentTarget,2,false, getProjectileImage(0),ProjectileImageSize.NINJASTAR);
        projectiles.add(p);
        setFireTimer();
    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets) {

    }
}
