import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class DartMonkey extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    private boolean attacking = false;

    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"DartMonkey.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(150);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
        this.setProjectileImage("src/towerImages/dart.png");
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
    public void fire(int targetX, int targetY) {
        if (!projectileActive) { // Only fire if no active projectile
            this.projX = this.xPosition + 25; // Start at center of tower
            this.projY = this.yPosition + 25;
            this.projectileActive = true;
        }
    }

}
