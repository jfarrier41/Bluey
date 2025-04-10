import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: BombTower.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Bomb Tower.
 */
public class BombTower extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    public BombTower(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"BombTower.png");

        // Set default values for BombTower (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(150);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.BOMBTOWER);
    }

    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public boolean isProjectileActive() {
        return false;
    }

    @Override
    public void setProjectileActive(boolean projectileActive) {

    }

    @Override
    public void fire(Balloon balloon, ArrayList<Projectile> projectiles) {
        if (!projectileActive) { // Only fire if no active projectile
            this.projX = this.xPosition + 25; // Start at center of tower
            this.projY = this.yPosition + 25;
            this.projectileActive = true;
        }
    }
}
