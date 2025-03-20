import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    }

    @Override
    int attack() {
        return 0;
    }
}
