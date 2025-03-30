import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class Ninja extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    public Ninja(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"Ninja.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(50);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
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
    public void fire(int targetX, int targetY) {

    }
}
