import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Filename: SuperMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Super Monkey.
 */

public class SuperMonkey extends Tower {
    // Constructor that only takes JFrame and BufferedImage
    public SuperMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"SuperMonkey.png");

        // Set default values for SuperMonkey (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(600);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
    }

    @Override
    int attack() {
        return 0;
    }
}
