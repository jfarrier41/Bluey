
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class DartMonkey extends Tower {
    private long lastFireTime; // Time of the last fire event
    private static final double COLLISION_AREA = 25;

    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"DartMonkey.png");

        // Set default values or SuperMonkey (can be overridden if needed)
        this.setRotatable(true);
        this.setFireRate(500);
        this.setRange(200);
        this.setProjectileSpeed(23);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.DARTMONKEY);
        String[] projectilePaths = new String[]{
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/explosion.png",
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
        return projectileActive;
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

        Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed, angleRadians, diameter,  currentTarget,1,false, getProjectileImage(0), ProjectileImageSize.DART);
        projectiles.add(p);
        setFireTimer();

    }

}

