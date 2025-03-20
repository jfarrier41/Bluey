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
    public DartMonkey(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"DartMonkey.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireSpeed(10);
        this.setRange(50);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
    }

    @Override
    int attack() {
        return 0;
    }
}
