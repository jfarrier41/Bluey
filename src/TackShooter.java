import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class TackShooter extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private int darts = 8;
    private static final int COLLISION_AREA = 40;

    public TackShooter(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "TackShooter.png");


        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireRate(700);
        this.setRange(150);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(1);
        this.setTowerImageSize(TowerImageSize.TACSHOOTER);
        this.setCost(425);
        towerType = "TackShooter";

        loadProjectileImages();


    }

    public void setDarts(int num) {
        darts = num;
    }

    @Override
    public void fire(Balloon balloon, ArrayList<Projectile> projectiles) {
        double x = this.xPosition + (getImgWidth() / 2.0);
        double y = this.yPosition + (getImgHeight() / 2.0);

        /** Calculate direction angle from tower to target. */

        for (int i = 0; i < darts - 1; i++) {
            double angle = Math.toRadians((360 / darts) * i);

            /** Create new projectile instance and add to list. */
            Projectile p = new Projectile(x, y, COLLISION_AREA, projectileSpeed,
                    angle, diameter, balloon, 1, false,
                    getProjectileImage(0), ProjectileImageSize.TAC,getProjectileDamage(), targets);
            projectiles.add(p);
        }

        /** Start cooldown before next fire. */
        setFireTimer();

    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets) {

    }
}
